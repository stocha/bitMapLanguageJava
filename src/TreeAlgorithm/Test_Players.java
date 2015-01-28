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

        void TestNBenchFlatLight() {

            L64fbase.gob64Struct gob = new L64fbase.gob64Struct();
            Light64Data band = new Light64Data(gob, (double) 0.5);
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

            L64fbase.gob64Struct gob = new L64fbase.gob64Struct();
            for (int mm = 0; mm < 128; mm++) {

                Light64Data band = new Light64Data(gob, (double) 0.5);
            //System.out.println("Theoritical values "+band.getSubData());

                FlatPlayer fp = new FlatPlayer(band, null);
                fp.deflat();

                int nbSim = 64000;
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

                System.out.println("best is " + fp.bestState());
                gob.copy(((Light64Data)fp.bestState()).mem);
            }

        }

        public void doit() {

            //TesNBenchtFlatOnBandit();
//            TestNBenchFlatLight();
            showFlatGame();
        }
    }
}
