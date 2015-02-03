/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bots;

import L64p.vrac.L64fbase;

/**
 *
 * @author denis
 */
public class SimpleBotComparator implements IBotComparator {
    
    static final int MAXGAMEMOVE=84*3;
    
    IGoBot [] bot= new IGoBot[2];
    DatSpool spooler=null;
    GameSpool gdisp=null;
    double komi=8.5;
    L64fbase.gob64Struct gob=new L64fbase.gob64Struct();
    int nbComparisonDone=0;;
    
    @Override
    public void setBots(IGoBot a, IGoBot b) {
        bot[0]=a;bot[1]=b;
    }

    @Override
    public void setUp() {
        for(IGoBot b : bot){
            b.setBoardSize(8*8);
            b.clearBoard(8.5);
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
        while(nbPass<2 && resign!=-1 && numMove < MAXGAMEMOVE){
            IGoBot playb=bot[(numMove&1)^mPh];
            IGoBot othb=bot[(numMove&1)^(mPh^1)];
            int m=playb.genMove();
            othb.forceMove(m,numMove&1);
            if(m==-2)resign=(numMove&1)^mPh;
            if(m==-1) nbPass++; else nbPass=0;
            
            gob.forceNormalisedMove(m, numMove&1);

            gdisp.spoolOut(gob.debug_show());
        }
    }

    @Override
    public void setGameSpooler(GameSpool gameSpool) {
        this.gdisp=gameSpool;
        
    }
    
}
