/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.registerTheoric.goban;

import v1.genericTesting.TestFramework;
import static v1.genericTesting.TestFramework.assertEquals;
import v1.registerTheoric.bitMaps.IRegBitMap;
import v1.registerTheoric.bitMaps.RegBitMapsUtils;
import v1.registerTheoric.registers.IRegFactory;
import v1.registerTheoric.registers.Reg64BitsBased;

/**
 *
 * @author denis
 */
public class Test_goban {

    public static void main(String args[]) {
        IRegFactory rf = () -> new Reg64BitsBased(256);
        IRegBitMap.Factory bmFact = (w, h) -> new IRegBitMap.RegBitMapImpl(w, h, rf);
        IGoban.Factory gobFact = (w, h) -> new IGoban.IGobanBitMap(w, h, bmFact);

        (new TestSimples(bmFact,gobFact)).doit();

        TestFramework.showResults();
    }

    public static class TestSimples {

        final IRegBitMap.Factory fact;
        final IGoban.Factory gobFact;

        TestSimples(IRegBitMap.Factory fact, IGoban.Factory gobFact) {
            this.fact = fact;
            this.gobFact = gobFact;
        }

        public void doit() {
            testInputOutput();
        }

        public void testInputOutput() {

            IGoban gob = gobFact.alloc(5, 3);
            assertEquals("<GOBAN>\n"
                    + "-----\n"
                    + "-----\n"
                    + "-----\n",
                    gob.outString()
            );
            
            gob.setAt(1, 1, 3);
            assertEquals("<GOBAN>\n"
                    + "-----\n"
                    + "-#---\n"
                    + "-----\n",
                    gob.outString()
            );            

            gob.inputString("<GOBAN>\n"
                    + "-X-O-\n"
                    + "--X-#\n"
                    + "#OX-#\n"
            );

            assertEquals("<GOBAN>\n"
                    + "-X-O-\n"
                    + "--X-#\n"
                    + "#OX-#\n",
                    gob.outString()
            );

        }

    }// Testeur de bitMap    
}
