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

/**
 *
 * @author denis
 */
public class Light64AmafSrc  implements BoardData {

    public final L64fbase.gob64Struct mem = new L64fbase.gob64Struct();
    final double komi;
    final int metaphase;
    
    static long rand=9888478;
    
    final boolean isSrc;
    final List<amafResult> amafStore;
    final Light64AmafSrc src;
    
    public static class amafResult{
        long bamaf;
        long wamaf;
        double score;
    }

    public Light64AmafSrc(L64fbase.gob64Struct state, double komi,int metaphase, Light64AmafSrc src) {
        mem.copy(state);
        this.komi = komi;
        this.metaphase=metaphase^1;
        
        this.src=src;
        if(this.src==this){
            isSrc=true;
            amafStore=new ArrayList<>();
        }else{
            amafStore=null;
            isSrc=false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        Light64Data o=(Light64Data)obj;
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
            mv.add(new Light64AmafSrc(sim,komi,metaphase,null));
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
        double sc = sim.finishRandNoSuicide(komi,metaphase);
        rand=sim.rand;
        if(sc > 0) return 1.0; else return 0.0;        
        //return -sc;
    }

}
