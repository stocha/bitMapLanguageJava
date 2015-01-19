/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registerTheoric;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author denis
 */
public class TestIRegBitMap {
    
    public TestIRegBitMap() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
    @Test
    public void hello() {
    
    RegisterUtilis x=new RegisterUtilis(null);
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
    
    
       @Test
    public void bmBase5_3() {
    
    RegisterUtilis x=new RegisterUtilis(null);
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
    
    @Test
    public void testCopy() {
    
    RegisterUtilis x=new RegisterUtilis(null);
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
