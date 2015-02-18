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
public class AnyCaptureNoTarget implements BoardData {

    public final L64fbase.gob64Struct mem = new L64fbase.gob64Struct();
    final TargetDescr objective;
    final int metaphase;

    static long rand = 9888478;
    boolean wining = false;
    boolean evaled = false;

    public AnyCaptureNoTarget(TargetDescr objective, int metaphase) {
        mem.p0 = objective.b;
        mem.p1 = objective.w;
        this.objective = objective;
        this.metaphase = metaphase ^ 1;
    }

    public AnyCaptureNoTarget(L64fbase.gob64Struct sim, TargetDescr objective, int metaphase) {
        mem.copy(sim);
        this.objective = objective;
        this.metaphase = metaphase ^ 1;
    }

    @Override
    public boolean equals(Object obj) {
        AnyCaptureNoTarget o = (AnyCaptureNoTarget) obj;
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
        if (!evaled) {
            throw new RuntimeException("supposed to be evaled at least once");
        }
        if (!evaled) {
            scoreOnce();
        }

        if (wining) {
            return mv;
        }

        L64fbase.gob64Struct sim = new L64fbase.gob64Struct();
        sim.copy(mem);
        //System.out.println("subdata src "+sim.debug_show());
        sim.rand = rand;
        long m = sim.playOneRandNoSuicide();
        //System.out.println("first sim "+sim.debug_show());
        rand = sim.rand;
        long forbid = 0;
        while (m != 0) {
            mv.add(new AnyCaptureNoTarget(sim, objective, metaphase));
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

        if (evaled) {
            throw new RuntimeException("not supposed to eval multiple times");
        }

        evaled = true;

        //System.out.println("Evaluating " + mem.debug_show());
//        TargetDescr desc = new TargetDescr();
//        desc.b = mem.p0;
//        desc.w = mem.p1;
//        desc.t = mem.getSingleLibPointsAtt();
//        System.out.println("proposed capture points " + desc.out());

        L64fbase.gob64Struct sim = new L64fbase.gob64Struct();
        sim.copy(mem);
        //System.out.println("subdata src "+sim.debug_show());
        sim.rand = rand;
        long forbid = ~mem.getSingleLibPointsAtt();

        long m = sim.playOneRandNoSuicide(forbid);
        //System.out.println("first sim "+sim.debug_show());
        rand = sim.rand;
        if ((sim.p0 ^ mem.p1) != 0 && m != 0) {
            //System.out.println("won from " + mem.debug_show());
            //System.out.println("win move " + sim.debug_show());
            wining = true;

            // break;
        } else {
            while (m != 0) {
                sim.copy(mem);
                forbid |= m;
                sim.rand = rand;
                m = sim.playOneRandNoSuicide(forbid);
                rand = sim.rand;

                if ((sim.p0 ^ mem.p1) != 0 && m != 0) {
                    //System.out.println("won from " + mem.debug_show());
                    //System.out.println("win move " + sim.debug_show());
                    wining = true;

                    break;
                }
            }
        }
        if (wining) {
            return 1;
        } else {
            return 0;
        }
    }

}
