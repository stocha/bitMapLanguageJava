/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.registerTheoric.registers;

import v1.genericTesting.TestFramework;
import static v1.genericTesting.TestFramework.assertEquals;

/**
 *
 * @author denis
 */
public class Test_ComposedRegister {

    public static void main(String args[]) {
        IRegFactory rf = () -> new T2BitReg();

        (new TestComposed(rf)).doit();
        
        IRegFactory rf2 = () -> new Reg64BitsBased(3);
        (new TestComposed(rf2)).doit();

        TestFramework.showResults();
    }

    public static class TestComposed {

        final IRegFactory fact;

        TestComposed(IRegFactory fact) {
            this.fact = fact;
        }

        public void doit() {
            keep2();
            basicCopy();
            basicTest();
            rule30_basic1();
            rule30_basic2();
            rule30_onBase2();
            multiSubSize();
            notShift();
            //rule30_onBase2_disp();
        }

        public void keep2() {
            ComposedRegister b = new ComposedRegister(2, fact);
            b.xor(b);
            assertEquals("OO", RegisterUtilis.toString(b));
            b.setAt(0, 1);
            assertEquals("XO", RegisterUtilis.toString(b));
            b.shr();
            assertEquals("OX", RegisterUtilis.toString(b));
            b.setAt(1, 0);
            assertEquals("OO", RegisterUtilis.toString(b));
        }

        public void basicTest() {
            ComposedRegister b = new ComposedRegister(11, fact);
            b.xor(b);
            assertEquals("OOOOOOOOOOO", RegisterUtilis.toString(b));
            b.setAt(0, 1);
            assertEquals("XOOOOOOOOOO", RegisterUtilis.toString(b));
            b.shr();
            assertEquals("OXOOOOOOOOO", RegisterUtilis.toString(b));
            b.setAt(1, 0);
            assertEquals("OOOOOOOOOOO", RegisterUtilis.toString(b));
        }

        public void basicCopy() {
            ComposedRegister b = new ComposedRegister(11, fact);
            ComposedRegister c = new ComposedRegister(11, fact);

            RegisterUtilis x = new RegisterUtilis(null);
            x.inString(b, "OOXOOOXXOXO", 'X');
            assertEquals("OOXOOOXXOXO", RegisterUtilis.toString(b));
            c.xor(c);
            c.cp(b, 2, 5);
            assertEquals("OOXOOOXOOOO", RegisterUtilis.toString(c));
            c.xor(c);
            c.not();
            c.cp(b, 2, 5);
            assertEquals("XXXOOOXXXXX", RegisterUtilis.toString(c));

        }

        public void apply30On(IRegister i, IRegFactory fact) {
            IRegister res = fact.alloc();
            IRegister l;
            IRegister c = fact.alloc();
            IRegister r;
            IRegister v = fact.alloc();

            IRegister lc;
            IRegister cc;
            IRegister rc;

            res.xor(res);

            RegisterUtilis x = new RegisterUtilis(fact);

            c.cp(i);
            l = x.ror(i);
            r = x.rol(i);

        //System.out.println("lnp : "+RegisterUtilis.toString(l));        
            //System.out.println("cnp : "+RegisterUtilis.toString(c));        
            //System.out.println("rnp : "+RegisterUtilis.toString(r));
            v.cp(x.and(x.nop(l), x.and(x.not(c), x.not(r))));
            //System.out.println("100 : "+RegisterUtilis.toString(v));
            res.or(v);

            v.cp(x.and(x.not(l), x.and(x.nop(c), x.nop(r))));
            //System.out.println("011 : "+RegisterUtilis.toString(v));
            res.or(v);

            v.cp(x.and(x.not(l), x.and(x.nop(c), x.not(r))));
            //System.out.println("010 : "+RegisterUtilis.toString(v));
            res.or(v);

            v.cp(x.and(x.not(l), x.and(x.not(c), x.nop(r))));
            //System.out.println("001 : "+RegisterUtilis.toString(v));
            res.or(v);

            i.cp(res);
        }

        public void rule30_basic1() {
            IRegister b = new ComposedRegister(11, fact);
            IRegister v = new ComposedRegister(11, fact);
            IRegister res = new ComposedRegister(11, fact);
            b.xor(b);
            assertEquals("OOOOOOOOOOO", RegisterUtilis.toString(b));
            assertEquals("false", "" + b.tstNot0());
            assertEquals("0", "" + b.countBitsAtOne());
            b.not();
            assertEquals("true", "" + b.tstNot0());
            assertEquals("11", "" + b.countBitsAtOne());
            b.not();

            b.setAt(5, 1);
            assertEquals("OOOOOXOOOOO", RegisterUtilis.toString(b));

            IRegister i = b;
            v.cp(i);
            assertEquals("OOOOOXOOOOO", RegisterUtilis.toString(v));
            v.shr();
            assertEquals("OOOOOOXOOOO", RegisterUtilis.toString(v));
            res.or(v);
            assertEquals("OOOOOOXOOOO", RegisterUtilis.toString(res));

            v.cp(i);
            v.shl();
            v.and(i);
            res.or(v);
            assertEquals("OOOOOOXOOOO", RegisterUtilis.toString(res));

            v.cp(i);
            res.or(v);
            assertEquals("OOOOOXXOOOO", RegisterUtilis.toString(res));

            v.cp(i);
            v.shl();
            res.or(v);
            assertEquals("OOOOXXXOOOO", RegisterUtilis.toString(res));

            i.cp(res);
            assertEquals("OOOOXXXOOOO", RegisterUtilis.toString(i));

        }

        public void rule30_basic2() {
            IRegister b = new ComposedRegister(11, fact);
            IRegister v = new ComposedRegister(11, fact);
            IRegister res = new ComposedRegister(11, fact);

            RegisterUtilis.inputString(b, "OOOOXXXOOOO", 'X');
            assertEquals("OOOOXXXOOOO", RegisterUtilis.toString(b));

            IRegister i = b;
            v.cp(i);
            assertEquals("OOOOXXXOOOO", RegisterUtilis.toString(v));
            v.shr();
            assertEquals("OOOOOXXXOOO", RegisterUtilis.toString(v));
            res.or(v);
            assertEquals("OOOOOXXXOOO", RegisterUtilis.toString(res));

            v.cp(i);
            v.shl();
            v.and(i);
            assertEquals("OOOOXXOOOOO", RegisterUtilis.toString(v));
            res.or(v);
            assertEquals("OOOOXXXXOOO", RegisterUtilis.toString(res));

        }

        public void rule30_onBase2() {
            ComposedRegister b = new ComposedRegister(11, fact);
            b.xor(b);
            assertEquals("OOOOOOOOOOO", RegisterUtilis.toString(b));
            b.setAt(5, 1);
            assertEquals("OOOOOXOOOOO", RegisterUtilis.toString(b));

            apply30On(b, () -> {
                return new ComposedRegister(11, fact);
            });
            assertEquals("OOOOXXXOOOO", RegisterUtilis.toString(b));

            apply30On(b, () -> {
                return new ComposedRegister(11, fact);
            });
            assertEquals("OOOXXOOXOOO", RegisterUtilis.toString(b));
            apply30On(b, () -> {
                return new ComposedRegister(11, fact);
            });
            assertEquals("OOXXOXXXXOO", RegisterUtilis.toString(b));
            apply30On(b, () -> {
                return new ComposedRegister(11, fact);
            });
            assertEquals("OXXOOXOOOXO", RegisterUtilis.toString(b));
        }

        //@Test
        public void rule30_onBase2_disp() {

            IRegFactory factBig = () -> new ComposedRegister(128, fact);
            IRegister b = factBig.alloc();
            b.xor(b);
            b.setAt(15, 1);
            System.out.println("" + RegisterUtilis.toString(b, 'X', '.'));
            RegisterUtilis x = new RegisterUtilis(factBig);

            for (int i = 0; i < 200; i++) {
                x.applyRollRuleOn(b, "10010110");
                System.out.println("" + RegisterUtilis.toString(b, 'X', '.'));
            }

        }

        //@Test
        public void multiSubSize() {

            int medSz;

            for (medSz = 13; medSz < 17; medSz++) {

                final int curmed = medSz;
                IRegFactory factMed1 = () -> new ComposedRegister(curmed - 1, fact);
                IRegFactory factHigh1 = () -> new ComposedRegister(128, factMed1);

                IRegFactory factMed2 = () -> new ComposedRegister(curmed, fact);
                IRegFactory factHigh2 = () -> new ComposedRegister(128, factMed2);

                IRegister a1 = factHigh1.alloc();
                IRegister a2 = factHigh2.alloc();
                a1.xor(a1);
                a2.xor(a2);
                a1.setAt(15, 1);
                a2.setAt(15, 1);

                for (int i = 0; i < 200; i++) {

                    RegisterUtilis x = new RegisterUtilis(factHigh2);

                    apply30On(a1, factHigh1);
                    x.applyRollRuleOn(a2, "00011110");

                    assertEquals(RegisterUtilis.toString(a1), RegisterUtilis.toString(a2));
                }
            }

        }

        public void notShift() {
            ComposedRegister b = new ComposedRegister(11, fact);
            b.xor(b);
            assertEquals("OOOOOOOOOOO", RegisterUtilis.toString(b));
            b.not();
            b.shl();
            b.shl();
            assertEquals("XXXXXXXXXOO", RegisterUtilis.toString(b));
            b.shr();
            b.shr();
            assertEquals("OOXXXXXXXXX", RegisterUtilis.toString(b));
           
        }
    }

}
