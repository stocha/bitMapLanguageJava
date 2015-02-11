/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

import v1.genericTesting.TestFramework;
import static v1.genericTesting.TestFramework.assertEquals;
import static v1.L64p.vrac.L64fbase.*;

/**
 *
 * @author denis
 */
public class Test_L64Bm_simpleFast {

    public static void main(String args[]) {

        (new TestBitMap()).doit();

        TestFramework.showResults();
    }

    public static class TestBitMap {

        TestBitMap() {
        }

        public void doit() {
            bmTest001();
            maskValues();
            gobTest001();
            testBitCount();
            testSelectNth();
            testPseudoEye();
            testScore();
            testExternalInterface();
            testConflictDetect();

        }

        public void testConflictDetect() {
            L64fbase.gob64Struct gob = new gob64Struct();
            gob.debug_input("<GOBAN>\n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - O O O - - - \n"
                    + "- - O X X O - - \n"
                    + "- - O - - X - - \n"
                    + "- - - O - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n");
            L64fbase.gob64Struct amaf01 = new gob64Struct();
            amaf01.debug_input("<GOBAN>\n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - X O - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n");

            gob64Struct conf;

            conf = amaf01;
            assertEquals("" + gob.isConflictingAmaf(conf.p0, conf.p1, 0), "false");

            L64fbase.gob64Struct amaf02 = new gob64Struct();
            amaf02.debug_input("<GOBAN>\n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - X O - - - \n"
                    + "- - - - X - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n");
            conf = amaf02;
            assertEquals("" + gob.isConflictingAmaf(conf.p0, conf.p1, 0), "true");

            conf = amaf02;
            assertEquals("" + gob.isConflictingAmaf(conf.p0, conf.p1, 1), "false");

            gob.passMove();
            conf = amaf02;
            assertEquals("" + gob.isConflictingAmaf(conf.p0, conf.p1, 0), "true");
            gob.passMove();

            L64fbase.gob64Struct amaf03 = new gob64Struct();
            amaf03.debug_input("<GOBAN>\n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - X - - - - \n"
                    + "- - - X O - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n");
            conf = amaf03;
            assertEquals("" + gob.isConflictingAmaf(conf.p0, conf.p1, 0), "true");

        }

        public void testScore() {
            L64fbase.gob64Struct gob = new gob64Struct();
            gob.debug_input("<GOBAN>\n"
                    + "O O O O O O O O \n"
                    + "O O - O O O - O \n"
                    + "O O O O O O O O \n"
                    + "O O O O O O O O \n"
                    + "X X X X X X X X \n"
                    + "X - X X - X X X \n"
                    + "X X X X X - X X \n"
                    + "- X - X X X X - \n");

            assertEquals("" + gob.scoreBoard(), "0");
            assertEquals("" + gob.scoreGame(63.0, 0), "-63.0");
            assertEquals("" + gob.scoreGame(63.0, 1), "63.0");

            gob.debug_input("<GOBAN>\n"
                    + "O O O O O O O O \n"
                    + "O O - O O O - O \n"
                    + "O O O O O O O O \n"
                    + "O O O O O O O O \n"
                    + "X X X O X X X X \n"
                    + "X - X X - X X X \n"
                    + "X X X X X - X X \n"
                    + "- X - X X X X - \n");

            assertEquals("" + gob.scoreBoard(), "-2");
            assertEquals("" + gob.scoreGame(63.0, 0), "-65.0");
            assertEquals("" + gob.scoreGame(63.0, 1), "65.0");
            gob.playOneRandNoSuicide();
            assertEquals("" + gob.scoreBoard(), "2");
            assertEquals("" + gob.scoreGame(63.0, 0), "-65.0");
            assertEquals("" + gob.scoreGame(63.0, 1), "65.0");
        }

        public void testExternalInterface() {
            L64fbase.gob64Struct gob = new gob64Struct();
            gob.debug_input("<GOBAN>\n"
                    + "O O O O O O O O \n"
                    + "- O O O O O O O \n"
                    + "O O O O O O O O \n"
                    + "O O O O O O O O \n"
                    + "X X X X X X X X \n"
                    + "X - X X - X X X \n"
                    + "X X X X X - X X \n"
                    + "- X - X X X X - \n");

            gob.forceNormalisedMove(8, 0);
            gob.forceNormalisedMove(16, 1);

            assertEquals("" + gob.debug_show(),
                    "<GOBAN>\n"
                    + "- - - - - - - - \n"
                    + "X - - - - - - - \n"
                    + "O - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "X X X X X X X X \n"
                    + "X - X X - X X X \n"
                    + "X X X X X - X X \n"
                    + "- X - X X X X - \n"
            );

            gob.debug_input("<GOBAN>\n"
                    + "O O O O O O O O \n"
                    + "- O O O O O O O \n"
                    + "O O O O O O O O \n"
                    + "O O O O O O O O \n"
                    + "X X X X X X X X \n"
                    + "X - X X - X X X \n"
                    + "X X X X X - X X \n"
                    + "- X - X X X X - \n");

            gob.passMove();

            try {
                gob.forceNormalisedMove(8, 1);
                assertEquals("Impossible path", "");
            } catch (Exception e) {
                assertEquals("playing suicidal move", e.getMessage());
            }

            gob.reset();
            gob.forceNormalisedMove(48, 0);
            assertEquals("" + gob.convertToNormalisedMove(gob.p1), "48");
            assertEquals("" + gob.convertToNormalisedMove(gob.p0), "-1");

        }

        public void testPseudoEye() {
            L64fbase.gob64Struct gob = new gob64Struct();
            gob.debug_input("<GOBAN>\n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n");

            assertEquals("<GOBAN>\n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n", gob.debug_show());

            gob.debug_input("<GOBAN>\n"
                    + "- X - X - X - X \n"
                    + "X O - X X O X X \n"
                    + "- - - - - - - - \n"
                    + "- - X O - - X - \n"
                    + "- X - X - X - X \n"
                    + "- - X O - O X - \n"
                    + "X - X - - - - - \n"
                    + "- X - X - - - - \n");

            assertEquals(outString(gob.pseudoEyes(), '#', '-'),
                    "<BITMAP>\n"
                    + "# - - - # - # - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - # - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n");
        }

        public void testSelectNth() {
            long m = -1L;
            long neg = -1L;

            for (int i = 0; i < 64; i++) {
                m = selectNth(neg, i);
                assertEquals("" + (m >>> i), "" + 1);
                assertEquals("" + (m), "" + (1L << i));
                //System.out.println(""+i+" "+m);
            }

            neg = 0;
            neg |= (1L << 6);
            neg |= (1L << 15);
            m = selectNth(neg, 0);
            assertEquals("" + (m), "" + (1L << 6));
            m = selectNth(neg, 1);
            assertEquals("" + (m), "" + (1L << 15));
        }

        public void testBitCount() {
            long mem = 0;
            for (int i = 0; i < 64; i++) {
                assertEquals("" + count(mem), "" + i);
                //System.out.println(""+count(mem)+" / "+i+" "+mem);
                mem |= 1L << (i);
            }
        }

        public void gobTest001() {
            String inGob = "<GOBAN>\n"
                    + "O O O X X X O X \n"
                    + "X - O O O O X X \n"
                    + "X X O X O X O X \n"
                    + "O O X O X X X X \n"
                    + "O O O X X # X X \n"
                    + "O O O X O O O X \n"
                    + "X X X O X X O X \n"
                    + "O O O X X X X X \n";
            long b0 = inputString(inGob, 0);
            long b1 = inputString(inGob, 1);

            assertEquals(outString(b0, b1),
                    "<GOBAN>\n"
                    + "O O O X X X O X \n"
                    + "X - O O O O X X \n"
                    + "X X O X O X O X \n"
                    + "O O X O X X X X \n"
                    + "O O O X X # X X \n"
                    + "O O O X O O O X \n"
                    + "X X X O X X O X \n"
                    + "O O O X X X X X \n"
            );
        }

        public void maskValues() {
            assertEquals(outString(tool_lMask(), 'X', 'O'), outString(LMASK, 'X', 'O'));
            assertEquals(outString(tool_rMask(), 'X', 'O'), outString(RMASK, 'X', 'O'));

            //System.out.println("LMask "+outString(LMASK, 'X', '-'));
            //System.out.println("RMask "+outString(RMASK, 'X', '-'));
            //System.out.println("Lmask \n"+outString(tool_lMask(), 'X', '-'));
            //System.out.println("Rmask \n"+outString(tool_rMask(), 'X', '-'));        
        }

        public void bmTest001() {
            long bm = 0;

            bm = inString('X', 'O', "<BITMAP>\n"
                    + "O O O X X X O X \n"
                    + "X X O O O O X X \n"
                    + "X X O X O X O X \n"
                    + "O O X O X X X X \n"
                    + "O O O X X O X X \n"
                    + "O O O X O O O X \n"
                    + "X X X O X X O X \n"
                    + "O O O X X X X X \n"
            );

            assertEquals(outString(bm, 'X', 'O'),
                    "<BITMAP>\n"
                    + "O O O X X X O X \n"
                    + "X X O O O O X X \n"
                    + "X X O X O X O X \n"
                    + "O O X O X X X X \n"
                    + "O O O X X O X X \n"
                    + "O O O X O O O X \n"
                    + "X X X O X X O X \n"
                    + "O O O X X X X X \n"
            );
            bm = setAt(bm, 3, 3, 1);
            assertEquals(outString(bm, 'X', 'O'),
                    "<BITMAP>\n"
                    + "O O O X X X O X \n"
                    + "X X O O O O X X \n"
                    + "X X O X O X O X \n"
                    + "O O X X X X X X \n"
                    + "O O O X X O X X \n"
                    + "O O O X O O O X \n"
                    + "X X X O X X O X \n"
                    + "O O O X X X X X \n"
            );

        }
    }// Testeur de bitMap

}
