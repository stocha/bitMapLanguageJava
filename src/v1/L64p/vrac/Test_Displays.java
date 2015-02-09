/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

import static v1.L64p.vrac.L64fbase.*;

/**
 *
 * @author denis
 */
public class Test_Displays {

    public static void main(String args[]) {
        //dispScramble();
        //dispRandFillAccell();
        //showRandomGame();
        //showRandomEnd();
        //dispRandFinishAccell();
        dispSomeFinishAccel();

        //showDoubleKoGame();
        //showSimpleKoGame();
        //showTripleKoGame();
    }

    public static void showRandomEnd() {
        L64fbase.gob64Struct g = new gob64Struct();
        g.init();
        int pass = 0;
        int count = 1000;
        while (count > 0) {
            //System.out.println(g.debug_show());
            long move = g.playOneRandNoSuicide();
            if (move == 0) {
                pass++;
            } else {
                pass = 0;
            }
            if (pass == 2) {
                System.out.println(g.debug_show());
                g.reset();
                count--;
            }
        }
        //System.out.println(g.debug_show());
    }

    public static void showSimpleKoGame() {
        L64fbase.gob64Struct g = new gob64Struct();
        g.init();
        g.debug_input("<GOBAN>\n"
                + "O O O O O O O O \n"
                + "O O - O O O - O \n"
                + "O O O O O O O O \n"
                + "X O O O X O - O \n"
                + "X X O X X X O X \n"
                + "X X X X X X X X \n"
                + "X X X X X X X X \n"
                + "X X - X X X X - \n"
        );

        int pass = 0;
        for (int i = 0; i < 64 * 3; i++) {
            System.out.println("" + g.phase + "|" + g.debug_show());
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
        System.out.println("Final : " + g.debug_show());
    }

    public static void showDoubleKoGame() {
        L64fbase.gob64Struct g = new gob64Struct();
        g.init();
        g.debug_input("<GOBAN>\n"
                + "O O O O O O O O \n"
                + "O O - O O O - O \n"
                + "O O O O O O O O \n"
                + "X O O O X O - O \n"
                + "- X O X X X O X \n"
                + "X X X X X X X X \n"
                + "X X X X X X X X \n"
                + "X X - X X X X - \n"
        );

        int pass = 0;
        for (int i = 0; i < 64 * 3; i++) {
            System.out.println("" + g.phase + "|" + g.debug_show());
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
        System.out.println("Final : " + g.debug_show());
    }

    public static void showTripleKoGame() {
        L64fbase.gob64Struct g = new gob64Struct();
        g.init();
        g.debug_input("<GOBAN>\n"
                + "O O O O O O O O \n"
                + "O O - O O O - O \n"
                + "O O O O O O O O \n"
                + "X O - O X O - O \n"
                + "- X O X X X O X \n"
                + "X X X X X X X X \n"
                + "X X X X X X X X \n"
                + "X X - X X X X - \n"
        );

        int pass = 0;
        for (int i = 0; i < 64 * 3; i++) {
            System.out.println("" + g.phase + "|" + g.debug_show());
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
        System.out.println("Final : " + g.debug_show());
    }

    public static void showRandomGame() {
        L64fbase.gob64Struct g = new gob64Struct();
        g.init();
        int pass = 0;
        for (int i = 0; i < 64 * 100; i++) {
            System.out.println(g.debug_show());
            long move = g.playOneRandNoSuicide();
            if (move == 0) {
                pass++;
            } else {
                pass = 0;
            }
            if (pass == 2) {
                g.reset();
            }
        }
        System.out.println(g.debug_show());
    }

    public static void dispScramble() {
        long m = 0;
        m = setAt(m, 3, 7, 1);
        m = setAt(m, 2, 2, 1);
        System.out.println(outString(m, 'X', '-'));
        for (int j = 0; j < 5; j++) {
            m = scramble(m);
            System.out.println(outString(m, 'X', '-'));
        }
    }
    
    
    public static void dispSomeFinishAccel(){
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        gob64Accel c = new gob64Accel(g);
        
        
        for(int i=0;i<40;i++){
            g.reset();
            c.finishAccelGameNoConfl();
            
            System.out.println("=================");
            System.out.println(g.debug_show());
            c.spreadLastCapture(c.lastCapture);
            System.out.println("corrige "+g.debug_show());
        }
    }

    public static void dispRandFinishAccell() {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        gob64Accel c = new gob64Accel(g);

        for (int amaTot = 0; amaTot < 3; amaTot++) {
            c.prepareAccelGame();
            for (int i = 0; i < 4; i++) {
                //System.out.println("=================");
                c.hitSelectRand();
                c.stepAccelGameNoConflict();
                c.synchRefOut();

                //System.out.println(g.debug_show());
            }
            System.out.println("=================");
            c.hitSelectAll();
            c.stepAccelGameNoConflict();
            c.synchRefOut();

            System.out.println(g.debug_show());
            System.out.println("Last capture "+outString(c.lastCapture, ~c.lastCapture));
        }// Amatot
    }

    public static void dispRandFillAccell() {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        for (int i = 0; i < 40; i++) {
            g.randomizeAccelNoConflict();
            

            System.out.println(g.debug_show());
        }
    }
}
