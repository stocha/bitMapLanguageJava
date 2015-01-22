/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L64p.vrac;

/**
 *
 * @author denis
 */
public class Bench_mesures {
    public static void main(String args[]) {
        //dispScramble();
        //dispRandFill();
        showRandomGame();
    }
    
    public static void showRandomGame() {
        L64fbase.gob64Struct g=new L64fbase.gob64Struct();
        g.init();
        long t0=System.nanoTime();
        
        final int nbGame=1000000;
        for(int big=0;big<nbGame;big++){
        for(int i=0;i<64;i++){
            //System.out.println(g.debug_show());
            g.playRandOnFree(0);
        }
        //System.out.println(g.debug_show());
        g.reset();
        }
        long t1=System.nanoTime();
        
        double t=(t1-t0)/1000000.0;
        System.out.println(""+nbGame+" partie en "+t+" millisecondes");
    }     
}
