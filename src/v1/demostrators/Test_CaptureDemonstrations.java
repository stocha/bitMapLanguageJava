/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.demostrators;

import v1.L64p.vrac.L64fbase;
import v1.demostrators.DemonstrationWithTarget;
import v1.demostrators.DemonstrationWithTarget.TargetDescr;
import v1.genericTesting.TestFramework;

/**
 *
 * @author denis
 */
public class Test_CaptureDemonstrations {

    public static void main(String args[]) {

        (new Test_CaptureDemonstrations()).doit();
        TestFramework.showResults();
    }

    private void doit() {
        TargetDescr ed = new DemonstrationWithTarget.TargetDescr();
        String model = "<TARGET>\n"
                + "- - - - - - - - \n"
                + "- - - - - - - - \n"
                + "- - O O O - - - \n"
                + "- - O B B O - - \n"
                + "- - W - E X - - \n"
                + "- - - O - - - - \n"
                + "- - - - - - - - \n"
                + "- - - - - - - - \n";
        ed.inFromStr(model);
        TestFramework.assertEquals(model, "" + ed.out());
        ed.invert();
        TestFramework.assertEquals("<TARGET>\n"
                + "- - - - - - - - \n"
                + "- - - - - - - - \n"
                + "- - X X X - - - \n"
                + "- - X W W X - - \n"
                + "- - B - E O - - \n"
                + "- - - X - - - - \n"
                + "- - - - - - - - \n"
                + "- - - - - - - - ", "" + ed.out());

        String probcapt = "<TARGET>\n"
                + "- - - - - - - - \n"
                + "- - - - - - - - \n"
                + "- - X X X - - - \n"
                + "- - X W W X - - \n"
                + "- - - - - O - - \n"
                + "- - - X - - - - \n"
                + "- - - - - - - - \n"
                + "- - - - - - - - ";
        ed.inFromStr(probcapt);
        ed.t=ed.scrblZone(1);
        System.out.println("scrbl 1 " + ed.out());
    }

}
