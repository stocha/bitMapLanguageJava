package registerTheoric;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import v1.registerTheoric.registers.RegisterUtilis;
import v1.registerTheoric.registers.T2BitReg;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jahan
 */
public class TestT2BitReg {
    
    public TestT2BitReg() {
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
    public void testIt(){
        T2BitReg b=new T2BitReg();        
        b.xor(b);        
        assertEquals( "OO",RegisterUtilis.toString(b));        
        b.setAt(0, 1);
        assertEquals("XO",RegisterUtilis.toString(b) );
        b.shr();
        assertEquals("OX",RegisterUtilis.toString(b) );    
        b.setAt(1, 0);
        assertEquals( "OO",RegisterUtilis.toString(b));
    }
    
    
    @Test
    public void logic(){
        T2BitReg b=new T2BitReg();        
        T2BitReg a=new T2BitReg(); 

        RegisterUtilis.inputString(a,"OO" , 'X');
        RegisterUtilis.inputString(b,"OO" , 'X');
        a.or(b);
        assertEquals( "OO",RegisterUtilis.toString(a));      
        
        RegisterUtilis.inputString(a,"OX" , 'X');
        RegisterUtilis.inputString(b,"XO" , 'X');
        a.or(b);
        assertEquals( "XX",RegisterUtilis.toString(a));          
        
        
         RegisterUtilis.inputString(a,"XO" , 'X');
         a.shr();
         assertEquals( "OX",RegisterUtilis.toString(a)); 
         a.shr();
         assertEquals( "OO",RegisterUtilis.toString(a)); 
         
         
         RegisterUtilis.inputString(a,"OX" , 'X');
         a.shl(a.size()-1);
         assertEquals( "XO",RegisterUtilis.toString(a)); 
         
         
        RegisterUtilis.inputString(a,"OO" , 'X');
        RegisterUtilis.inputString(b,"XO" , 'X');
        a.or(b);
        assertEquals( "XO",RegisterUtilis.toString(a));          
        
    }    
}
