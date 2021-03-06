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
        //dispRandFinishCrossEscap();
        //showDoubleKoGame();
        //showSimpleKoGame();
        //showTripleKoGame();
        
        //dispRandomCrossEmpty();
        
        dispAmafSample();
    }
    
    
    public static void dispAmafSample(){
        gob64Struct forRand=new gob64Struct();
        
        BiMap.AmafAccu acc=new BiMap.AmafAccu();
        
        forRand.init();
      
        final int nbGame = 400000;    
        
        long t0 = System.nanoTime();   
        double accCount=0;
        long rand=forRand.rand;
        BiMap amaf=new BiMap();
        for(int i=0;i<nbGame;i++){
            rand=rule30(rand);
            long div=rand;
            rand=rule30(rand);
            div&=rand;
            
            amaf.bamaf=rand;
            amaf.wamaf=~rand;
            
            rand=rule30(rand);
            rand=rule30(rand);
            long sc=0;
            
            //sc=rand&1;
            sc=(i%3)&1;
            //long sc=1;
            acc.addAmaf(amaf,sc);
            accCount+=sc;

        }
        
       long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        System.out.println("timeBitSet " + nbGame + " actions en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " actions par secondes");  
        System.out.println("avg count = "+(accCount/(double) nbGame));  
        System.out.println(""+acc.debug_out(true));
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

    public static void dispRandomCrossEmpty() {

        L64fbase.gob64Struct g = new gob64Struct();
        g.init();
        int pass = 0;
        for (int i = 0; i < 64 * 100; i++) {
            System.out.println(g.debug_show());
            long move = g.play_Empty_Cross_();
            if (move == 0) {
                pass++;
            } else {
                pass = 0;
            }
            if (pass == 2) {
                System.out.println("=========================== "+g.debug_show());
                g.reset();
            }
        }
        

    }

    public static void dispRandFinishCrossEscap() {
        L64fbase.gob64Struct g = new gob64Struct();
        g.init();
        int pass = 0;
        int count = 1;
        while (count > 0) {
            System.out.println(g.debug_show());

            boolean played = false;
            long move = 0;

            {
                long singleLib = 0;

                long c0 = 0;
                long c1 = 0;

                long lib;
                long empty = ~(g.p0 | g.p1);

                lib = lsh(empty);
                c1 |= c0 & lib;
                c0 ^= lib;
                lib = rsh(empty);
                c1 |= c0 & lib;
                c0 ^= lib;
                lib = empty >>> 8;
                c1 |= c0 & lib;
                c0 ^= lib;
                lib = empty << 8;
                c1 |= c0 & lib;
                c0 ^= lib;

                singleLib = c0 & ~c1 & g.p0;

                long hasNeigh = 0;
                hasNeigh |= lsh(g.p1);
                hasNeigh |= rsh(g.p1);
                hasNeigh |= ush(g.p1);
                hasNeigh |= dsh(g.p1);

                singleLib &= hasNeigh;
                singleLib = scramble(singleLib);
                singleLib &= empty;

                TargetDescr td = new TargetDescr();
                td.b = g.p0;
                td.w = g.p1;
                td.t = singleLib & ~(g.p0 | g.p1);

                //System.out.println("Phase"+g.phase+" SingleLib : "+td.out()); 
                move = g.playOneRandNoSuicide(~singleLib);
            }

            if (move == 0) {
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
                move = g.playOneRandNoSuicide(~hane);

            }

            if (move != 0) {

            } else {
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
