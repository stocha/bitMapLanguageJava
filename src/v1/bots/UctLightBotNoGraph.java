/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.bots;

import v1.L64p.vrac.L64fbase;
import v1.TreeAlgorithm.BoardData;
import v1.TreeAlgorithm.Light64Data;
import v1.TreeAlgorithm.UctGraphNoGraph;

/**
 *
 * @author denis
 */
public class UctLightBotNoGraph  implements IGoBot {
    final UctGraphNoGraph instGraph;
    UctGraphNoGraph.UctNode player;
    L64fbase.gob64Struct gob;
    double komi=0;
    final int nbSim;
    final long seed;

    public UctLightBotNoGraph(long seed,int nbSim) {
        this.seed = seed;
        this.nbSim=nbSim;
        instGraph=new UctGraphNoGraph(nbSim);
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
        player=instGraph.new UctNode(new Light64Data(gob,komi,(int)gob.phase), 0);
        player.deflat();
        
        for(int i=0;i<nbSim;i++){
            player.doSimulation();
        }
        BoardData bd=(Light64Data)player.bestState();
        if(bd==null){gob.passMove(); return -1;}
        L64fbase.gob64Struct best=((Light64Data)bd).mem;
        long pos=best.p1^gob.p0;
        int move= gob.convertToNormalisedMove(pos);
        gob.copy(best);
        
        return move;
    }

    @Override
    public String name() {
        return "UctLightNoGraph"+nbSim;
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