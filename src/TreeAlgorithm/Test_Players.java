/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeAlgorithm;

import L64p.vrac.L64fbase;
import genericTesting.TestFramework;

/**
 *
 * @author denis
 */
public class Test_Players {

    public static void main(String args[]) {

        (new TestIt()).doit();

        TestFramework.showResults();
    }

    public static class TestIt {
        
        

        void TesNBenchtFlatOnBandit() {
            BanditManchotData band = new BanditManchotData(4);
            System.out.println("Theoritical values " + band.getSubData());

            FlatPlayer fp = new FlatPlayer(band, null);
            fp.deflat();

            int nbSim = 10000000;
            long t0 = System.nanoTime();
            for (int i = 0; i < nbSim; i++) {
                fp.doSimulation();
            }
            long t1 = System.nanoTime();
            System.out.println("Flat data " + fp.childs);

            double t = t1 - t0;
            t = t / 1000000;
            t = t / 1000;
            double simSec = nbSim / t;

            System.out.println("" + nbSim + " en " + t + " /  " + simSec + " act per second");

            System.out.println("best is " + fp.bestState());

        }
        
        
        void TesNBenchtUctOnBandit() {
            BanditManchotData band = new BanditManchotData(4);
            System.out.println("Theoritical values " + band.getSubData());

            UctPlayer fp = new UctPlayer(band, null);
            fp.deflat();

            int nbSim = 10000000;
            long t0 = System.nanoTime();
            for (int i = 0; i < nbSim; i++) {
                fp.doSimulation();
            }
            long t1 = System.nanoTime();
            System.out.println("Flat data " + fp.childs);

            double t = t1 - t0;
            t = t / 1000000;
            t = t / 1000;
            double simSec = nbSim / t;

            System.out.println("" + nbSim + " en " + t + " /  " + simSec + " act per second");

            System.out.println("best is " + fp.bestState());

        }        

        void TestNBenchFlatLight() {

            L64fbase.gob64Struct gob = new L64fbase.gob64Struct();
            Light64Data band = new Light64Data(gob, (double) 0.5,0);
            //System.out.println("Theoritical values "+band.getSubData());

            FlatPlayer fp = new FlatPlayer(band, null);
            fp.deflat();

            int nbSim = 640000;
            long t0 = System.nanoTime();
            for (int i = 0; i < nbSim; i++) {
                fp.doSimulation();
            }
            long t1 = System.nanoTime();

            for (FlatPlayer c : fp.childs) {
                System.out.println("Flat data " + c.showResNBoard());
            }

            double t = t1 - t0;
            t = t / 1000000;
            t = t / 1000;
            double simSec = nbSim / t;

            System.out.println("" + nbSim + " en " + t + " /  " + simSec + " act per second");

            System.out.println("best is " + fp.bestState());

        }

        void showFlatGame() {
            
            int pass=0;

            L64fbase.gob64Struct gob = new L64fbase.gob64Struct();
            long black=0;black=~black;
            black=black>>>8;
            black|=((long)(0xAA))<<(64-8);
            black=black>>>1;black&=L64fbase.RMASK;
            black=~black;
            gob.p0=black;
            final double komi=63;
            for (int mm = 0; mm < 128; mm++) {

                Light64Data band = new Light64Data(gob, komi,mm&1);
            //System.out.println("Theoritical values "+band.getSubData());

                FlatPlayer fp = new FlatPlayer(band, null);
                fp.deflat();

                int nbSim = 64000*2;
                long t0 = System.nanoTime();
                for (int i = 0; i < nbSim; i++) {
                    fp.doSimulation();
                }
                long t1 = System.nanoTime();

                double t = t1 - t0;
                t = t / 1000000;
                t = t / 1000;
                double simSec = nbSim / t;

                //System.out.println("" + nbSim + " en " + t + " /  " + simSec + " act per second");

                for(FlatPlayer chi:fp.childs){
                    System.out.println("" + chi);
                }
                
                System.out.println(""+gob.phase+" to play");
                System.out.println("best is " + fp.bestState());
                if(fp.bestState()==null){
                    pass++; gob.phase^=1;
                }
                else{
                    pass=0;
                    gob.copy(((Light64Data)fp.bestState()).mem);
                }
                
                if(pass>=2){ 
                    
                    System.out.println("final board :"+gob.debug_show());
                    System.out.println("final score = "+gob.finishRandNoSuicide(komi,mm&1));
                    
                    break;
                
                
                }
            }

        }
    
    
    
        void showUctGame() {
            
            int pass=0;

            L64fbase.gob64Struct gob = new L64fbase.gob64Struct();
            long black=0;black=~black;
            //black=black>>>8;
            //black|=((long)(0xAA))<<(64-8);
            //black=black>>>1;black&=L64fbase.RMASK;
            black=~black;
            gob.p0=black;
            final double komi=10.5;
            for (int mm = 0; mm < 128; mm++) {

                Light64Data band = new Light64Data(gob, komi,mm&1);
            //System.out.println("Theoritical values "+band.getSubData());

                UctPlayer fp = new UctPlayer(band, null);
                fp.deflat();

                int nbSim = 64000*2;
                long t0 = System.nanoTime();
                for (int i = 0; i < nbSim; i++) {
                    fp.doSimulation();
                }
                long t1 = System.nanoTime();

                double t = t1 - t0;
                t = t / 1000000;
                t = t / 1000;
                double simSec = nbSim / t;

                //System.out.println("" + nbSim + " en " + t + " /  " + simSec + " act per second");

                for(UctPlayer chi:fp.childs){
                    //System.out.println("" + chi);
                }
                
                System.out.println(""+gob.phase+" to play");
                System.out.println("best is " + fp.bestState());
                if(fp.bestState()==null){
                    pass++; gob.passMove();
                }
                else{
                    pass=0;
                    gob.copy(((Light64Data)fp.bestState()).mem);
                }
                
                if(pass>=2){ 
                    
                    System.out.println("final board :"+gob.debug_show());
                    System.out.println("final score = "+gob.scoreGame(komi, 0));
                    
                    break;
                
                
                }
            }

        }

        public void doit() {
            //TesNBenchtUctOnBandit();
            //TesNBenchtFlatOnBandit();
//            TestNBenchFlatLight();
            //showFlatGame();
            showUctGame();
        }
    }
}
