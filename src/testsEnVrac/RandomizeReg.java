/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsEnVrac;

import registerTheoric.IRegFactory;
import registerTheoric.IRegister;
import registerTheoric.Reg64BitsBased;
import registerTheoric.RegisterUtilis;

/**
 *
 * @author Jahan
 */
public interface RandomizeReg {
    
    public IRegister next();
    public void seed(IRegister seed);

    public static class Impl implements RandomizeReg {

        final int sz;
        final IRegister v;
        final RegisterUtilis x;

        public Impl(int sz) {
            this.sz = sz;
            IRegFactory fact = () -> new Reg64BitsBased(this.sz);

            v = fact.alloc();
            x=new RegisterUtilis(fact);

        }

        @Override
        public IRegister next() {
            x.applyRollRuleOn(v, "00011110");
            
            return v;
        }

        @Override
        public void seed(IRegister seed) {
            v.xor(seed);
        }

    }

}
