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
import v1.TreeAlgorithm.BoardData;
import v1.demostrators.DemonstrationWithTarget.TargetDescr;

/**
 *
 * @author denis
 */
public class Light64TargetData  implements BoardData {

    public final L64fbase.gob64Struct mem = new L64fbase.gob64Struct();
    final DemonstrationWithTarget.TargetDescr objective;
    final int metaphase;
    
    static long rand=9888478;

    public Light64TargetData(L64fbase.gob64Struct src, TargetDescr objective,int metaphase) {
        mem.copy(src);
        this.objective=objective;
        this.metaphase=metaphase^1;
    }

    @Override
    public boolean equals(Object obj) {
        Light64TargetData o=(Light64TargetData)obj;
        return Arrays.equals(new long[]{this.mem.p0,this.mem.p1}, new long[]{o.mem.p0,o.mem.p1});
        //return  true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new long[]{this.mem.p0,this.mem.p1});
        //return 125;
    }
    
    
    
    @Override
    public String toString(){
        String res="";
        res+=mem.debug_show();
        
        return res;
    }

    @Override
    public List<BoardData> getSubData() {
        List<BoardData> mv=new ArrayList<>();
        
        L64fbase.gob64Struct sim=new L64fbase.gob64Struct();
        sim.copy(mem);
        sim.rand=rand;
        long m=sim.playOneRandNoSuicide();
        rand=sim.rand;
        long forbid=0;
        while(m!=0){
            mv.add(new Light64TargetData(sim,objective,metaphase));
            sim.copy(mem);
            forbid|=m;
            sim.rand=rand;
            m=sim.playOneRandNoSuicide(forbid);
            rand=sim.rand;
        }
        
        
        
        
        return mv;
    }

    @Override
    public double scoreOnce() {
        L64fbase.gob64Struct sim = new L64fbase.gob64Struct();
        
        sim.copy(mem);
        sim.rand=rand;        
        sim.finishRandNoSuicide(0,metaphase);
        rand=sim.rand;
        //if(sc > 0) return 1.0; else return 0.0;        
        //return -sc;
        return 0.0;
    }

}

