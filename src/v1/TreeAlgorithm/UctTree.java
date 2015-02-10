/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.TreeAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author denis
 */
public class UctTree {
    final UctTree father;
    List<UctTree> childs=null;
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

    public UctTree(BoardData state,UctTree father) {
        this.father=father;
        this.state = state;
    }
    
    private static final UctTree nullp=new UctTree(null, null);
    public BoardData bestState(){
        double max=Double.NEGATIVE_INFINITY;
        UctTree maxfp=nullp;
        
        //System.out.println("childs count : "+childs.size());
        for(UctTree fp : childs){
            double sc=fp.scoreAvg();
            if(sc>max) {max=sc;maxfp=fp;}
        }
        return maxfp.state;
    }
    
    public UctTree selectChildToVisit(){
        double max=Double.NEGATIVE_INFINITY;
        UctTree maxfp=null;
        for(UctTree fp : childs){
            double sc=visitValue(fp);
            //System.err.println(sc+" /"+max+" "+fp);
            if(sc>=max) {max=sc;maxfp=fp;}
        }
        return maxfp;
    }
    
    double visitValue(UctTree node){
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
            UctTree ch=selectChildToVisit();
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
            childs.add(new UctTree(n, this));
        }
    }
    
    
    double scoreAvg(){
        if(hits>0) return scoreacc/hits;
        return 0;
    }
}
