/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.bots;

import java.util.ArrayList;
import java.util.List;
import v1.L64p.vrac.L64fbase;

/**
 *
 * @author denis
 */
public class ComparatorWithInitialState implements IBotComparator {

    static final int MAXGAMEMOVE = 84 * 3;

    IGoBot[] bot = new IGoBot[2];
    DatSpool spooler = null;
    GameSpool gdisp = null;
    double komi = 6.5;
    L64fbase.gob64Struct gob = new L64fbase.gob64Struct();
    int nbComparisonDone = 0;
    
    public void setInitialState(String s){
        initial=s;
    }

    private String initial = "<GOBAN>\n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n";
    
    public static final String initialCross = "<GOBAN>\n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - X O - - - \n"
            + "- - - O X - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n";
    
    public static final String initialVide = "<GOBAN>\n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"
            + "- - - - - - - - \n"; 
   

    static class GameData {

        int botw;
        int botb;
        double scoreres;
        int winner;
        int nbMoves;
        int resign;
        double timeMilli;
    }
    List<GameData> data = new ArrayList<>();

    @Override
    public void setBots(IGoBot a, IGoBot b) {
        bot[0] = a;
        bot[1] = b;
    }

    public void debug_input(String s) {
        L64fbase.gob64Struct g = new L64fbase.gob64Struct();
        g.debug_input(s);

        int phase = 0;
        while (g.p0 != 0 || g.p1 != 0) {
            if (g.p0 != 0) {
                long sel = g.selectOneFree(g.p0);
                g.p0 ^= sel;
                for (IGoBot b : bot) {
                    b.forceMove(g.convertToNormalisedMove(sel), phase & 1);
                }
            } else {
                for (IGoBot b : bot) {
                    b.forceMove(-1, phase & 1);
                }
            }
            phase++;

            if (g.p1 != 0) {
                long sel = g.selectOneFree(g.p1);
                g.p1 ^= sel;
                for (IGoBot b : bot) {
                    b.forceMove(g.convertToNormalisedMove(sel), phase & 1);
                }
            } else {
                for (IGoBot b : bot) {
                    b.forceMove(-1, phase & 1);
                }
            }
            phase++;
        }
    }

    @Override
    public void setUp() {
        for (IGoBot b : bot) {
            b.setBoardSize(8 * 8);
        }
        reset();

    }

    private void reset() {
        for (IGoBot b : bot) {
            b.clearBoard(komi);
        }
        debug_input(initial);
        gob.debug_input(initial);
    }

    @Override
    public void loadState(byte[] dat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSpooler(DatSpool spooler) {
        this.spooler = spooler;
    }

    @Override
    public String displayResultAsText() {
        String r = "";

        return r;
    }
    
    public double doOneScore(){
       int mPh = nbComparisonDone & 1;

        int nbPass = 0;
        int numMove = 0;
        int resign = -1;

        //long t0 = System.nanoTime();

        reset();
        while (nbPass < 2 && resign == -1 && numMove < MAXGAMEMOVE) {
            int ap = (numMove & 1) ^ mPh;
            int bp = (numMove & 1) ^ (mPh ^ 1);

            //System.out.println("ap/bp "+ap+"/"+bp);
            IGoBot playb = bot[ap];
            IGoBot othb = bot[bp];
            int m = playb.genMove();
            othb.forceMove(m, numMove & 1);
            if (m == -2) {
                resign = ap;
            }
            if (m == -1) {
                nbPass++;
            } else {
                nbPass = 0;
            }

            gob.forceNormalisedMove(m, numMove & 1);

            //gdisp.spoolOut("move " + numMove + "\n" + gob.debug_show());
            numMove++;
        }      
        
        double gobscore = gob.scoreGame(komi, 0);
        double winner = gobscore > 0 ? 1 : 0;
        
//        gdisp.spoolOut("simulated winner " + winner + "\n" + gob.debug_show());
        
        return winner;
    }

    @Override
    public void addNextComparison() {
        int mPh = nbComparisonDone & 1;

        int nbPass = 0;
        int numMove = 0;
        int resign = -1;

        long t0 = System.nanoTime();

        reset();
        while (nbPass < 2 && resign == -1 && numMove < MAXGAMEMOVE) {
            int ap = (numMove & 1) ^ mPh;
            int bp = (numMove & 1) ^ (mPh ^ 1);

            //System.out.println("ap/bp "+ap+"/"+bp);
            IGoBot playb = bot[ap];
            IGoBot othb = bot[bp];
            int m = playb.genMove();
            othb.forceMove(m, numMove & 1);
            if (m == -2) {
                resign = ap;
            }
            if (m == -1) {
                nbPass++;
            } else {
                nbPass = 0;
            }

            gob.forceNormalisedMove(m, numMove & 1);

            gdisp.spoolOut("move " + numMove + "\n" + gob.debug_show());
            numMove++;
        }

        long t1 = System.nanoTime();

        GameData r = new GameData();
        r.botb = mPh;
        r.botw = mPh ^ 1;
        r.nbMoves = numMove;
        r.scoreres = gob.scoreGame(komi, 0);
        r.winner = r.scoreres > 0 ? 1 : 0;
        r.timeMilli = (t1 - t0) / 1000000.0;

        data.add(r);

        nbComparisonDone++;
        
        System.out.println(""+r.scoreres+"   board "+gob.scoreBoard()+" phase "+gob.phase);
    }

    public String aggregateData() {
        String res = "Komi(" + komi + ")";

        {
            GameData agg[] = new GameData[]{new GameData(), new GameData()};
            double nbgam = data.size();
            for (GameData d : data) {
                GameData a = agg[d.botb];

                a.botb += 1;
                a.botw += d.botw;
                a.nbMoves += d.nbMoves;
                a.resign += d.resign;
                a.scoreres += d.scoreres;
                a.winner += d.winner;
                a.timeMilli += d.timeMilli;
            }

            int botnum = 0;
            String lf = System.lineSeparator();
            for (GameData a : agg) {
                nbgam = a.botb;
                if (nbgam == 0) {
                    continue;
                }
                res += ("Black " + this.bot[botnum].name() + "[" + botnum + "]") + lf;
                res += ("White " + this.bot[botnum ^ 1].name() + "[" + (botnum ^ 1) + "]") + lf;
                res += "nbGame" + " " + nbgam + " count botw " + a.botw + lf;
                res += "nbWins" + " " + a.winner + lf;
                res += "av win " + (a.winner / nbgam) + lf;
                res += "av score " + (a.scoreres / nbgam) + lf;
                res += "av resign " + (a.resign / nbgam) + lf;
                res += "av length " + (a.nbMoves / nbgam) + lf;
                res += "av milli " + (a.timeMilli / nbgam) + lf;
                botnum++;
            }
        }

        return res;
    }

    @Override
    public void setGameSpooler(GameSpool gameSpool) {
        this.gdisp = gameSpool;

    }

}
