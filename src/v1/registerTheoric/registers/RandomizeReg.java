/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.registerTheoric.registers;

/**
 *
 * @author Jahan
 */
public interface RandomizeReg {
    
    public IRegister next();
    public void seed(IRegister seed);

    public static class Impl implements RandomizeReg {

        final IRegister v;
        final IRegister seed;
        final RegisterUtilis x;
        final IRegFactory fact;

        public Impl(IRegFactory fact) {
            this.fact=fact;
            //IRegFactory fact = () -> new Reg64BitsBased(this.sz);

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
