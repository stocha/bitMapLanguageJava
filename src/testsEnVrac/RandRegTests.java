/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testsEnVrac;

import registerTheoric.registers.IRegFactory;
import registerTheoric.registers.IRegister;
import registerTheoric.registers.Reg64BitsBased;
import registerTheoric.registers.RegisterUtilis;

/**
 *
 * @author Jahan
 */
public interface RandRegTests {
    
    public IRegister next();
    public void seed(IRegister seed);

    public static class Impl implements RandRegTests {

        final int sz;
        final IRegister v;
        final IRegister seed;
        final RegisterUtilis x;

        public Impl(int sz) {
            this.sz = sz;
            IRegFactory fact = () -> new Reg64BitsBased(this.sz);

            v = fact.alloc();
            seed = fact.alloc();
            x=new RegisterUtilis(fact);

        }

        @Override
        public IRegister next() {
            v.xor(seed);
            x.applyRollRuleOn(v, "00011110");
            x.applyRollRuleOn(v, "00011110");
            
            
            return v;
        }

        @Override
        public void seed(IRegister seed) {
            this.seed.cp(seed);
        }

    }

}

