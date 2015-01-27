/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TreeAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis
 */
public class FlatPlayer {
    final FlatPlayer father;
    List<FlatPlayer> childs=null;
    long hits=0;
    double scoreacc=0;
    final BoardData state;

    public FlatPlayer(BoardData state,FlatPlayer father) {
        this.father=father;
        this.state = state;
    }
    
    public BoardData bestState(){
        double max=Double.MIN_VALUE;
        FlatPlayer maxfp=null;
        for(FlatPlayer fp : childs){
            double sc=scoreAvg();
            if(sc>max) {max=sc;maxfp=fp;}
        }
        return maxfp.state;
    }
    
    public FlatPlayer selectChildToVisit(){
        double max=Double.MIN_VALUE;
        FlatPlayer maxfp=null;
        for(FlatPlayer fp : childs){
            double sc=visitValue(fp);
            if(sc>max) {max=sc;maxfp=fp;}
        }
        return maxfp;
    }
    
    double visitValue(FlatPlayer node){
        if(hits==0) return Double.MAX_VALUE;
        return 1/hits;
    }
    
    public double backPropCurrNodeScore(){
        return scoreAvg();
    }
    
    
    
    public double incScore(){
        if(childs!=null){
            return 0;
        }else{
            double sc=state.scoreOnce();
            hits++;
            scoreacc+=sc;
            return sc;
        }
    };
    
    public void deflat(){
        List<BoardData> next=state.getSubData();
        childs=new ArrayList<>(next.size());
        for(BoardData n:next){
            childs.add(new FlatPlayer(n, this));
        }
    }
    
    
    double scoreAvg(){
        if(hits>0) return scoreacc/hits;
        return 0;
    }
}
