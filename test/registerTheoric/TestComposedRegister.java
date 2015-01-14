package registerTheoric;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class TestComposedRegister {
    
    public TestComposedRegister() {
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
    public void keep2(){      
        ComposedRegister b=new ComposedRegister(2, () -> new T2BitReg());        
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
    public void basicTest(){      
        ComposedRegister b=new ComposedRegister(11, () -> new T2BitReg());        
        b.xor(b);        
        assertEquals( "OOOOOOOOOOO",RegisterUtilis.toString(b));        
        b.setAt(0, 1);
        assertEquals("XOOOOOOOOOO",RegisterUtilis.toString(b) );
        b.shr();
        assertEquals("OXOOOOOOOOO",RegisterUtilis.toString(b) );    
        b.setAt(1, 0);
        assertEquals( "OOOOOOOOOOO",RegisterUtilis.toString(b));
    }
    
    
    public void apply30On(IRegister i,IRegFactory fact){
        IRegister res=fact.alloc();
        IRegister l=fact.alloc();
        IRegister c=fact.alloc();
        IRegister r=fact.alloc();
        IRegister v=fact.alloc();
       
        IRegister lc;
        IRegister cc;
        IRegister rc;
               
        
        res.xor(res);
        
        RegisterUtilis x=new RegisterUtilis(fact);
        
        
        c.cp(i);
        l.cp(i);l.shr();
        r.cp(i);r.shl();

        System.out.println("lnp : "+RegisterUtilis.toString(l));        
        System.out.println("cnp : "+RegisterUtilis.toString(c));        
        System.out.println("rnp : "+RegisterUtilis.toString(r));
        
        v.cp(x.and(x.nop(l), x.and(x.not(c),x.not(r))));
        System.out.println("100 : "+RegisterUtilis.toString(v));
        res.or(v);
        
        v.cp(x.and(x.not(l), x.and(x.nop(c),x.nop(r))));
        System.out.println("011 : "+RegisterUtilis.toString(v));
        res.or(v);
        
        v.cp(x.and(x.not(l), x.and(x.nop(c),x.not(r))));
        System.out.println("010 : "+RegisterUtilis.toString(v));
        res.or(v);
        
        v.cp(x.and(x.not(l), x.and(x.not(c),x.nop(r))));
        System.out.println("001 : "+RegisterUtilis.toString(v));
        res.or(v);
        
        i.cp(res);
    }
    
    @Test
    public void rule30_basic1(){
        IRegFactory fact=() -> new T2BitReg();
        IRegister b=new ComposedRegister(11, fact);  
        IRegister v=new ComposedRegister(11, fact);  
        IRegister res=new ComposedRegister(11, fact);  
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
    
    
    @Test
    public void rule30_basic2(){
        IRegFactory fact=() -> new T2BitReg();
        IRegister b=new ComposedRegister(11, fact);  
        IRegister v=new ComposedRegister(11, fact);  
        IRegister res=new ComposedRegister(11, fact);  
      
        RegisterUtilis.inputString(b,"OOOOXXXOOOO",'X');
        assertEquals("OOOOXXXOOOO",RegisterUtilis.toString(b) );
        
        IRegister i=b;
        v.cp(i);
        assertEquals("OOOOXXXOOOO",RegisterUtilis.toString(v) );
        v.shr();
        assertEquals("OOOOOXXXOOO",RegisterUtilis.toString(v) );
        res.or(v);
        assertEquals("OOOOOXXXOOO",RegisterUtilis.toString(res) );
        
        v.cp(i);
        v.shl();
        v.and(i);
        assertEquals("OOOOXXOOOOO",RegisterUtilis.toString(v) );
        res.or(v);
        assertEquals("OOOOXXXXOOO",RegisterUtilis.toString(res) );
        
        
    }       
    
    @Test
    public void rule30(){      
        
        IRegFactory fact=() -> new T2BitReg();
        ComposedRegister b=new ComposedRegister(11, fact);  
        b.xor(b);        
        assertEquals( "OOOOOOOOOOO",RegisterUtilis.toString(b));        
        b.setAt(5, 1);
        assertEquals("OOOOOXOOOOO",RegisterUtilis.toString(b) );
        
        apply30On(b, ()->{
            return new ComposedRegister(11, fact);
        });
        assertEquals("OOOOXXXOOOO",RegisterUtilis.toString(b) );    
        
        apply30On(b, ()->{
            return new ComposedRegister(11, fact);
        });
        assertEquals("OOOXXOOXOOO",RegisterUtilis.toString(b) );  
        apply30On(b, ()->{
            return new ComposedRegister(11, fact);
        });        
        assertEquals("OOXXOXXXXOO",RegisterUtilis.toString(b) ); 
        apply30On(b, ()->{
            return new ComposedRegister(11, fact);
        });        
        assertEquals("OXXOOXOOOXO",RegisterUtilis.toString(b) );         
    }    
}
