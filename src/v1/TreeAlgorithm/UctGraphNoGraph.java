/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.TreeAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis
 */
public class UctGraphNoGraph {

    private final UctNode nullp = new UctNode(null, -1);

    public class UctNode {

        List<UctNode> childs = null;
        List<Integer> childVisit = null;
        long hits = 0;
        double scoreacc = 0;
        final BoardData state;

        public int depth;

        public UctNode(BoardData state, int depth) {

            this.state = state;
            this.depth = depth;
        }

        public BoardData bestState() {
            double max = Double.NEGATIVE_INFINITY;
            UctNode maxfp = nullp;

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

        public void deflat() {
            List<BoardData> next = state.getSubData();
            childs = new ArrayList<>(next.size());
            childVisit = new ArrayList<>(next.size());
            for (BoardData n : next) {
                childs.add(new UctNode(n, this.depth + 1));
                childVisit.add(0);
            }
        }

        public int selectChildToVisit() {
            double max = Double.NEGATIVE_INFINITY;
            int maxindex = -1;
            for (int i=0;i<childs.size();i++) {
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
