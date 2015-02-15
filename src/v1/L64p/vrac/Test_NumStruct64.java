/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

/**
 *
 * @author denis
 */
public class Test_NumStruct64 {

    public static void main(String args[]) {
        test001();
    }

    public static void test001() {
        NumStruct64 ns = new NumStruct64();
        ns.clear();

        System.out.println("before\n" + ns.out());        
        
        while (ns.groupStep(0, 0)) {
            System.out.println("steping\n" + ns.out());
        }
        
        L64fbase.gob64Struct gob=new L64fbase.gob64Struct();
        gob.init();
        gob.finishRandNoSuicide(0.0, 0);
        System.out.println(""+gob.debug_show());
        ns.findGroups(gob.p0, gob.p1);
        System.out.println("groups\n" + ns.out());   
        
        
    }
}
