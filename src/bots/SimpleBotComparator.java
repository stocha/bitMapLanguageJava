/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bots;

import L64p.vrac.L64fbase;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis
 */
public class SimpleBotComparator implements IBotComparator {
    
    static final int MAXGAMEMOVE=84*3;
    
    IGoBot [] bot= new IGoBot[2];
    DatSpool spooler=null;
    GameSpool gdisp=null;
    double komi=0.5;
    L64fbase.gob64Struct gob=new L64fbase.gob64Struct();
    int nbComparisonDone=0;
    static class GameData{
        int botw;
        int botb;
        double scoreres;
        int winner;
        int nbMoves;
        int resign;
    }
    List<GameData> data=new ArrayList<>();
    
    
    
    @Override
    public void setBots(IGoBot a, IGoBot b) {
        bot[0]=a;bot[1]=b;
    }

    @Override
    public void setUp() {
        for(IGoBot b : bot){
            b.setBoardSize(8*8);
            b.clearBoard(komi);
        }
        gob.reset();
        
    }

    @Override
    public void loadState(byte[] dat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSpooler(DatSpool spooler) {
        this.spooler=spooler;
    }

    @Override
    public String displayResultAsText() {
        String r="";
        
        
        return r;
    }

    @Override
    public void addNextComparison() {
        int mPh=nbComparisonDone&1;
        
        int nbPass=0;
        int numMove=0;
        int resign=-1;
        
        for(IGoBot b : bot){
            b.clearBoard(komi);
        }
        gob.reset();
        while(nbPass<2 && resign==-1 && numMove < MAXGAMEMOVE){
            int ap=(numMove&1)^mPh;
            int bp=(numMove&1)^(mPh^1);
            
            //System.out.println("ap/bp "+ap+"/"+bp);
            
            IGoBot playb=bot[ap];
            IGoBot othb=bot[bp];
            int m=playb.genMove();
            othb.forceMove(m,numMove&1);
            if(m==-2)resign=ap;
            if(m==-1) nbPass++; else nbPass=0;
            
            gob.forceNormalisedMove(m, numMove&1);

            gdisp.spoolOut("move "+numMove+"\n"+gob.debug_show());
            numMove++;
        }
        
        GameData r=new GameData();
        r.botb=mPh;
        r.botw=mPh^1;
        r.nbMoves=numMove;
        r.scoreres=gob.scoreGame(komi, 0);
        r.winner=r.scoreres>0?1:0;
        
        data.add( r);
        
                nbComparisonDone++;
    }
    
    
    public String aggregateData(){
        String res="";
        
        {
            GameData agg[]=new GameData[]{new GameData(),new GameData()};
            double nbgam=data.size();
            for(GameData d : data){
                GameData a=agg[d.botb];

                a.botb+=1;
                a.botw+=d.botw;
                a.nbMoves+=d.nbMoves;
                a.resign+=d.resign;
                a.scoreres+=d.scoreres;
                a.winner+=d.winner;
            }

            int botnum=0;
            String lf=System.lineSeparator() ;
            for(GameData a : agg){
                nbgam=a.botb;
                if(nbgam==0) continue;
                res+=("Black "+this.bot[botnum].name()+"["+botnum+"]")+lf;
                res+=("White "+this.bot[botnum^1].name()+"["+(botnum^1)+"]")+lf;
                res+="nbGame"+" "+nbgam+" count botw "+a.botw+lf;
                res+="nbWins"+" "+a.winner+lf;
                res+="av win "+(a.winner /nbgam)+lf;
                res+="av score "+(a.scoreres /nbgam)+lf;
                res+="av resign "+(a.resign /nbgam)+lf;
                res+="av length "+(a.nbMoves /nbgam)+lf;
                botnum++;
            }
        }
        
        
        return res;
    }

    @Override
    public void setGameSpooler(GameSpool gameSpool) {
        this.gdisp=gameSpool;
        
    }
    
}
