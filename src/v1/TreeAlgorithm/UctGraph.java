/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.TreeAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author denis
 */
public class UctGraph {
    
    static public long reuse=0;
    static public long shortenDepth=0;
    static public long loopSkip=0;
    static public long endPoint=0;
    
    public static final void resetStats(){
        reuse=0;shortenDepth=0;loopSkip=0;endPoint=0;
    }
    
    public static void printlnReuse(){
        System.out.println("reuse "+reuse+" | shorten "+shortenDepth+" | skip "+loopSkip
        +" | endPoint "+endPoint);
    }

    private final UctNode nullp = new UctNode(null, -1);
    private final HashMap<BoardData,UctNode> graph;
    
    public void clear(){
        graph.clear();
    }
    
    

    public UctGraph(int hashMapSize) {
        graph=new HashMap<>(hashMapSize);
    }
    
    

    public class UctNode {

        List<UctNode> childs = null;
        List<Integer> childVisit = null;
        long hits = 0;
        double scoreacc = 0;
        final BoardData state;

        public int depth;
        
        public String debugFlat(){
            String res="";
            if(childs==null) return res;
            for(int i=0;i<childs.size();i++){
                res+=" Root child "+i+" ";
                res+=" hit "+childVisit.get(i);
                res+=" score "+childs.get(i).scoreAvg();
                res+=childs.get(i).state;
            }
            
            return res;
        }
        
        public String debugRec(int depth){
            String res="\n";
            if(depth>0)
            res+=String.format("%"+depth*3+"s", ("="+depth +"=="));
            
                res+=" hit "+hits;
                res+=" score "+this.scoreAvg();
                res+=this.state;
            if(childs==null) return res;
            
            BoardData best=this.bestState();
            UctNode next=graph.get(best);
            if(next==null) return res;
            if(next.depth>this.depth && depth < 7)
                res+=next.debugRec(depth+1);
            
            return res;
        }        

        public UctNode(BoardData state, int depth) {

            this.state = state;
            this.depth = depth;
        }

        public BoardData bestState() {
            double max = Double.NEGATIVE_INFINITY;
            UctNode maxfp = nullp;
            
            //System.err.println(""+this.debugFlat());

            //System.out.println("childs count : "+childs.size());
            for (UctNode fp : childs) {
                
                
                double sc = fp.scoreAvg();
                if (sc > max) {
                    max = sc;
                    maxfp = fp;
                }
            }
            return maxfp.state;
        }

        double visitValue(int childNum) {
            UctNode node = childs.get(childNum);

            if (node.hits == 0) {
                return Double.MAX_VALUE;
            }
            double winrate = node.scoreAvg();
            if (false && hits == 0) {

                System.err.println("plante sur " + this + "   " + node);
                throw new RuntimeException("hits 0");
            }
            double lnparentvisit = Math.log((double) hits);
            double f = Math.sqrt(lnparentvisit / (5 * (double) childVisit.get(childNum)));
            //System.err.println("f "+f+" win "+winrate+" lnPar "+lnparentvisit);
            return winrate + f;
        }

        double scoreAvg() {
            if (hits > 0) {
                return scoreacc / hits;
            }
            return 0;
        }

        public double doSimulation() {
            final long expandValue = 20;

            if (childs == null && hits > expandValue) {
                deflat();
            }

            if (childs != null && childs.size() > 0) {
                int chInd = selectChildToVisit();
                if(chInd==-1){
                    endPoint++;
                    double sc = state.scoreOnce();
                    hits++;
                    scoreacc += sc;
                    return sc;
                }
                UctNode ch=childs.get(chInd);
                        double sc = 1.0 - ch.doSimulation();
                this.hits++;
                childVisit.set(chInd,(childVisit.get(chInd)+1) );
                this.scoreacc += sc;
                return sc;
            } else {

                double sc = state.scoreOnce();
                hits++;
                scoreacc += sc;
                return sc;
            }
        }
        
        public UctNode getExistingBoard(BoardData bd){
            UctNode res;
            
            
            res=graph.get(bd);
            
            if(res==null){
                res=new UctNode(bd, this.depth + 1);
                graph.put(bd, res);
                
                //System.out.println("bd "+bd.hashCode()+" "+bd.getClass().getCanonicalName());
            }else{
                reuse++;
            }
            
            
            
            
            return res;
        }

        public void deflat() {
            List<BoardData> next = state.getSubData();
            childs = new ArrayList<>(next.size());
            childVisit = new ArrayList<>(next.size());
            for (BoardData n : next) {
                childs.add(getExistingBoard(n));
                childVisit.add(0);
            }
        }
        
        boolean checkChildLoop(int childInd){
            UctNode next=childs.get(childInd);
            if(next.depth>this.depth+1){
                shortenDepth++;
                next.depth=this.depth+1;
            }else if(next.depth<=this.depth){
                loopSkip++;
                return true;
            }
            
            return false;
        };

        public int selectChildToVisit() {
            double max = Double.NEGATIVE_INFINITY;
            int maxindex = -1;
            for (int i=0;i<childs.size();i++) {
                if(checkChildLoop(i)) continue;
                
                double sc = visitValue(i);
                //System.err.println(sc+" /"+max+" "+fp);
                if (sc >= max) {
                    max = sc;
                    maxindex = i;
                }
            }
            return maxindex;
        }

    ;
}

}
