/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.bots;

import v1.L64p.vrac.L64fbase;
import v1.TreeAlgorithm.BoardData;
import v1.TreeAlgorithm.Light64AmafSrcData;
import v1.TreeAlgorithm.Light64Data;
import v1.TreeAlgorithm.UctGraph;

/**
 *
 * @author denis
 */
public class UctGraphLightBot  implements IGoBot {
    final UctGraph instGraph;
    UctGraph.UctNode player;
    L64fbase.gob64Struct gob;
    double komi=0;
    final int nbSim;
    final long seed;
    
    final DataFactory dataFact;
    final DataConverter dataConv;
    
    final String subInstName;

    public UctGraphLightBot(long seed,int nbSim,DataFactory dataFact,DataConverter dataConv) {
        this.seed = seed;
        this.nbSim=nbSim;
        this.dataFact=dataFact;
        this.dataConv=dataConv;
        instGraph=new UctGraph(nbSim);
        
        subInstName=dataFact.gen(new L64fbase.gob64Struct(), 0, 0).getClass().getName();
    }
    
    public interface DataFactory{
        public BoardData gen(L64fbase.gob64Struct stat,double komi,int phase);        
    }
    
    public interface DataConverter{
        public L64fbase.gob64Struct toGob64(BoardData dat);
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
        instGraph.clear();
        //player=instGraph.new UctNode(new Light64Data(gob,komi,(int)gob.phase), 0);
        player=instGraph.new UctNode(dataFact.gen(gob,komi,(int)gob.phase), 0);
        player.deflat();
        
        for(int i=0;i<nbSim;i++){
            player.doSimulation();
        }
        //System.out.println(""+player.debugRec(0));
        //System.out.println(""+player.debugFlat());
       // System.out.println(""+Light64AmafSrcData.getStats());
        BoardData bd=player.bestState();
        if(bd==null){gob.passMove(); return -1;}
        L64fbase.gob64Struct best=dataConv.toGob64(bd);
        long pos=best.p1^gob.p0;
        int move= gob.convertToNormalisedMove(pos);
        gob.copy(best);
        
        return move;
    }

    @Override
    public String name() {
        return "UctLightNoGraph"+nbSim+":"+subInstName;
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