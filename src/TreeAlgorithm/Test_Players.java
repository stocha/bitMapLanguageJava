/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeAlgorithm;

import genericTesting.TestFramework;

/**
 *
 * @author denis
 */
public class Test_Players {
    
    public static void main(String args[]) {

        (new TestIt()).doit();

        TestFramework.showResults();
    }

    public static class TestIt {

        void TesNBenchtFlatOnBandit() {
            BanditManchotData band=new BanditManchotData(4);
            System.out.println("Theoritical values "+band.getSubData());
            
            FlatPlayer fp=new FlatPlayer(band, null);
            fp.deflat();
            
            int nbSim=10000000;
            long t0=System.nanoTime();
            for(int i=0;i<nbSim;i++){
                fp.doSimulation();
            }
            long t1=System.nanoTime();
            System.out.println("Flat data "+fp.childs);
            
            double t=t1-t0; t=t/1000000;t=t/1000;
            double simSec=nbSim/t;
            
            System.out.println(""+nbSim+" en "+t+" /  "+simSec+" act per second");
            
            System.out.println("best is "+fp.bestState());
            
        }

        public void doit() {
   
            TesNBenchtFlatOnBandit();
           
        }
    }
}
