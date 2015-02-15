/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

import v1.genericTesting.TestFramework;
import static v1.L64p.vrac.L64fbase.*;

/**
 *
 * @author denis
 */
public class Test_NumStruct64 {

    public static void main(String args[]) {
        //test001();
        //test002();
        testGroupTest();
    }
    
    public static void testGroupTest(){
        long a=0;
        
        NumStruct64 ns = new NumStruct64();
        int up=61;
        int down=17;
        
        ns.clear();
        ns.set(27, up);
        ns.set(28, down);
        a=L64fbase.setAt(0, 27, 1);a=L64fbase.setAt(a, 28, 1);
        System.out.println("before\n" + ns.out());
        ns.groupStep(a, 0);
        System.out.println("after\n" + ns.out()); 
        
        ns.clear();
        ns.set(27, up);
        ns.set(26, down);
        a=L64fbase.setAt(0, 27, 1);a=L64fbase.setAt(a, 26, 1);
        System.out.println("before\n" + ns.out());
        ns.groupStep(a, 0);
        System.out.println("after\n" + ns.out()); 
        
        ns.clear();
        ns.set(27, up);
        ns.set(35, down);
        a=L64fbase.setAt(0, 27, 1);a=L64fbase.setAt(a, 35, 1);
        System.out.println("before\n" + ns.out());
        ns.groupStep(a, 0);
        System.out.println("after\n" + ns.out()); 
        
        ns.clear();
        ns.set(19, up);
        ns.set(27, down);
        a=L64fbase.setAt(0, 19, 1);a=L64fbase.setAt(a, 27, 1);
        System.out.println("before\n" + ns.out());
        ns.groupStep(a, 0);
        System.out.println("after\n" + ns.out());         
        
        //TestFramework.assertEquals(model, result);
    }

    public static void test002(){
        NumStruct64 ns = new NumStruct64();
        ns.clear();
        System.out.println("before\n" + ns.out());     
        
        ns.set(27, 63);
        System.out.println("before\n" + ns.out()); 
        
    }
    
    public static void test001() {
        NumStruct64 ns = new NumStruct64();
        ns.identity();

        System.out.println("before\n" + ns.out());        
        
        while (ns.groupStep(-1L, 0)) {
           // System.out.println("steping\n" + ns.out());
        }
        
        L64fbase.gob64Struct gob=new L64fbase.gob64Struct();
        gob.init();
        gob.finishRandNoSuicide(0.0, 0);gob.passMove();
        System.out.println("phase "+gob.phase+" "+gob.debug_show());
        ns.findGroups(gob.p0, gob.p1);
        System.out.println("groups\n" + ns.out());   
        
        
    }
}
