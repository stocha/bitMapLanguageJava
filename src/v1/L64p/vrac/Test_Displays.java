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
        //dispSomeFinishAccel();

       // dispNoConflictLogicGameMultipledispNoConflictLogicGameMultiple();
        //dispNoConflict();
        dispRandFinishCrossEscap();

        //showDoubleKoGame();
        //showSimpleKoGame();
        //showTripleKoGame();
    }

    public static void dispRandFinishCrossEscap() {
        L64fbase.gob64Struct g = new gob64Struct();
        g.init();
        int pass = 0;
        int count = 1;
        while (count > 0) {
           System.out.println(g.debug_show());

            
            boolean played=false;
            long move=0;
            {
                long hane = g.haneForNextPlayer();
//
//                hane |= (lsh(g.p0) >>> 8) & ((g.p1 >>> 8) | lsh(g.p1));
//                hane |= (rsh(g.p0) >>> 8) & ((g.p1 >>> 8) | rsh(g.p1));
//                hane |= (lsh(g.p0) << 8) & ((g.p1 << 8) | lsh(g.p1));
//                hane |= (rsh(g.p0) << 8) & ((g.p1 << 8) | rsh(g.p1));

                TargetDescr td = new TargetDescr();
                td.b = g.p0;
                td.w = g.p1;
                td.t = hane & ~(g.p0 | g.p1);

                //System.out.println("Phase"+g.phase+" Hane : "+td.out());
                
                move = g.playOneRandNoSuicide(~td.t);
                
                long escape = 0;
                long voidiag = -1L;
            }

            if(move!=0){
                
            }else{                
                move = g.playOneRandNoSuicide();
            }
            if (move == 0) {
                System.out.println("Passing : ");
                pass++;
                g.passMove();
            } else {
                pass = 0;
            }
            if (pass == 2) {
                System.out.println("Ending " + g.debug_show());
                g.reset();
                count--;
            }
        }

    }

    public static void dispNoConflict() {

        L64fbase.gob64Struct g = new gob64Struct();
        L64fbase.gob64Struct last = new gob64Struct();
        g.init();

        int pass = 0;
        for (int r = 0; r < 200; r++) {
            g.reset();
            g.randomizeAccelNoConflict();
            g.randomizeAccelNoConflict();
            g.randomizeAccelNoConflict();
            System.out.println("===== End ====" + "\n" + g.debug_show());
        }
    }

    public static void dispNoConflictLogicGameMultipledispNoConflictLogicGameMultiple() {
        L64fbase.gob64Struct g = new gob64Struct();
        L64fbase.gob64Struct last = new gob64Struct();
        g.init();

        int pass = 0;
        for (int r = 0; r < 200; r++) {
            g.reset();
            g.randomizeAccelNoConflict();
            pass = 0;
            int nbFill = 0;
            while (pass < 4) {
                if (last.equals(g)) {
                    pass++;
                } else {
                    pass = 0;
                }
                //System.out.println(g.debug_show());
                last.copy(g);
                g.randomizeAccelNoConflict();
                nbFill++;
            }
            System.out.println("===== End ====" + nbFill + "\n" + g.debug_show());
        }

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

    public static void dispSomeFinishAccel() {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        gob64Accel c = new gob64Accel(g);

        for (int i = 0; i < 40; i++) {
            g.reset();
            c.finishAccelGameNoConfl();

            System.out.println("=================");
            System.out.println(g.debug_show());
            c.spreadLastCapture(c.lastCapture);
            System.out.println("corrige " + g.debug_show());
        }
    }

    public static void dispRandFinishAccell() {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        gob64Accel c = new gob64Accel(g);

        for (int amaTot = 0; amaTot < 3; amaTot++) {
            c.prepareAccelGame();
            for (int i = 0; i < 1; i++) {
                //System.out.println("=================");
                c.hitSelectRand();
                c.stepAccelGameNoConflict();
                c.synchRefOut();
                System.out.println(g.debug_show());

                c.hitSelectRand();
                c.stepAccelGameNoConflict();
                c.synchRefOut();
                System.out.println(g.debug_show());

                System.out.println("+++conflicting+++");
                c.hitSelectRand();
                c.stepAccelGameWithConflict();
                c.synchRefOut();
                System.out.println(g.debug_show());

                c.synchRefOut();
                System.out.println(g.debug_show());
            }
            System.out.println("=================");
            c.hitSelectAll();
            c.stepAccelGameNoConflict();
            c.synchRefOut();
            System.out.println(g.debug_show());
        }// Amatot
        System.out.println("Last capture " + outString(c.lastCapture, ~c.lastCapture));
        c.spreadLastCapture(c.lastCapture);
        c.synchRefOut();
        System.out.println(g.debug_show());
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
