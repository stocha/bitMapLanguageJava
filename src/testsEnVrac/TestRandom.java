/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsEnVrac;

import registerTheoric.IRegFactory;
import registerTheoric.IRegister;
import registerTheoric.RandomizeReg;
import registerTheoric.Reg64BitsBased;
import registerTheoric.RegisterUtilis;

/**
 *
 * @author Jahan
 */
public class TestRandom {

    public static void main(String args[]) {
        doIt2();
    }
    
    
    public static void doIt2(){
        int sz=130;
        IRegFactory fact=()->new Reg64BitsBased(sz);
        IRegister mem=fact.alloc();
        RandomizeReg rr=new RandomizeReg.Impl(sz,fact);
        mem.setAt(13, 1);mem.setAt(32, 1);mem.setAt(47, 1);
        rr.seed(mem);
        
        for(int i=0;i<100;i++){
            IRegister r=rr.next();
            //System.out.println(RegisterUtilis.toString(r, '#', '.'));
        }
        mem.cp(rr.next());
        System.out.println("memSeed="+RegisterUtilis.toString(mem, '#', '.'));
        
        AggregateStatsOnRegister rso=new AggregateStatsOnRegister(sz);
        
        
        for(int i=0;i<320000;i++){
            IRegister r=rr.next();
            mem.cp(r);
            mem.and(rr.next());
            mem.and(rr.next());
            mem.and(rr.next());
            mem.and(rr.next());
            //System.out.println(RegisterUtilis.toString(r, '#', '.'));
            rso.input(mem);
        }
        
        System.out.println(""+rso.dispCount(6));
        
    }

    public static void doIt() {
        IRegFactory fact = () -> new Reg64BitsBased(128);
        RegisterUtilis x = new RegisterUtilis(fact);

        IRegister e = fact.alloc();
        IRegister r = fact.alloc();
        IRegister s = fact.alloc();
        IRegister t = fact.alloc();
        e.setAt(0, 1);
        r.setAt(32, 1);
        s.setAt(64, 1);
        t.setAt(96, 1);

        IRegister f = fact.alloc();

        for (int i = 0; i < 200; i++) {

            s.cp(e);
            t.cp(e);
            System.out.println(x.outString(e, 'E', '.'));
            //System.out.println(x.outString(r, 'R','.'));
            //System.out.println(x.outString(s, 'S','.'));
            //System.out.println(x.outString(t, 'T','.'));
            x.applyRollRuleOn(r, "01101001");
            x.applyRollRuleOn(s, "01011010");
            x.applyRollRuleOn(t, "10010110");

            r.not();
            e.cp(s);
            e.and(r);

            r.not();
            f.cp(t);
            f.and(r);

            e.or(f);

            f.cp(r);
            r.xor(s);
            s.xor(t);
            t.xor(f);

        }
    }
}
