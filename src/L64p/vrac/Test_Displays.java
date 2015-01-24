/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L64p.vrac;

import static L64p.vrac.L64fbase.*;

/**
 *
 * @author denis
 */
public class Test_Displays {

    public static void main(String args[]) {
        //dispScramble();
        //dispRandFill();
        showRandomGame();
    }
    
    public static void showRandomGame() {
        L64fbase.gob64Struct g=new gob64Struct();
        g.init();
        for(int i=0;i<64*3;i++){
            System.out.println(g.debug_show());
            long move=g.playOneRandomMove();
            if(move==0) g.reset();
        }
        System.out.println(g.debug_show());
    }    

    public static void dispScramble() {
        long m = 0;
        m = setAt(m, 3, 7, 1);
        m = setAt(m, 2, 2, 1);
        System.out.println(outString(m, 'X', '-'));
        for (int j = 0; j < 5; j++) {
            m = scramble(m);
            System.out.println(outString(m, 'X', '-'));
        }
    }

    public static void dispRandFill() {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.init();
        for (int i = 0; i < 10; i++) {
            g.randomize();

            System.out.println(g.debug_show());
        }
    }
}
