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
        IRegister l;
        IRegister c=fact.alloc();
        IRegister r;
        IRegister v=fact.alloc();
       
        IRegister lc;
        IRegister cc;
        IRegister rc;
               
        
        res.xor(res);
        
        RegisterUtilis x=new RegisterUtilis(fact);
        
        
        c.cp(i);
        l=x.ror(i);
        r=x.rol(i);

        //System.out.println("lnp : "+RegisterUtilis.toString(l));        
        //System.out.println("cnp : "+RegisterUtilis.toString(c));        
        //System.out.println("rnp : "+RegisterUtilis.toString(r));
        
        v.cp(x.and(x.nop(l), x.and(x.not(c),x.not(r))));
        //System.out.println("100 : "+RegisterUtilis.toString(v));
        res.or(v);
        
        v.cp(x.and(x.not(l), x.and(x.nop(c),x.nop(r))));
        //System.out.println("011 : "+RegisterUtilis.toString(v));
        res.or(v);
        
        v.cp(x.and(x.not(l), x.and(x.nop(c),x.not(r))));
        //System.out.println("010 : "+RegisterUtilis.toString(v));
        res.or(v);
        
        v.cp(x.and(x.not(l), x.and(x.not(c),x.nop(r))));
        //System.out.println("001 : "+RegisterUtilis.toString(v));
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
    public void rule30_onBase2(){      
        
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
    
    @Test
    public void rule30_onBase2_disp(){      
        
        IRegFactory fact=() -> new T2BitReg();
        IRegFactory factBig=() -> new ComposedRegister(128, fact);
        IRegister b=factBig.alloc();  
        b.xor(b);             
        b.setAt(15, 1);
        System.out.println(""+RegisterUtilis.toString(b,'X','.'));
        RegisterUtilis x=new RegisterUtilis(factBig);

        for(int i=0;i<200;i++){
            x.applyRollRuleOn(b, "00011110");
            System.out.println(""+RegisterUtilis.toString(b,'X','.'));
        }
    
    }     
    
    
    @Test
    public void multiSubSize(){      
        
        int medSz;
        
        
        for(medSz=3;medSz<32;medSz++){
            IRegFactory fact=() -> new T2BitReg();
            
            final int curmed=medSz;
            IRegFactory factMed1=() -> new ComposedRegister(curmed-1, fact);
            IRegFactory factHigh1=() -> new ComposedRegister(128, factMed1);
            
            IRegFactory factMed2=() -> new ComposedRegister(curmed, fact);
            IRegFactory factHigh2=() -> new ComposedRegister(128, factMed2);            
            
            
            IRegister a1=factHigh1.alloc();  
            IRegister a2=factHigh2.alloc();  
            a1.xor(a1);    
            a2.xor(a2); 
            a1.setAt(15, 1);
            a2.setAt(15, 1);
        

            for(int i=0;i<200;i++){
                
                RegisterUtilis x=new RegisterUtilis(factHigh2);
                
                apply30On(a1, factHigh1);
                x.applyRollRuleOn(a2, "00011110");
                
                assertEquals(RegisterUtilis.toString(a1), RegisterUtilis.toString(a2));
            }
        }
       
    
    }            
}
