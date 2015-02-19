/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

import v1.L64p.vrac.L64fbase.gob64Struct;
import static v1.L64p.vrac.L64fbase.outString;

/**
 *
 * @author denis
 */
public class BiMap {
    
    public long bamaf;
    public long wamaf;
    //public double score;

    public void cp(BiMap src) {
        this.bamaf = src.bamaf;
        this.wamaf = src.wamaf;
    }
    
    public void addMove(long move, long phase) {
        if ((move & bamaf) != 0 || (move & wamaf) != 0) {
            return;
        }
        if (phase == 0) {
            bamaf |= move;
        } else {
            wamaf |= move;
        }
    }
    
    public String out() {
        return outString(bamaf, wamaf);
    }
    
    public boolean appartient(BiMap am) {
        long a = bamaf & am.bamaf;
        long b = wamaf & am.wamaf;
        
        a ^= bamaf;
        b ^= wamaf;
        a |= b;
        
        return a == 0;
    }
    
    public static int lightIntoBiMap(gob64Struct src, BiMap amaf, BiMap biControl) {
        L64fbase.gob64Struct sim = new L64fbase.gob64Struct();
        
        sim.copy(src);
        sim.rand = src.rand;
        
        int pass = 0;
        int play = 0;
        for (int i = 0; i < 64 * 3; i++) {
            //System.out.println(g.debug_show());
            long move = sim.playOneRandNoSuicide();
            amaf.addMove(move, sim.phase ^ 1);
            if (move == 0) {
                sim.passMove();
                pass++;
            } else {
                pass = 0;
                play++;
            }
            if (pass == 2) {
                sim.controlMapImmediate(biControl);
            }
        }
        src.rand = sim.rand;
        return play;        
    }
}
