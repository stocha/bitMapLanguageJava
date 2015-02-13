/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.bots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import v1.L64p.vrac.L64fbase;
import v1.TreeAlgorithm.BoardData;

/**
 *
 * @author denis
 */
public class HeavyRecursData implements BoardData {

    public final L64fbase.gob64Struct mem = new L64fbase.gob64Struct();
    final double komi;
    final int metaphase;
    
    static long rand=9888478;
    
    int mm=0;
    
    final static int NbHyperSims=3000;
    public static final ComparatorWithInitialState comp = new ComparatorWithInitialState();
    static final IGoBot a=new UctLightBot(7878786L,NbHyperSims);
    static final IGoBot b=new UctLightBot(9987L,NbHyperSims);
    static {
        comp.setBots(a, b);
        comp.setUp();        
    }

    public HeavyRecursData(L64fbase.gob64Struct src, double komi,int metaphase) {
        mem.copy(src);
        this.komi = komi;
        this.metaphase=metaphase^1;
          
    }

    @Override
    public boolean equals(Object obj) {
        HeavyRecursData o=(HeavyRecursData)obj;
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
            mv.add(new HeavyRecursData(sim,komi,metaphase));
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
        comp.setInitialState(mem.debug_show());
        mm=0;

        
        //comp.setGameSpooler((String gameDesc) -> {
        //    if (mm++ < 300) {
                //System.out.println("Simulated move "+mm+" " + gameDesc);
        //    }
        //});                      
       // System.out.println("==================    SCORE ONCE ================");
        double sc= comp.doOneScore();
        
        if(metaphase==0) return sc; else return -sc;
        
        //return 0.0;
        
        //double sc=comp.
        //if(sc > 0) return 1.0; else return 0.0;        
        //return -sc;
    }

}
