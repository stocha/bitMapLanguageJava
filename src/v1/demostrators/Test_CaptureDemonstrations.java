/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.demostrators;

import v1.L64p.vrac.TargetDescr;

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
        tst004Disp();
    }
    
    private void tst004Disp(){
        TargetDescr ed = new TargetDescr();
        String model = "<TARGET>\n"
                + "- - - - - - - - \n"
                + "- - - - - - - - \n"
                + "- - - X X - - - \n"
                + "- - X W W X - - \n"
                + "- O - - - - - - \n"
                + "- O - X O - - - \n"
                + "- - O O O - - - \n"
                + "- - - - - - - - \n";
        ed.inFromStr(model);    
        int nbGame=250000;
        DemoGraph dg=new DemoGraph(nbGame);
        DemoGraph.DemoGraphNode no=dg.new  DemoGraphNode(new TargetCaptureData( ed, 0),0);
        
                long t0 = System.nanoTime(); 
        for(int i=0;i<nbGame && !no.locked;i++){
            no.doSimulation();
        }
        
       long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        nbGame=(int)no.hits;
        System.out.println("search " + nbGame + " actions en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " actions par secondes");
        DemoGraph.printlnReuse();
        System.out.println(""+no.debugRec(0));
        
         
    }        
    
    private void tst003Disp(){
        TargetDescr ed = new TargetDescr();
        String model = "<TARGET>\n"
                + "O O X - X - X O \n"
                + "O O X X X X X O \n"
                + "O O X X X X O O \n"
                + "O O X W W X O O \n"
                + "O O - - - - O O \n"
                + "O O - X O - - O \n"
                + "O O O O O O O O \n"
                + "- O O O O O O - \n";
        ed.inFromStr(model);    
        int nbGame=25000*6;
        DemoGraph dg=new DemoGraph(nbGame);
        DemoGraph.DemoGraphNode no=dg.new  DemoGraphNode(new TargetCaptureData( ed, 0),0);
        
                long t0 = System.nanoTime(); 
        for(int i=0;i<nbGame && !no.locked;i++){
            no.doSimulation();
        }
        
       long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        nbGame=(int)no.hits;
        System.out.println("search " + nbGame + " actions en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " actions par secondes");
        DemoGraph.printlnReuse();
        System.out.println(""+no.debugRec(0));
        
         
    }    
    
    private void tst002Disp(){
        TargetDescr ed = new TargetDescr();
        String model = "<TARGET>\n"
                + "O O X - X - X O \n"
                + "O O X X X X X O \n"
                + "O O X X X X O O \n"
                + "O O X W W X O O \n"
                + "O O - - - X O O \n"
                + "O O X X X X O O \n"
                + "O O O O O O O O \n"
                + "- O O O O O O - \n";
        ed.inFromStr(model);    
        int nbGame=25000*2;
        DemoGraph dg=new DemoGraph(nbGame);
        DemoGraph.DemoGraphNode no=dg.new  DemoGraphNode(new TargetCaptureData( ed, 0),0);
        
                long t0 = System.nanoTime(); 
        for(int i=0;i<nbGame && !no.locked;i++){
            no.doSimulation();
        }
        
       long t1 = System.nanoTime();

        double t = (t1 - t0) / 1000000000.0;
        nbGame=(int)no.hits;
        System.out.println("search " + nbGame + " actions en " + t + " secondes");
        double nbgamSec = nbGame;
        nbgamSec /= t;
        System.out.println("" + nbgamSec + " actions par secondes");
        DemoGraph.printlnReuse();
        System.out.println(""+no.debugRec(0));
        
         
    }
    private void tst001Disp() {
        TargetDescr ed = new TargetDescr();
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
