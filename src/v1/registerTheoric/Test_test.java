/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.registerTheoric;
import v1.registerTheoric.registers.RegisterUtilis;
import v1.registerTheoric.bitMaps.IRegBitMap;
import v1.registerTheoric.registers.IRegFactory;
import v1.registerTheoric.registers.Reg64BitsBased;
import static v1.genericTesting.TestFramework.*;
import v1.registerTheoric.bitMaps.RegBitMapsUtils;

/**
 *
 * @author denis
 */
public class Test_test {
    
    public static void main(String args[]){
        Test_test t=new Test_test();
        t.test();
    }
    
    public void test(){
        basicBm();
        bmBase5_3();
        testCopy();
        
        showResults();
    }
    
    public void basicBm() {
    
    RegBitMapsUtils x=new RegBitMapsUtils();
    IRegFactory fact=()->new Reg64BitsBased(256);
    IRegBitMap bm=new IRegBitMap.RegBitMapImpl(22, 9, fact);
    
    x.intString(bm, 'X', 'O', "<BITMAP>\n" +
"OOOXXXOXXOXXXXOOXXXOOO\n" +
"XXOOOOXXOOXXXXXXXXXXOO\n" +
"XXOXOXOXXOXOXXOOXXOXXO\n" +
"OOXOXXXXXXOXOOXXOOXXOO\n" +
"OOOXXOXXXXOOOXOOOXXXOO\n" +
"OOOXOOOXOOOOXOOXXXOXOO\n" +
"XXXOXXOXOXXOOXOXOOXXOO\n" +
"OOOXXXXXXOOOOOXOXOOXXO\n" +
"OXXXOXXOOOOOXXXOOOOXOO");
    
        assertEquals(x.outString(bm, 'X', 'O'), 
                "<BITMAP>\n" +
"OOOXXXOXXOXXXXOOXXXOOO\n" +
"XXOOOOXXOOXXXXXXXXXXOO\n" +
"XXOXOXOXXOXOXXOOXXOXXO\n" +
"OOXOXXXXXXOXOOXXOOXXOO\n" +
"OOOXXOXXXXOOOXOOOXXXOO\n" +
"OOOXOOOXOOOOXOOXXXOXOO\n" +
"XXXOXXOXOXXOOXOXOOXXOO\n" +
"OOOXXXXXXOOOOOXOXOOXXO\n" +
"OXXXOXXOOOOOXXXOOOOXOO\n"
                );
    
    
    }    
    
    public void bmBase5_3() {
    
    RegBitMapsUtils x=new RegBitMapsUtils();
    IRegFactory fact=()->new Reg64BitsBased(256);
    IRegBitMap bm=new IRegBitMap.RegBitMapImpl(5, 3, fact);
    
    x.intString(bm, 'X', 'O', "<BITMAP>\n" +
"OOOXX\n" +
"XXOOO\n" +
"XXOXO\n" );
    
        assertEquals(x.outString(bm, 'X', 'O'), 
                 "<BITMAP>\n" +
"OOOXX\n" +
"XXOOO\n" +
"XXOXO\n"
                );
    
    
    }    
    
    public static void testCopy() {
    
    RegBitMapsUtils x=new RegBitMapsUtils();
    IRegFactory fact=()->new Reg64BitsBased(256);
    IRegBitMap bm=new IRegBitMap.RegBitMapImpl(22, 9, fact);
    
    x.intString(bm, 'X', 'O', "<BITMAP>\n" +
"OOOXXXOXXOXXXXOOXXXOOO\n" +
"XXOOOOXXOOXXXXXXXXXXOO\n" +
"XXOXOXOXXOXOXXOOXXOXXO\n" +
"OOXOXXXXXXOXOOXXOOXXOO\n" +
"OOOXXOXXXXOOOXOOOXXXOO\n" +
"OOOXOOOXOOOOXOOXXXOXOO\n" +
"XXXOXXOXOXXOOXOXOOXXOO\n" +
"OOOXXXXXXOOOOOXOXOOXXO\n" +
"OXXXOXXOOOOOXXXOOOOXOO");
    
        assertEquals(x.outString(bm, 'X', 'O'), 
                "<BITMAP>\n" +
"OOOXXXOXXOXXXXOOXXXOOO\n" +
"XXOOOOXXOOXXXXXXXXXXOO\n" +
"XXOXOXOXXOXOXXOOXXOXXO\n" +
"OOXOXXXXXXOXOOXXOOXXOO\n" +
"OOOXXOXXXXOOOXOOOXXXOO\n" +
"OOOXOOOXOOOOXOOXXXOXOO\n" +
"XXXOXXOXOXXOOXOXOOXXOO\n" +
"OOOXXXXXXOOOOOXOXOOXXO\n" +
"OXXXOXXOOOOOXXXOOOOXOO\n"
                );
    
    IRegFactory fact2=()->new Reg64BitsBased(256);
    IRegBitMap bm2=new IRegBitMap.RegBitMapImpl(5, 3, fact);
    
    x.intString(bm2, 'X', 'O', "<BITMAP>\n" +
"OOOXX\n" +
"XXOOO\n" +
"XXOXO\n" );
    
        assertEquals(x.outString(bm2, 'X', 'O'), 
                 "<BITMAP>\n" +
"OOOXX\n" +
"XXOOO\n" +
"XXOXO\n"   );
        
        bm2.xor(bm2);
        bm2.cp(bm, 5, 2, 3, 2,0,0);
        
         assertEquals(x.outString(bm2, 'X', 'O'), 
                 "<BITMAP>\n" +
"XOXOO\n" +
"XXXOO\n" +
"OOOOO\n"   );   
         
      bm2.xor(bm2);
        bm2.cp(bm, 5, 2, 3, 2,2,1);
        
         assertEquals(x.outString(bm2, 'X', 'O'), 
                 "<BITMAP>\n" +
"OOOOO\n"+
"OOXOX\n" +
"OOXXX\n" 
   );   
        
    }          
}
