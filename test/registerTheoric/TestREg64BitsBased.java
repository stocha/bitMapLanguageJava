/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package registerTheoric;

import v1.registerTheoric.registers.RegisterUtilis;
import v1.registerTheoric.registers.Reg64BitsBased;
import v1.registerTheoric.registers.T2BitReg;
import v1.registerTheoric.registers.ComposedRegister;
import v1.registerTheoric.registers.IRegister;
import v1.registerTheoric.registers.IRegFactory;
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
public class TestREg64BitsBased {
    
    public TestREg64BitsBased() {
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
    
    
@Test
    public void rule30_basic1(){
        IRegFactory fact=() -> new Reg64BitsBased(11);
        IRegister b=fact.alloc();  
        IRegister v=fact.alloc(); 
        IRegister res=fact.alloc(); 
        b.xor(b);        
        assertEquals( "OOOOOOOOOOO",RegisterUtilis.toString(b));        
        b.setAt(5, 1);
        assertEquals("OOOOOXOOOOO",RegisterUtilis.toString(b) );
        
        IRegister i=b;
        v.cp(i);
        assertEquals("OOOOOXOOOOO",RegisterUtilis.toString(v) );
        v.shr();
        assertEquals("OOOOOOXOOOO",RegisterUtilis.toString(v) );
        res.or(v);
        assertEquals("OOOOOOXOOOO",RegisterUtilis.toString(res) );
        
        v.cp(i);
        v.shl();
        v.and(i);
        res.or(v);
        assertEquals("OOOOOOXOOOO",RegisterUtilis.toString(res) );
        
        v.cp(i);
        res.or(v);
        assertEquals("OOOOOXXOOOO",RegisterUtilis.toString(res) );
        
        v.cp(i);
        v.shl();
        res.or(v);      
        assertEquals("OOOOXXXOOOO",RegisterUtilis.toString(res) );
        
        i.cp(res);
        assertEquals("OOOOXXXOOOO",RegisterUtilis.toString(i) );
        
    }        

    public void compareToRefImpl(int nbBit){      
        
        int medSz=13;
        
        

            IRegFactory fact=() -> new T2BitReg();
            int nbBitsTot=nbBit;
            
            final int curmed=medSz;
            IRegFactory factMed1=() -> new ComposedRegister(curmed-1, fact);
            IRegFactory factHigh1=() -> new ComposedRegister(nbBitsTot, factMed1);
            
            IRegFactory factHigh2=() -> new Reg64BitsBased(nbBitsTot);            
            
            
            IRegister a1=factHigh1.alloc();  
            IRegister a2=factHigh2.alloc();  
            a1.xor(a1);    
            a2.xor(a2); 
            a1.setAt(15, 1);
            a2.setAt(15, 1);
        

            for(int i=0;i<200;i++){
                
                RegisterUtilis x=new RegisterUtilis(factHigh1);
                RegisterUtilis y=new RegisterUtilis(factHigh2);
                
                x.applyRollRuleOn(a1, "00011110");
                y.applyRollRuleOn(a2, "00011110");
                
                //System.out.println(""+RegisterUtilis.toString(a1,'#','.'));
                //System.out.println(""+RegisterUtilis.toString(a2,'X','.'));
                assertEquals(RegisterUtilis.toString(a1), RegisterUtilis.toString(a2));
            }
       
    
    }            
    
    @Test
    public void compareToRefImpl(){
        compareToRefImpl(191);
        compareToRefImpl(192);
        compareToRefImpl(193);
    }
}
