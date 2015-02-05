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
public class UctPlayer {
    final UctPlayer father;
    List<UctPlayer> childs=null;
    long hits=0;
    double scoreacc=0;
    final BoardData state;
    
    
    public String toString(){
        String res="";
        res+=scoreAvg();
        res+="/"+hits;
        return res;
    }
    
    public String showResNBoard(){
        String res="";
            res+=""+this;
            res+=""+state+"";
        return res;
    }

    public UctPlayer(BoardData state,UctPlayer father) {
        this.father=father;
        this.state = state;
    }
    
    private static final UctPlayer nullp=new UctPlayer(null, null);
    public BoardData bestState(){
        double max=Double.NEGATIVE_INFINITY;
        UctPlayer maxfp=nullp;
        
        //System.out.println("childs count : "+childs.size());
        for(UctPlayer fp : childs){
            double sc=fp.scoreAvg();
            if(sc>max) {max=sc;maxfp=fp;}
        }
        return maxfp.state;
    }
    
    public UctPlayer selectChildToVisit(){
        double max=Double.NEGATIVE_INFINITY;
        UctPlayer maxfp=null;
        for(UctPlayer fp : childs){
            double sc=visitValue(fp);
            //System.err.println(sc+" /"+max+" "+fp);
            if(sc>=max) {max=sc;maxfp=fp;}
        }
        return maxfp;
    }
    
    double visitValue(UctPlayer node){
        if(node.hits==0) return Double.MAX_VALUE;
        double winrate=node.scoreAvg();
        if(false && hits==0){
            
            System.err.println("plante sur "+this+"   "+node);
            throw new RuntimeException("hits 0");
        }
        double lnparentvisit=Math.log((double)hits);
        double f=Math.sqrt(lnparentvisit/(5*(double)node.hits));
        //System.err.println("f "+f+" win "+winrate+" lnPar "+lnparentvisit);
        return winrate+f;
    }
    
    public double backPropCurrNodeScore(){
        return scoreAvg();
    }
    
    
    
    public double doSimulation(){
        final long expandValue=20;
        
            if(childs==null && hits>expandValue){
                deflat();
            }
        
        if(childs!=null && childs.size()>0){
            UctPlayer ch=selectChildToVisit();
            double sc=1.0- ch.doSimulation();
            this.hits++;
            this.scoreacc+=sc;
            return sc;
        }
        else
        {
            
            
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
            childs.add(new UctPlayer(n, this));
        }
    }
    
    
    double scoreAvg(){
        if(hits>0) return scoreacc/hits;
        return 0;
    }
}
