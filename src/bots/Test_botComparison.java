/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bots;

/**
 *
 * @author denis
 */
public class Test_botComparison {
    public static void main(String args[]){
        SimpleBotComparator comp=new SimpleBotComparator();
        comp.setBots(new FlatBot(7878786L,64), new FlatBot(9991112L,32000));
        
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
