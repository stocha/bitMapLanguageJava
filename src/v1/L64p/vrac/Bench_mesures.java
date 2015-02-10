/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

/**
 *
 * @author denis
 */
public class Bench_mesures {

    public static void main(String args[]) {
        //dispScramble();
        //dispRandFill();
        //timeRandomGame();
        //timeRandomNoSuicideGame();
        //timeRandomNoSuicideGameAccelerated();
        
        //timeSomeFinishAccel();
        //time391RandHash(); time391RandHash();
        time391RandomBit(); time391RandomBit();
        //timeBitSet();timeBitSet();
    }
    
    
    
    public static void timeBitSet(){
       Reg381 r[]=Reg381.allocBuff(10);
       
       Reg381 rh=Reg381.alloc();
       Reg381 rbit=Reg381.alloc();
       rh.setAt(190, 1);
       long rand=1;
       
        final int nbGame = 6000000*4;    
        
        long t0 = System.nanoTime();   
        double accCount=0;
        
        for(int i=0;i<nbGame;i++){
            rh.randHash(r);
            //accCount+=Math.abs(rh.count()-190.5);
            rbit.setAt((int)(rand&0xFFFF%381), 1);
            rand=L64fbase.rule30(rand);
            //System.out.println("=================");
            //System.out.println(g.debug_show());
        }
        
       long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        System.out.println("timeBitSet " + nbGame + " actions en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " actions par secondes");  
        System.out.println("avg count = "+(accCount/(double) nbGame));        
    }    
    
    public static void time391RandomBit(){
       Reg381 r[]=Reg381.allocBuff(10);
       
       Reg381 rh=Reg381.alloc();
       Reg381 divid=Reg381.alloc();
       Reg381 rbit=Reg381.alloc();
       rh.setAt(190, 1);
       divid.setAt(56, 1);divid.setAt(23, 1);
       for(int i=0;i<100;i++){
           rh.randHash(r);
           divid.randHash(r);
       }
       long rand=1;
       
        final int nbGame = 6000000;    
        
        long t0 = System.nanoTime();   
        double accCount=0;
        
        for(int i=0;i<nbGame;i++){
            rh.randHash(r);
            //accCount+=Math.abs(rh.count()-190.5);
            r[1].cp(rh);
            r[1].and(divid);
            rand=rbit.randomSelectOneBitFrom(r[1], rand);
            //System.out.println("=================");
            //System.out.println(g.debug_show());
        }
        
       long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        System.out.println("time391RandHash " + nbGame + " actions en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " actions par secondes");  
        System.out.println("avg count = "+(accCount/(double) nbGame));        
    }
   public static void time391RandHash(){
       Reg381 r[]=Reg381.allocBuff(10);
       
       Reg381 rh=Reg381.alloc();
       rh.setAt(190, 1);
       
       
        final int nbGame = 20000000;    
        
        long t0 = System.nanoTime();   
        double accCount=0;
        
        for(int i=0;i<nbGame;i++){
            rh.randHash(r);
            accCount+=Math.abs(rh.count()-190.5);
            //System.out.println("=================");
            //System.out.println(g.debug_show());
        }
        
       long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        System.out.println("time391RandHash " + nbGame + " actions en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " actions par secondes");  
        System.out.println("avg count = "+(accCount/(double) nbGame));
    }    
    
      
    public static void timeSomeFinishAccel(){
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        L64fbase.gob64Accel c = new L64fbase.gob64Accel(g);
        
        final int nbGame = 200000;    
        
        long t0 = System.nanoTime();    
        
        for(int i=0;i<nbGame;i++){
            g.reset();
            c.finishAccelGameNoConfl();
            //System.out.println("=================");
            //System.out.println(g.debug_show());
        }
        
       long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        System.out.println("timeRandomNoSuicideGameAccelerated " + nbGame + " partie en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " parties par secondes");        
        
    }

    public static void timeRandomNoSuicideGameAccelerated() {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        long t0 = System.nanoTime();
        for (int ran = 0; ran < 4; ran++) {
            g.randomizeAccelNoConflict();
        }

        final int nbGame = 200000;
        for (int big = 0; big < nbGame; big++) {
            int pass = 0;
            for (int i = 0; i < 64 * 3; i++) {
                //System.out.println(g.debug_show());
                long move = g.playOneRandNoSuicide();
                if (move == 0) {
                    pass++;
                } else {
                    pass = 0;
                }
                if (pass == 2) {
                    break;
                }
            }
            //System.out.println(g.debug_show());
            g.reset();
            for (int ran = 0; ran < 4; ran++) {
                g.randomizeAccelNoConflict();
            }
        }
        long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        System.out.println("timeRandomNoSuicideGameAccelerated " + nbGame + " partie en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " parties par secondes");
    }

    public static void timeRandomNoSuicideGame() {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        long t0 = System.nanoTime();

        final int nbGame = 100000;
        for (int big = 0; big < nbGame; big++) {
            int pass = 0;
            for (int i = 0; i < 64 * 3; i++) {
                //System.out.println(g.debug_show());
                long move = g.playOneRandNoSuicide();
                if (move == 0) {
                    pass++;
                } else {
                    pass = 0;
                }
                if (pass == 2) {
                    break;
                }
            }
            //System.out.println(g.debug_show());
            g.reset();
        }
        long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        System.out.println(" timeRandomNoSuicideGame " + nbGame + " partie en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " parties par secondes");
    }

    public static void timeRandomGame() {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        long t0 = System.nanoTime();

        final int nbGame = 100000;
        for (int big = 0; big < nbGame; big++) {
            for (int i = 0; i < 64 * 3; i++) {
                //System.out.println(g.debug_show());
                long move = g.playOneRandomMove();
                if (move == 0) {
                    g.reset();
                }
            }
            //System.out.println(g.debug_show());
            g.reset();
        }
        long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        System.out.println("" + nbGame + " partie en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " parties par secondes");
    }
}
