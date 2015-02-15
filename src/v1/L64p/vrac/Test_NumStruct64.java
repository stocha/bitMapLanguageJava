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
        
        public static void test001(){
            NumStruct64 ns=new NumStruct64();
            ns.clear();
            
            System.out.println(""+ns.out());
        }
}
