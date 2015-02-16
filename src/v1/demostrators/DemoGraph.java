/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.demostrators;

import v1.TreeAlgorithm.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author denis
 */
public class DemoGraph {

    static public long reuse = 0;
    static public long shortenDepth = 0;
    static public long loopSkip = 0;
    static public long endPoint = 0;

    final long expandValue = 40;

    public static final void resetStats() {
        reuse = 0;
        shortenDepth = 0;
        loopSkip = 0;
        endPoint = 0;
    }

    public static void printlnReuse() {
        System.out.println("reuse " + reuse + " | shorten " + shortenDepth + " | skip " + loopSkip
                + " | endPoint " + endPoint);
    }

    private final DemoGraphNode nullp = new DemoGraphNode(null, -1);
    private final HashMap<BoardData, DemoGraphNode> graph;

    public void clear() {
        graph.clear();
    }

    public DemoGraph(int hashMapSize) {
        graph = new HashMap<>(hashMapSize);
    }

    public class DemoGraphNode {

        List<DemoGraphNode> childs = null;
        double win = 0.0;
        boolean locked = false;
        final BoardData state;
        long hits = 0;

        public int depth;

        public String debugFlat() {
            String res = "";
            if (childs == null) {
                return res;
            }
            for (int i = 0; i < childs.size(); i++) {
                res += " Root child " + i + " ";
                res += " score " + childs.get(i).win;
                res += "" + childs.get(i).state;
            }

            return res;
        }

        public String debugRec(int depth) {
            String res = "\n";
            if (depth >= 0) {
                res += String.format("%" + (depth+1) * 3 + "s", ("=" + depth + "=="));
            }
            res += " score " + win+"  H"+hits+" L"+locked;
            res += this.state;
            if (childs == null) {
                return res;
            }
//System.out.println(""+this.debugFlat());
            BoardData best = this.bestState();
            DemoGraphNode next = graph.get(best);
            
            int k=0;
            //for(DemoGraphNode next : childs)
            {                    
                if (next == null) {
                    return res+" DONE";
                }
                if (next.depth > this.depth && depth < 4) {
                    res+="["+(k++)+"]"+next.depth;
                    res += next.debugRec(depth + 1);
                }
            }

            return res;
        }

        public DemoGraphNode(BoardData state, int depth) {

            this.state = state;
            this.depth = depth;

            //System.out.println("" + state);
        }

        public BoardData bestState() {
            double max = Double.NEGATIVE_INFINITY;
            DemoGraphNode maxfp = nullp;

            //System.err.println(""+this.debugFlat());
            //System.out.println("childs count : "+childs.size());
            for (DemoGraphNode fp : childs) {
    //System.err.println("lm "+fp.win);
                if ((fp.win > 0) && fp.locked) {
                    //System.err.println("returned max"+fp.state);
                    return fp.state;
                }
            }
            for (DemoGraphNode fp : childs) {
    //System.err.println("lm "+fp.win);
                if (fp.locked) {
                    //System.err.println("returned max"+fp.state);
                    return fp.state;
                }
            }            
            return maxfp.state;
        }

        double visitValue(int childNum) {
            DemoGraphNode node = childs.get(childNum);

            if (true) {
                
                if(node.hits<=0.001){
                    return 1.0;
                }else
                return 1.0 / (double) node.hits;
            }
            return 0;
        }

        double scoreAvg() {
            return win == 0 ? 1 : 0;
        }

        public double doSimulation() {
            
            if(locked) return 0;

            //System.out.println("doing it simulation");
            hits++;

            if (childs == null && hits > 1) {
                deflat();
            }

            if (childs != null && childs.size() > 0) {
                int chInd = selectChildToVisit();
                if (chInd == -1) {
                    
                    //System.out.println("<LOCKED "+this.debugFlat()+" LOCKED>");
                    locked = true;
                    return 1.0 - win;
                }
                DemoGraphNode ch = childs.get(chInd);
                double sc = 1.0 - ch.doSimulation();
                sc=0;
                
                for(DemoGraphNode n : childs){
                    sc=Math.max(sc, n.win);
                    if(n.win ==1 && n.locked){
                        win=0;locked=true;break;
                    }
                }
                
                win= 1-sc;
                return win;
            } else {
                //System.out.println("Non deflat "+this.state);
                double sc = state.scoreOnce();
                win = sc;
                if(sc==1) locked=true;

                return 1-sc;
            }
        }

        public DemoGraphNode getExistingBoard(BoardData bd) {
            DemoGraphNode res;

            res = graph.get(bd);

            if (res == null) {
                res = new DemoGraphNode(bd, this.depth + 1);
                graph.put(bd, res);

                //System.out.println("bd "+bd.hashCode()+" "+bd.getClass().getCanonicalName());
            } else {
                reuse++;
            }

            return res;
        }

        public void deflat() {
            //System.out.println("deflating "+this.state);
            List<BoardData> next = state.getSubData();
            childs = new ArrayList<>(next.size());
            for (BoardData n : next) {
                childs.add(getExistingBoard(n));
            }
        }

        boolean checkChildLoop(int childInd) {
            DemoGraphNode next = childs.get(childInd);
            if (next.depth > this.depth + 1) {
                shortenDepth++;
                next.depth = this.depth + 1;
            } else if (next.depth <= this.depth) {
                //System.out.println(""+this+" "+depth+"  next"+childInd+"  depth="+next.depth);
                loopSkip++;
                return true;
            }

            return false;
        }

        ;

        public int selectChildToVisit() {
            double max = Double.NEGATIVE_INFINITY;
            int maxindex = -1;
            for (int i = 0; i < childs.size(); i++) {
                if (checkChildLoop(i)||childs.get(i).locked==true) {
                    continue;
                }

                double sc = visitValue(i);
                //if(maxindex!=-1)
                //System.err.println(sc+" /"+max+" mInd "+maxindex);

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
