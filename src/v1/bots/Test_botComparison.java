/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.bots;

/**
 *
 * @author denis
 */
public class Test_botComparison {
    public static void main(String args[]){
        SimpleBotComparator comp=new SimpleBotComparator();
        //comp.setBots(new FlatBot(7878786L,64*1000), new FlatBot(9991112L,32*1000));
        
        //comp.setBots(new UctLightBot(7878786L,8*1000), new FlatBot(9991112L,16*1000));
        //comp.setBots(new UctAccelLightBot(7878786L,9*1000), new UctLightBot(9991112L,5*1000));
        //comp.setBots(new UctAccelLightBot(7878786L,64*1000*2), new UctLightBot(9991112L,64*100*4));
        //comp.setBots(new UctLightBot(7878786L,5*1000), new UctLightBot(9991112L,5*1000));
       //comp.setBots(new UctUltraFillNoConflict(7878786L,16*1000), new UctUltraFillNoConflict(9991112L,16*1000));
        //comp.setBots(new UctUltraFillNoConflict(7878786L,64*1000*2), new UctUltraFillNoConflict(9991112L,64*1000*2));
        //comp.setBots(new UctUltraFillNoConflict(7878786L,16*1000), new UctLightBot(9991112L,8*1000));
        comp.setBots(new UctLightBot(7878786L,3*1000), new FlatBot(9991112L,6*1000));
        
        comp.setUp();
        comp.setGameSpooler((String gameDesc) -> {
           //System.out.println(""+gameDesc);
        });
        
        for(int i=0;i<1000;i++)
        {
            comp.addNextComparison();
            System.out.println("<comparison ...>"+System.lineSeparator()+""+comp.aggregateData());
        }
    }
}