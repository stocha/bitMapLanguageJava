/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.bots;

import v1.L64p.vrac.L64fbase;
import v1.TreeAlgorithm.BoardData;
import v1.TreeAlgorithm.Light64AmafSrcData;
import v1.TreeAlgorithm.Light64Data;
import v1.TreeAlgorithm.UctGraph;

/**
 *
 * @author denis
 */
public class Test_botComparison {
    public static int mm=0;
    
    public static void main(String args[]){
        ComparatorWithInitialState comp=new ComparatorWithInitialState();
        //comp.setInitialState(BibliothequePosTest.acs_p25_e18_lowAdd_m2);
        
        //comp.setBots(new FlatBot(7878786L,64*1000), new FlatBot(9991112L,32*1000));
        
        //comp.setBots(new UctLightBot(7878786L,8*1000), new FlatBot(9991112L,16*1000));
        //comp.setBots(new UctAccelLightBot(7878786L,9*1000), new UctLightBot(9991112L,5*1000));
        //comp.setBots(new UctAccelLightBot(7878786L,64*1000*2), new UctLightBot(9991112L,64*100*4));
        //comp.setBots(new UctLightBot(7878786L,5*1000), new UctLightBot(9991112L,5*1000));
       //comp.setBots(new UctUltraFillNoConflict(7878786L,16*1000), new UctUltraFillNoConflict(9991112L,16*1000));
        //comp.setBots(new UctUltraFillNoConflict(7878786L,64*1000*2), new UctUltraFillNoConflict(9991112L,64*1000*2));
        //comp.setBots(new UctUltraFillNoConflict(7878786L,16*1000), new UctLightBot(9991112L,8*1000));
        //comp.setBots(new UctLightBot(7878786L,3*1000), new FlatBot(9991112L,6*1000));
        
//        comp.setBots(new UctGraphLightBot(7878786L,60*1000, 
//                (L64fbase.gob64Struct stat, double komi, int phase)
//                        -> new Light64AmafSrcData(stat, komi, phase, null),
//                (BoardData dat)
//                        -> ((Light64AmafSrcData)dat).mem), 
//                new UctGraphLightBot(7878786L,6*1000, 
//                (L64fbase.gob64Struct stat, double komi, int phase)
//                        -> new Light64Data(stat, komi, phase),
//                (BoardData dat)
//                        -> ((Light64Data)dat).mem)
//                );
        
        comp.setBots(new UctGraphLightBot(7878786L,60*1000, 
                (L64fbase.gob64Struct stat, double komi, int phase)
                        -> new Light64AmafSrcData(stat, komi, phase, null),
                (BoardData dat)
                        -> ((Light64AmafSrcData)dat).mem), 
                new UctLightBot(7878786L,6*1000)
                );        
        
        
        
        comp.setUp();
        
        
        comp.setGameSpooler((String gameDesc) -> {
            if(mm++ < 6)
           System.out.println(""+gameDesc);
           //System.exit(0);
        });
        
        for(int i=0;i<1000;i++)
        {
            mm=0;
            comp.addNextComparison();
            System.out.println("<comparison ...>"+System.lineSeparator()+""+comp.aggregateData());
            UctGraph.printlnReuse();
            UctGraph.resetStats();
            System.out.println(""+Light64AmafSrcData.getStats());
            Light64AmafSrcData.resetStats();
            System.gc();
        }
    }
}
