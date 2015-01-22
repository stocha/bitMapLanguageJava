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
           // bmBase5_3();
           // testCopy();
           // testShift();
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

        }
//
//        public void bmBase5_3() {
//
//            RegBitMapsUtils x = new RegBitMapsUtils();
//            IRegFactory rf = () -> new Reg64BitsBased(256);
//            IRegBitMap bm = fact.alloc(5, 3);
//
//            x.intString(bm, 'X', 'O', "<BITMAP>\n"
//                    + "OOOXX\n"
//                    + "XXOOO\n"
//                    + "XXOXO\n");
//
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"
//                    + "OOOXX\n"
//                    + "XXOOO\n"
//                    + "XXOXO\n"
//            );
//
//        }
//
//        public void testCopy() {
//
//            RegBitMapsUtils x = new RegBitMapsUtils();
//            IRegBitMap bm = fact.alloc(22, 9);
//
//            x.intString(bm, 'X', 'O', "<BITMAP>\n"
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
//                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
//                    + "OXXXOXXOOOOOXXXOOOOXOO");
//
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
//                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
//                    + "OXXXOXXOOOOOXXXOOOOXOO\n"
//            );
//            
//            IRegBitMap bm2 = fact.alloc(5, 3);
//
//            x.intString(bm2, 'X', 'O', "<BITMAP>\n"
//                    + "OOOXX\n"
//                    + "XXOOO\n"
//                    + "XXOXO\n");
//
//            assertEquals(x.outString(bm2, 'X', 'O'),
//                    "<BITMAP>\n"
//                    + "OOOXX\n"
//                    + "XXOOO\n"
//                    + "XXOXO\n");
//
//            bm2.xor(bm2);
//            bm2.cp(bm, 5, 2, 3, 2, 0, 0);
//
//            assertEquals(x.outString(bm2, 'X', 'O'),
//                    "<BITMAP>\n"
//                    + "XOXOO\n"
//                    + "XXXOO\n"
//                    + "OOOOO\n");
//
//            bm2.xor(bm2);
//            bm2.cp(bm, 5, 2, 3, 2, 2, 1);
//
//            assertEquals(x.outString(bm2, 'X', 'O'),
//                    "<BITMAP>\n"
//                    + "OOOOO\n"
//                    + "OOXOX\n"
//                    + "OOXXX\n"
//            );
//            
//            bm.cp(bm2, 2, 1, 3, 2, 6, 3);
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
//                    + "OOXOXXXOXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
//                    + "OXXXOXXOOOOOXXXOOOOXOO\n"
//            );
//        }
//        
//        
//         public void testShift() {
//            RegBitMapsUtils x = new RegBitMapsUtils();
//            IRegBitMap bm = fact.alloc(22, 9);
//
//            x.intString(bm, 'X', 'O', "<BITMAP>\n"
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
//                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
//                    + "OXXXOXXOOOOOXXXOOOOXOO\n"
//            );
//            
//            bm.shiftd();
//
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"
//                    + "OOOOOOOOOOOOOOOOOOOOOO\n"
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
//                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
//                    
//            );     
//            
//            bm.shiftu();            
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"                    
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
//                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
//                    + "OOOOOOOOOOOOOOOOOOOOOO\n"
//                    
//            );              
//            
//            //====
//            x.intString(bm, 'X', 'O', "<BITMAP>\n"
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
//                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
//                    + "OXXXOXXOOOOOXXXOOOOXOO\n"                  
//                );
//            
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"                    
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
//                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
//                    + "OXXXOXXOOOOOXXXOOOOXOO\n"  
//            ); 
//            
//            bm.shiftr();
//            
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"                    
//                    + "OOOOXXXOXXOXXXXOOXXXOO\n"
//                    + "OXXOOOOXXOOXXXXXXXXXXO\n"
//                    + "OXXOXOXOXXOXOXXOOXXOXX\n"
//                    + "OOOXOXXXXXXOXOOXXOOXXO\n"
//                    + "OOOOXXOXXXXOOOXOOOXXXO\n"
//                    + "OOOOXOOOXOOOOXOOXXXOXO\n"
//                    + "OXXXOXXOXOXXOOXOXOOXXO\n"
//                    + "OOOOXXXXXXOOOOOXOXOOXX\n"
//                    + "OOXXXOXXOOOOOXXXOOOOXO\n" 
//            );             
//            
//            bm.shiftr();
//            
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"                    
//                    + "OOOOOXXXOXXOXXXXOOXXXO\n"
//                    + "OOXXOOOOXXOOXXXXXXXXXX\n"
//                    + "OOXXOXOXOXXOXOXXOOXXOX\n"
//                    + "OOOOXOXXXXXXOXOOXXOOXX\n"
//                    + "OOOOOXXOXXXXOOOXOOOXXX\n"
//                    + "OOOOOXOOOXOOOOXOOXXXOX\n"
//                    + "OOXXXOXXOXOXXOOXOXOOXX\n"
//                    + "OOOOOXXXXXXOOOOOXOXOOX\n"
//                    + "OOOXXXOXXOOOOOXXXOOOOX\n" 
//                    
//            );          
//            
//            bm.shiftl();
//            
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"                    
//                    + "OOOOXXXOXXOXXXXOOXXXOO\n"
//                    + "OXXOOOOXXOOXXXXXXXXXXO\n"
//                    + "OXXOXOXOXXOXOXXOOXXOXO\n"
//                    + "OOOXOXXXXXXOXOOXXOOXXO\n"
//                    + "OOOOXXOXXXXOOOXOOOXXXO\n"
//                    + "OOOOXOOOXOOOOXOOXXXOXO\n"
//                    + "OXXXOXXOXOXXOOXOXOOXXO\n"
//                    + "OOOOXXXXXXOOOOOXOXOOXO\n"
//                    + "OOXXXOXXOOOOOXXXOOOOXO\n" 
//                    
//            );              
//               
//            bm.shiftl();
//            
//            assertEquals(x.outString(bm, 'X', 'O'),
//                    "<BITMAP>\n"                    
//                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
//                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
//                    + "XXOXOXOXXOXOXXOOXXOXOO\n"
//                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
//                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
//                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
//                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
//                    + "OOOXXXXXXOOOOOXOXOOXOO\n"
//                    + "OXXXOXXOOOOOXXXOOOOXOO\n" 
//                    
//            );              
//         }
    }// Testeur de bitMap
    
}
