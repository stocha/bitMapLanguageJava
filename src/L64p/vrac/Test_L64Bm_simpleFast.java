/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L64p.vrac;

import genericTesting.TestFramework;
import static genericTesting.TestFramework.assertEquals;
import static L64p.vrac.L64fbase.*;

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
            //rule30Test();
           
        }
        
        public void testSelectNth(){
            long m=-1L;
            long neg=-1L;
            
            for(int i=0;i<64;i++){
                m=selectNth(neg,i);
                assertEquals(""+(m>>>i), ""+1);
                assertEquals(""+(m), ""+(1L<<i));
                //System.out.println(""+i+" "+m);
            }
            
            neg=0;neg|=(1L<<6);neg|=(1L<<15);
            m=selectNth(neg,0);
            assertEquals(""+(m), ""+(1L<<6));
            m=selectNth(neg,1);
            assertEquals(""+(m), ""+(1L<<15));
        }
        
        public void testBitCount(){
            long mem=0;
            for(int i=0;i<64;i++){
                assertEquals(""+count(mem), ""+i);
                //System.out.println(""+count(mem)+" / "+i+" "+mem);
                mem|=1L<<(i);
            }
        }
        
        public void gobTest001(){
            String inGob="<GOBAN>\n"
                    + "O O O X X X O X \n"
                    + "X - O O O O X X \n"
                    + "X X O X O X O X \n"
                    + "O O X O X X X X \n"
                    + "O O O X X # X X \n"
                    + "O O O X O O O X \n"
                    + "X X X O X X O X \n"
                    + "O O O X X X X X \n";
            long b0=inputString( inGob,0);
            long b1=inputString( inGob,1);


            assertEquals(outString(b0,b1),
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
        
        public void rule30Test(){
            long m=1L<<32;
            
            for(int i=0;i<1;i++){
                m=rule30(m);
                m=rule30(m);
                //System.out.printf("%64s\n",Long.toBinaryString(m));
                //System.out.println(""+outString(m, 'X', '-'));
            }
            
        }

        public void maskValues(){
            assertEquals(outString(tool_lMask(), 'X', 'O'),outString(LMASK, 'X', 'O'));
            assertEquals(outString(tool_rMask(), 'X', 'O'),outString(RMASK, 'X', 'O'));
            
            //System.out.println("LMask "+outString(LMASK, 'X', '-'));
            //System.out.println("RMask "+outString(RMASK, 'X', '-'));
            
            //System.out.println("Lmask \n"+outString(tool_lMask(), 'X', '-'));
            //System.out.println("Rmask \n"+outString(tool_rMask(), 'X', '-'));        
        }
        public void bmTest001() {
            long bm = 0;

            bm=inString( 'X', 'O', "<BITMAP>\n"
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
            bm=setAt(bm, 3, 3, 1);
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
