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

    public static final void resetStats() {
        nbNodeTotal = 0;
        nbNodeSrc = 0;
    }

    public static String getStats() {
        String res = "Light64AmafSrc stats  ";
        if (nbNodeTotal == 0) {
            return "No stats";
        }
        double ratio = nbNodeSrc / (double) nbNodeTotal;
        res += " Node " + nbNodeTotal + " NodeSrc " + nbNodeSrc + "   ratio " + ratio;

        return res;
    }

    public final L64fbase.gob64Struct mem = new L64fbase.gob64Struct();
    final double komi;
    final int metaphase;

    static long rand = 9888478;

    final boolean isSrc;
    final List<amafResult> amafStore;
    final Light64AmafSrcData src;

    amafResult amaFromSrc = new amafResult();

    public static class amafResult {

        long bamaf;
        long wamaf;
        double score;
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

    @Override
    public String toString() {
        String res = "";
        res += mem.debug_show();

        return res;
    }

    private boolean tstReplay(long move) {
        long m = move;
        long covam = amaFromSrc.bamaf | amaFromSrc.wamaf |src.mem.p0 | src.mem.p1;
        if ((covam & m) != 0) {
            return true;
        }
        
        if(count(amaFromSrc.bamaf)+count(amaFromSrc.wamaf) < 5) return true;
        return false;
    }

    private boolean tstSrc(long move) {
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

                    System.out.println("amarf +src " + outString(src.mem.p0,src.mem.p1,amaFromSrc.bamaf , amaFromSrc.wamaf,src.mem.phase));
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

}
