/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bots;

import L64p.vrac.L64fbase;
import TreeAlgorithm.FlatPlayer;
import TreeAlgorithm.Light64Data;

/**
 *
 * @author denis
 */
public class FlatBot implements IGoBot {
    
    FlatPlayer player;
    L64fbase.gob64Struct gob;
    double komi=0;
    final int nbSim=32000;

    @Override
    public void setBoardSize(int sz) {
        gob=new L64fbase.gob64Struct();
        gob.init();
    }

    @Override
    public void clearBoard(double komi) {
        this.komi=komi;
        gob.reset();
    }

    @Override
    public void forceMove(int m, int phase) {
        gob.forceNormalisedMove(m, phase);
    }

    @Override
    public int genMove() {
        player=new FlatPlayer(new Light64Data(gob,komi,(int)gob.phase), null);
        
        for(int i=0;i<nbSim;i++){
            player.doSimulation();
        }
        long pos=((Light64Data)player.bestState()).mem.p1^gob.p0;
        return gob.convertToNormalisedMove(pos);
    }

    @Override
    public String name() {
        return "flat player"+nbSim;
    }

    @Override
    public String version() {
        return "V0";
    }

    @Override
    public String[] infoOnPathDetailed(int[] path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] infoOnPathShort(int[] path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
