/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.bots;

import v1.L64p.vrac.L64fbase;
import v1.TreeAlgorithm.BoardData;
import v1.TreeAlgorithm.Light64Data;
import v1.TreeAlgorithm.LightAccelAssymData;
import v1.TreeAlgorithm.UctTree;

/**
 *
 * @author denis
 */
public class UctAccelLightBot implements IGoBot {
    
    UctTree player;
    L64fbase.gob64Struct gob;
    double komi=0;
    final int nbSim;
    final long seed;

    public UctAccelLightBot(long seed,int nbSim) {
        this.seed = seed;
                this.nbSim=nbSim;
    }
    
    

    @Override
    public void setBoardSize(int sz) {
        gob=new L64fbase.gob64Struct();
        gob.rand^=seed;
        gob.init();
    }

    @Override
    public void clearBoard(double komi) {
        this.komi=komi;
        gob.rand^=System.nanoTime();
        gob.reset();
        
    }

    @Override
    public void forceMove(int m, int phase) {
        gob.forceNormalisedMove(m, phase);
    }

    @Override
    public int genMove() {
        player=new UctTree(new LightAccelAssymData(gob,komi,(int)gob.phase), null);
        player.deflat();
        
        for(int i=0;i<nbSim;i++){
            player.doSimulation();
        }
        BoardData bd=(LightAccelAssymData)player.bestState();
        if(bd==null){gob.passMove(); return -1;}
        L64fbase.gob64Struct best=((LightAccelAssymData)bd).mem;
        long pos=best.p1^gob.p0;
        int move= gob.convertToNormalisedMove(pos);
        gob.copy(best);
        
        return move;
    }

    @Override
    public String name() {
        return this.getClass().getName()+nbSim;
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
