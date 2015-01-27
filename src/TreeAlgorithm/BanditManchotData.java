/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeAlgorithm;

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
    
    public static final Random rand=new Random(176276265L);

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
            r.add(bd);
        }
        
        return r;
    }

    @Override
    public double scoreOnce() {
        int max=1000;
        int flip=(int)(max*prob);
        int r=(rand.nextInt()&0xFFFF)%1000;
        if(r>=flip) return 1.0; else return 0.0;
    }

}
