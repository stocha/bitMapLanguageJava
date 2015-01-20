/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registerTheoric.bitMaps;

import registerTheoric.registers.RegisterUtilis;
import registerTheoric.bitMaps.IRegBitMap;
import registerTheoric.registers.IRegFactory;
import registerTheoric.registers.Reg64BitsBased;
import genericTesting.TestFramework;
import static genericTesting.TestFramework.*;
import registerTheoric.bitMaps.RegBitMapsUtils;

/**
 *
 * @author denis
 */
public class FastTest_RegBm256 {

    public static void main(String args[]) {
        IRegFactory rf = () -> new Reg64BitsBased(256);
        IRegBitMap.Factory fact = (w, h) -> new IRegBitMap.RegBitMapImpl(w, h, rf);

        (new TestAIRegBitMap(fact)).doit();

        TestFramework.showResults();
    }

    public static class TestAIRegBitMap {

        final IRegBitMap.Factory fact;

        TestAIRegBitMap(IRegBitMap.Factory fact) {
            this.fact = fact;
        }

        public void doit() {
            bmTest001();
            bmBase5_3();
            testCopy();
            testShift();
        }

        public void bmTest001() {

            RegBitMapsUtils x = new RegBitMapsUtils();
            //IRegFactory fact = () -> new Reg64BitsBased(256);
            IRegBitMap bm = fact.alloc(22, 9);

            x.intString(bm, 'X', 'O', "<BITMAP>\n"
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    + "OXXXOXXOOOOOXXXOOOOXOO");

            assertEquals(x.outString(bm, 'X', 'O'),
                    "<BITMAP>\n"
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    + "OXXXOXXOOOOOXXXOOOOXOO\n"
            );

        }

        public void bmBase5_3() {

            RegBitMapsUtils x = new RegBitMapsUtils();
            IRegFactory rf = () -> new Reg64BitsBased(256);
            IRegBitMap bm = fact.alloc(5, 3);

            x.intString(bm, 'X', 'O', "<BITMAP>\n"
                    + "OOOXX\n"
                    + "XXOOO\n"
                    + "XXOXO\n");

            assertEquals(x.outString(bm, 'X', 'O'),
                    "<BITMAP>\n"
                    + "OOOXX\n"
                    + "XXOOO\n"
                    + "XXOXO\n"
            );

        }

        public void testCopy() {

            RegBitMapsUtils x = new RegBitMapsUtils();
            IRegBitMap bm = fact.alloc(22, 9);

            x.intString(bm, 'X', 'O', "<BITMAP>\n"
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    + "OXXXOXXOOOOOXXXOOOOXOO");

            assertEquals(x.outString(bm, 'X', 'O'),
                    "<BITMAP>\n"
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    + "OXXXOXXOOOOOXXXOOOOXOO\n"
            );
            
            IRegBitMap bm2 = fact.alloc(5, 3);

            x.intString(bm2, 'X', 'O', "<BITMAP>\n"
                    + "OOOXX\n"
                    + "XXOOO\n"
                    + "XXOXO\n");

            assertEquals(x.outString(bm2, 'X', 'O'),
                    "<BITMAP>\n"
                    + "OOOXX\n"
                    + "XXOOO\n"
                    + "XXOXO\n");

            bm2.xor(bm2);
            bm2.cp(bm, 5, 2, 3, 2, 0, 0);

            assertEquals(x.outString(bm2, 'X', 'O'),
                    "<BITMAP>\n"
                    + "XOXOO\n"
                    + "XXXOO\n"
                    + "OOOOO\n");

            bm2.xor(bm2);
            bm2.cp(bm, 5, 2, 3, 2, 2, 1);

            assertEquals(x.outString(bm2, 'X', 'O'),
                    "<BITMAP>\n"
                    + "OOOOO\n"
                    + "OOXOX\n"
                    + "OOXXX\n"
            );
            
            bm.cp(bm2, 2, 1, 3, 2, 6, 3);
            assertEquals(x.outString(bm, 'X', 'O'),
                    "<BITMAP>\n"
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXOXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    + "OXXXOXXOOOOOXXXOOOOXOO\n"
            );
        }
        
        
         public void testShift() {
            RegBitMapsUtils x = new RegBitMapsUtils();
            IRegBitMap bm = fact.alloc(22, 9);

            x.intString(bm, 'X', 'O', "<BITMAP>\n"
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    + "OXXXOXXOOOOOXXXOOOOXOO\n"
            );
            
            bm.shiftd();

            assertEquals(x.outString(bm, 'X', 'O'),
                    "<BITMAP>\n"
                    + "OOOOOOOOOOOOOOOOOOOOOO\n"
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    
            );     
            
            bm.shiftu();            
            assertEquals(x.outString(bm, 'X', 'O'),
                    "<BITMAP>\n"                    
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    + "OOOOOOOOOOOOOOOOOOOOOO\n"
                    
            );              
            
            //====
            x.intString(bm, 'X', 'O', "<BITMAP>\n"
                    + "OOOXXXOXXOXXXXOOXXXOOO\n"
                    + "XXOOOOXXOOXXXXXXXXXXOO\n"
                    + "XXOXOXOXXOXOXXOOXXOXXO\n"
                    + "OOXOXXXXXXOXOOXXOOXXOO\n"
                    + "OOOXXOXXXXOOOXOOOXXXOO\n"
                    + "OOOXOOOXOOOOXOOXXXOXOO\n"
                    + "XXXOXXOXOXXOOXOXOOXXOO\n"
                    + "OOOXXXXXXOOOOOXOXOOXXO\n"
                    + "OXXXOXXOOOOOXXXOOOOXOO\n"                  
                );
            
            
            bm.shiftr();
            
            assertEquals(x.outString(bm, 'X', 'O'),
                    "<BITMAP>\n"                    
                    + "OOOOXXXOXXOXXXXOOXXXOO\n"
                    + "OXXOOOOXXOOXXXXXXXXXXO\n"
                    + "OXXOXOXOXXOXOXXOOXXOXX\n"
                    + "OOOXOXXXXXXOXOOXXOOXXO\n"
                    + "OOOOXXOXXXXOOOXOOOXXXO\n"
                    + "OOOOXOOOXOOOOXOOXXXOXO\n"
                    + "OXXXOXXOXOXXOOXOXOOXXO\n"
                    + "OOOOXXXXXXOOOOOXOXOOXX\n"
                    + "OOXXXOXXOOOOOXXXOOOOXO\n" 
            );             
            
            bm.shiftr();
            
            assertEquals(x.outString(bm, 'X', 'O'),
                    "<BITMAP>\n"                    
                    + "OOOOOXXXOXXOXXXXOOXXXO\n"
                    + "OOXXOOOOXXOOXXXXXXXXXX\n"
                    + "OOXXOXOXOXXOXOXXOOXXOX\n"
                    + "OOOOXOXXXXXXOXOOXXOOXX\n"
                    + "OOOOOXXOXXXXOOOXOOOXXX\n"
                    + "OOOOOXOOOXOOOOXOOXXXOX\n"
                    + "OOXXXOXXOXOXXOOXOXOOXX\n"
                    + "OOOOOXXXXXXOOOOOXOXOOX\n"
                    + "OOOXXXOXXOOOOOXXXOOOOX\n" 
                    
            );            
               
         }
    }// Testeur de bitMap

}
