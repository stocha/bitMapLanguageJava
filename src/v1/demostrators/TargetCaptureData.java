/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.demostrators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import v1.L64p.vrac.L64fbase;
import v1.L64p.vrac.TargetDescr;
import v1.TreeAlgorithm.BoardData;

/**
 *
 * @author denis
 */
public class TargetCaptureData implements BoardData {

    public final L64fbase.gob64Struct mem = new L64fbase.gob64Struct();
    final TargetDescr objective;
    final int metaphase;

    static long rand = 9888478;

    public TargetCaptureData(TargetDescr objective, int metaphase) {
        mem.p0 = objective.b;
        mem.p1 = objective.w;
        this.objective = objective;
        this.metaphase = metaphase ^ 1;
    }

    public TargetCaptureData(L64fbase.gob64Struct sim, TargetDescr objective, int metaphase) {
        mem.copy(sim);
        this.objective = objective;
        this.metaphase = metaphase ^ 1;
    }

    @Override
    public boolean equals(Object obj) {
        TargetCaptureData o = (TargetCaptureData) obj;
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

    @Override
    public List<BoardData> getSubData() {
        List<BoardData> mv = new ArrayList<>();

        L64fbase.gob64Struct sim = new L64fbase.gob64Struct();
        sim.copy(mem);
        //System.out.println("subdata src "+sim.debug_show());
        sim.rand = rand;
        long m = sim.playOneRandNoSuicide();
        //System.out.println("first sim "+sim.debug_show());
        rand = sim.rand;
        long forbid = 0;
        while (m != 0) {
            mv.add(new TargetCaptureData(sim, objective, metaphase));
            sim.copy(mem);
            forbid |= m;
            sim.rand = rand;
            m = sim.playOneRandNoSuicide(forbid);
            rand = sim.rand;
            // System.out.println("other sim "+sim.debug_show());
        }

        return mv;
    }

    @Override
    public double scoreOnce() {

        L64fbase.gob64Struct sim = new L64fbase.gob64Struct();

        sim.copy(mem);
        sim.rand = rand;
        //sim.finishRandNoSuicide(0,metaphase);
        rand = sim.rand;
        //if(sc > 0) return 1.0; else return 0.0;        
        //return -sc;
        if (metaphase != 1) {
            sim.passMove();
        }
        long wCont = (~sim.p1 & objective.t);

        double sc = wCont == 0 ? 0.0 : 1;

        if (sc > 0) {
            //System.out.println("phas " + mem.phase + "| met " + metaphase + "| Found : " + mem.debug_show());

            //System.out.println("objective "+L64fbase.outString(objective.t, sim.p0));
            //System.out.println("object "+L64fbase.outString(wCont, sim.p0));
        }

        return sc;
    }

}
