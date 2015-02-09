/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.TreeAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author denis
 */
public class BanditManchotData implements BoardData {

    final int nbBandit;
    double prob = 0;
    
    public static final Random rand=new Random(){

        long my=1;
        @Override
        public int nextInt() {
            my=v1.L64p.vrac.L64fbase.rule30(my);
            my=v1.L64p.vrac.L64fbase.rule30(my);
            return (int)my;
        }
        
    };
    public static final int max=1000;

    public BanditManchotData(int nbBandit) {
        this.nbBandit = nbBandit;
    }
    
    public String toString(){
        return ""+prob;
    }

    @Override
    public List<BoardData> getSubData() {
        List<BoardData> r = new ArrayList<>(nbBandit);

        double start = 0.1;
        double step = 0.8 / nbBandit;
        for (int i = 0; i < nbBandit; i++) {
            BanditManchotData bd = new BanditManchotData(0);
            bd.prob = start + ((double) i) * step;
            int val=(int)(bd.prob*max);
            bd.prob=val/(double)1000.0;
            r.add(bd);
        }
        
        return r;
    }

    @Override
    public double scoreOnce() {
        //System.out.println("scoring for "+prob);
        int flip=(int)(max*prob);
        int r=(rand.nextInt()&0xFFFF)%1000;
        if(r<=flip) return 1.0; else return 0.0;
    }

}
