/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

import v1.L64p.vrac.CaptureDemonstrations.ExoDescr;
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
    
    private void doit(){
        ExoDescr ed=new CaptureDemonstrations.ExoDescr();
        ed.ExoDescrFromStr("<GOBAN>\n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - O O O - - - \n"
                    + "- - O B B O - - \n"
                    + "- - W - E X - - \n"
                    + "- - - O - - - - \n"
                    + "- - - - - - - - \n"
                    + "- - - - - - - - \n");
        System.out.println(""+ed.out());
    }
        
}
