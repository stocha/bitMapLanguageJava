/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeAlgorithm;

import L64p.vrac.L64fbase;
import L64p.vrac.L64fbase.gob64Struct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis
 */
public class Light64Data implements BoardData {

    public final L64fbase.gob64Struct mem = new gob64Struct();
    final double komi;
    final int metaphase;
    
    static long rand=9888478;

    public Light64Data(gob64Struct src, double komi,int metaphase) {
        mem.copy(src);
        this.komi = komi;
        this.metaphase=metaphase^1;
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
        
        gob64Struct sim=new gob64Struct();
        sim.copy(mem);
        sim.rand=rand;
        long m=sim.playOneRandNoSuicide();
        rand=sim.rand;
        long forbid=0;
        while(m!=0){
            mv.add(new Light64Data(sim,komi,metaphase));
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
        gob64Struct sim = new gob64Struct();
        
        sim.copy(mem);
        sim.rand=rand;        
        double sc = sim.finishRandNoSuicide(komi,metaphase);
        rand=sim.rand;
        if(sc > 0) return 1.0; else return 0.0;        
        //return -sc;
    }

}
