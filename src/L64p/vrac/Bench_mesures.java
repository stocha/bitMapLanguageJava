/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L64p.vrac;

/**
 *
 * @author denis
 */
public class Bench_mesures {

    public static void main(String args[]) {
        //dispScramble();
        //dispRandFill();
        //timeRandomGame();
        timeRandomNoSuicideGame();
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
        System.out.println("" + nbGame + " partie en " + t + " secondes");
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
