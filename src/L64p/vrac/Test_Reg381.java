/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L64p.vrac;

import genericTesting.TestFramework;
import static genericTesting.TestFramework.assertEquals;

/**
 *
 * @author denis
 */
public class Test_Reg381 {
    public static void main(String args[]) {

        (new Test_Reg381()).doit();
        TestFramework.showResults();
    }    
    
    public void testSimple(){
        Reg381 a=new Reg381();
        Reg381 b=new Reg381();
        Reg381 op1=new Reg381();
        Reg381 op2=new Reg381();
        
        a.setAt(0, 1);
        b.setAt(380, 1);
        
        op1.cp(a);
        for(int i=0;i<380;i++){
            op1.regShr(1);
            //System.out.println("shr "+op1.debug_regOut());
        }
        assertEquals(op1.debug_regOut(), b.debug_regOut());
        op1.cp(b);
        for(int i=0;i<380;i++){
            op1.regShl(1);
            //System.out.println("shl "+op1.debug_regOut());
        }    
        assertEquals(op1.debug_regOut(), a.debug_regOut());
        
        
        
        op1.cp(a);op1.regRol(1);
        assertEquals(op1.debug_regOut(), b.debug_regOut());
        op1.cp(b);op1.regRor(1);
        assertEquals(op1.debug_regOut(), a.debug_regOut());        
        
        //System.out.println(a.debug_regOut());
        //System.out.println(b.debug_regOut());
        
        op1.cp(a);
        op2.cp(b);
        op1.not();
        op2.not();
        for(int i=0;i<381;i++){
            op1.regRol(1);
            op1.not();
            op2.regRor(1);
            op2.not();
            //System.out.println(op1.debug_regOut());
        }
        //System.out.println(op1.debug_regOut());
        assertEquals(op1.debug_regOut(), a.debug_regOut());
        assertEquals(op2.debug_regOut(), b.debug_regOut());      
    }
    
    public void testRegistre(){
        Reg381 a=new Reg381();
        Reg381 b=new Reg381();
        Reg381 op1=new Reg381();
        Reg381 op2=new Reg381();
        
        a.setAt(190, 1);
        final Reg381 buff[]=Reg381.allocBuff(10);
        for(int i=0;i<390*2;i++){
            System.out.println(a.debug_regOut());
            //a.rule30(buff);
            a.randHash(buff);
        }
        System.out.println(a.debug_regOut());
    }
            
    public void doit(){
        testSimple();
        testRegistre();
    }
}
