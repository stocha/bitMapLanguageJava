/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.TreeAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import v1.L64p.vrac.L64fbase;
import static v1.L64p.vrac.L64fbase.*;

/**
 *
 * @author denis
 */
public class Light64AmafSrcData implements BoardData {

    public static long nbNodeTotal = 0;
    public static long nbNodeSrc = 0;
    public static long askedSim = 0;
    public static long realSim = 0;

    public static final void resetStats() {
        nbNodeTotal = 0;
        nbNodeSrc = 0;
        askedSim = 0;
        realSim = 0;
    }

    public static String getStats() {
        String res = "Light64AmafSrc stats  ";
        if (nbNodeTotal == 0) {
            return "No stats";
        }
        double ratio = nbNodeSrc / (double) nbNodeTotal;
        res += " Node " + nbNodeTotal + " NodeSrc " + nbNodeSrc + "   ratio " + ratio;
        res += " reelSim " + realSim + " askedSim=" + askedSim;

        return res + "\n";
    }

    public final L64fbase.gob64Struct mem = new L64fbase.gob64Struct();
    final double komi;
    final int metaphase;

    static long rand = 9888478;

    final boolean isSrc;
    final List<amafResult> amafStore;
    final Light64AmafSrcData src;

    int nbConsomedSimulation = 0;
    int nbScoreDistributed=0;
    int nbSimulationConsomated=0;
    int nbSimulationSkipped=0;

    amafResult amaFromSrc = new amafResult();

    public static class amafResult {

        long bamaf;
        long wamaf;
        double score;

        public void cp(amafResult src) {
            this.bamaf = src.bamaf;
            this.wamaf = src.wamaf;
        }

        public void addMove(long move, long phase) {
            if ((move & bamaf) != 0 || (move & wamaf) != 0) {
                return;
            }
            if (phase == 0) {
                bamaf |= move;
            } else {
                wamaf |= move;
            }
        }

        public String out() {
            return "sc=" + score + " " + outString(bamaf, wamaf);
        }

        public boolean appartient(amafResult am) {
            long a = bamaf & am.bamaf;
            long b = wamaf & am.wamaf;

            a ^= bamaf;
            b ^= wamaf;
            a |= b;

            return a == 0;
        }
    }

    public Light64AmafSrcData(L64fbase.gob64Struct state, double komi, int metaphase, Light64AmafSrcData src, amafResult am, long m, long phase) {
        this(state, komi, metaphase, src);
        amaFromSrc.bamaf = am.bamaf;
        amaFromSrc.wamaf = am.wamaf;

        if (phase == 0) {
            amaFromSrc.bamaf |= m;
        } else {
            amaFromSrc.wamaf |= m;
        }
    }

    public Light64AmafSrcData(L64fbase.gob64Struct state, double komi, int metaphase, Light64AmafSrcData src) {
        mem.copy(state);
        this.komi = komi;
        this.metaphase = metaphase ^ 1;

        if (src == null) {
            this.src = this;
        } else {
            this.src = src;
        }

        if (this.src == this) {
            isSrc = true;
            amafStore = new ArrayList<>();
            nbNodeSrc++;
        } else {
            amafStore = null;
            isSrc = false;
        }
        nbNodeTotal++;
    }

    @Override
    public boolean equals(Object obj) {
        Light64AmafSrcData o = (Light64AmafSrcData) obj;
        return Arrays.equals(new long[]{this.mem.p0, this.mem.p1}, new long[]{o.mem.p0, o.mem.p1});
        //return  true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new long[]{this.mem.p0, this.mem.p1});
        //return 125;
    }

    public String computeAmafTheoretical() {
        String res = " theoAmaf[";

        double hit = 0;
        double sc = 0;
        for (amafResult ar : src.amafStore) {
            if (amaFromSrc.appartient(ar)) {
                double sco = ar.score;
                hit++;
                if (sco > 0) {
                    sc += 1;
                }
            }
        }

        if (hit > 0) {
            res += " " + hit + "/" + src.amafStore.size() + " " + sc / hit;
        }

        return res + "]\n";
    }

    @Override
    public String toString() {
        String res = "";
        res += computeAmafTheoretical();
        res += " myAmafMap " + amaFromSrc.out();
        res += mem.debug_show() + " ";
        res +=" simBase "+src.amafStore.size()+" consom"+nbConsomedSimulation;
        res+=" distributed scor"+nbScoreDistributed+" skipedSImBase "+nbSimulationSkipped;

        return res;
    }

    private boolean tstReplay(long move) {
        long m = move;
        long covam = amaFromSrc.bamaf | amaFromSrc.wamaf | src.mem.p0 | src.mem.p1;
        if ((covam & m) != 0) {
            return true;
        }

        if (count(amaFromSrc.bamaf) + count(amaFromSrc.wamaf) < 5) {
            return true;
        }
        return false;
    }

    private boolean tstSrc(long move) {
        //if(true) return true;

        long m = move;
        long covam = amaFromSrc.bamaf | amaFromSrc.wamaf;
        if ((covam & m) != 0) {
            return true;
        }
        return src.mem.isConflictingAmaf(amaFromSrc.bamaf | m, amaFromSrc.wamaf, 0);
        //return false;
        // return true;
    }

    @Override
    public List<BoardData> getSubData() {
        List<BoardData> mv = new ArrayList<>();

        L64fbase.gob64Struct sim = new L64fbase.gob64Struct();
        sim.copy(mem);
        sim.rand = rand;
        long m = sim.playOneRandNoSuicide();
        rand = sim.rand;
        long forbid = 0;
        while (m != 0) {
            if (tstSrc(m)) {

                if (false & !tstReplay(m)) {
                    System.out.println("source state" + src.mem.debug_show());

                    System.out.println("amarf +src " + outString(src.mem.p0, src.mem.p1, amaFromSrc.bamaf, amaFromSrc.wamaf, src.mem.phase));
                    System.out.println("amarf " + outString(amaFromSrc.bamaf | m, amaFromSrc.wamaf | m));
                    System.out.println("move " + outString(m, 0));
                    System.out.println("Current " + mem.debug_show());
                    System.out.println("Proposed " + sim.debug_show());
                }

                mv.add(new Light64AmafSrcData(sim, komi, metaphase, null));
            } else {
                mv.add(new Light64AmafSrcData(sim, komi, metaphase, this.src, this.amaFromSrc, m, mem.phase));
            }

            sim.copy(mem);
            forbid |= m;
            sim.rand = rand;
            m = sim.playOneRandNoSuicide(forbid);
            rand = sim.rand;
        }

        return mv;
    }

    @Override
    public double scoreOnce() {
                nbScoreDistributed++;

        if (true) {
            
            double sc = scoreFromAmaf();
            // System.out.println(" "+this.metaphase+" scoring "+mem.debug_show());
            //System.out.print("|"+sc+"m"+metaphase);
            if (sc > 0) {
                return 1.0;
            } else {
                return 0.0;
            }
        }

        L64fbase.gob64Struct sim = new L64fbase.gob64Struct();

        sim.copy(mem);
        sim.rand = rand;
        double sc = sim.finishRandNoSuicide(komi, metaphase);
        rand = sim.rand;
        if (sc > 0) {
            return 1.0;
        } else {
            return 0.0;
        }
        //return -sc;
    }

    private boolean findNextAmaf() {
        int len = this.src.amafStore.size();
        for (; this.nbConsomedSimulation < len; this.nbConsomedSimulation++) {
            int i = this.nbConsomedSimulation;
            amafResult cmp = src.amafStore.get(i);
            if (this.amaFromSrc.appartient(cmp)) {
                // System.out.println(this.amaFromSrc.out()+" appartient a "+cmp.out());

                return true;
            }else{
               // System.out.println("visit "+this. "skipping amaf "+nbConsomedSimulation);
                nbSimulationSkipped++;
            }

        }

        return false;
    }

    private double getCurrAmafScore() {
        double k = komi;
        if (!this.amaFromSrc.appartient(src.amafStore.get(this.nbConsomedSimulation))) {
            throw new RuntimeException("Utilisation d'un amaf non correspondant");
        }
        
        double scoreBoard = src.amafStore.get(this.nbConsomedSimulation++).score;

        if ((metaphase) == 0) {
            return (scoreBoard - k);
        } else {
            return -(scoreBoard - k);
        }
    }

    private boolean addOneAmaf() {
        realSim++;

        amafResult res = new amafResult();
        res.cp(amaFromSrc);
        gob64Struct sim = new gob64Struct();

        sim.copy(this.mem);
        sim.rand = rand;

        int pass = 0;
        int play = 0;
        for (int i = 0; i < 64 * 3; i++) {
            //System.out.println(g.debug_show());
            long move = sim.playOneRandNoSuicide();
            res.addMove(move, sim.phase ^ 1);
            if (move == 0) {
                pass++;
            } else {
                pass = 0;
                play++;
            }
            if (pass == 2) {
                res.score = sim.scoreBoard();
                if (sim.phase != 0) {
                    res.score = -res.score;
                }
                if (play > 0) {
                    src.amafStore.add(res);
                    //System.out.println("adding "+res.out()+" to "+src.mem.debug_show());
                }
            }
        }
        rand = sim.rand;
        return play > 0;
    }

    private double scoreFromAmaf() {
        askedSim++;

        if (findNextAmaf()) {
            return getCurrAmafScore();
        }
        boolean add = addOneAmaf();

        if (add) {
            add = findNextAmaf();
            if (!add) {
                throw new RuntimeException("Impossible de ne pas trouver ce qu'on vient juste d'ajouter !");
            }
            return getCurrAmafScore();
        } else {
            //System.out.println();
            //System.out.println("Stopping on "+mem.debug_show()+" phase="+mem.phase+" meta="+metaphase);
            //System.out.println("Score final = "+mem.scoreGame(komi, metaphase)+" meta="+metaphase);
            //System.exit(0);
            return mem.scoreGame(komi, metaphase);

        }

    }

}
