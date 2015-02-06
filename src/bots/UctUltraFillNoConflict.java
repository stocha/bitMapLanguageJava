/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bots;

import L64p.vrac.L64fbase;
import TreeAlgorithm.BoardData;
import TreeAlgorithm.LightNoConfl8Data;
import TreeAlgorithm.UctPlayer;

/**
 *
 * @author denis
 */
public class UctUltraFillNoConflict implements IGoBot {

    UctPlayer player;
    L64fbase.gob64Struct gob;
    double komi = 0;
    final int nbSim;
    final long seed;

    public UctUltraFillNoConflict(long seed, int nbSim) {
        this.seed = seed;
        this.nbSim = nbSim;
    }

    @Override
    public void setBoardSize(int sz) {
        gob = new L64fbase.gob64Struct();
        gob.rand ^= seed;
        gob.init();
    }

    @Override
    public void clearBoard(double komi) {
        this.komi = komi;
        gob.rand ^= System.nanoTime();
        gob.reset();

    }

    @Override
    public void forceMove(int m, int phase) {
        gob.forceNormalisedMove(m, phase);
    }

    @Override
    public int genMove() {
        player = new UctPlayer(new LightNoConfl8Data(gob, komi, (int) gob.phase), null);
        player.deflat();

        for (int i = 0; i < nbSim; i++) {
            player.doSimulation();
        }
        BoardData bd = (LightNoConfl8Data) player.bestState();
        if (bd == null) {
            gob.passMove();
            return -1;
        }
        L64fbase.gob64Struct best = ((LightNoConfl8Data) bd).mem;
        long pos = best.p1 ^ gob.p0;
        int move = gob.convertToNormalisedMove(pos);
        gob.copy(best);

        return move;
    }

    @Override
    public String name() {
        return this.getClass().getName() + nbSim;
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
