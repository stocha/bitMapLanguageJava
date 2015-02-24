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

    public static class AmafAccu {

        long hitsb[];
        long hitsw[];
        double scb[];
        double scw[];
        
        long totHit;
        
        final int sz;

        public AmafAccu() {
            this.sz=64;
            hitsb = new long[sz];
            hitsw = new long[sz];
            scb = new double[sz];
            scw = new double[sz];
            
            clear();
        }

        public final void clear() {
            for (int i = 0; i < hitsb.length; i++) {
                hitsb[i] = 0;
                scb[i] = 0;
                hitsw[i] = 0;
                scw[i] = 0;
            }
            
            totHit=0;
        }
        
        public void addAmaf(BiMap amaf,double score){
            for(int i=0;i<sz;i++){
                long hitb=L64fbase.getAt(amaf.bamaf, i);
                long hitw=L64fbase.getAt(amaf.wamaf, i);
                
                hitsb[i]+=hitb;
                hitsw[i]+=hitw;
                
                if(hitb>0)
                    scb[i]+=score;
                
                if(hitw>0)
                    scw[i]+=(1.0-score);
            }
            totHit++;
        }
        
        public double getValAt(int i,int where){
            if(where==0){
                            double valb=0;
                            if(hitsb[i]>0){
                    valb=scb[i]/hitsb[i];
                }
                return valb;
            }else
            {
                            double valb=0;
                            if(hitsw[i]>0){
                    valb=scw[i]/hitsw[i];
                }
                return valb;
            }                
        }
        
        public String debug_out_(int col,boolean dispHit){
            String res="";
            
            if(col==0) res+="amaf aggreg for w";
            if(col==1) res+="amaf aggreg for b";
            
            int nbDec=(int)Math.log10(totHit);
            if(nbDec<1) nbDec=1;
            
            for(int i=0;i<sz;i++){

                if(i%8==0) res+="\n";
                long hit=0;
                if(col==0)hit=hitsb[i];
                if(col==1)hit=hitsw[i];
                
                if(dispHit) res+=String.format("#H%0"+nbDec+"d", hit);
                res+=String.format("'%0"+(1+3)+"d", (long)(getValAt(i,col)*1000));
            }
            res+="\n";
            return res;
        }
        
        public String debug_out(boolean dispHit){
            return debug_out_(0,dispHit)+""+debug_out_(1,dispHit);
        }

    }
}
