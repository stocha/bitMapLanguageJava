/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeAlgorithm;

import L64p.vrac.L64fbase;
import L64p.vrac.L64fbase.gob64Struct;
import java.util.List;

/**
 *
 * @author denis
 */
public class Light64Data implements BoardData {

    final L64fbase.gob64Struct mem = new gob64Struct();
    final double komi;

    Light64Data(gob64Struct src, double komi) {
        mem.copy(src);
        this.komi = komi;
    }

    @Override
    public List<BoardData> getSubData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double scoreOnce() {
        gob64Struct sim = new gob64Struct();
        sim.copy(mem);
        double sc = sim.finishRandNoSuicide(komi);
        return sc;
    }

}
