/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testsEnVrac;

import registerTheoric.IRegister;

/**
 *
 * @author Jahan
 */
public class AggregateStatsOnRegister {
    
    final int sz;
    final long hit[];
    int nb=0;

    public AggregateStatsOnRegister(int sz) {
        this.sz = sz;
        hit=new long[sz];
    }
    
    public void reset(){
        nb=0;
        for(int i=0;i<hit.length;i++){
            hit[i]=0;
        }
    }
    
    public int size(){
        return sz;
    }
    
    public void input(IRegister r){
        
        for(int i=0;i<r.size();i++){
            hit[i]+=r.getAt(i);
        }
        
    }
    
    public double getAt(int i){
        double r=hit[i];
        double div=nb;
        return r/div;
    }
    
    public String disp(int n){
        StringBuilder sb=new StringBuilder();
        
        double mul=1.0;
        
        for(int i=0;i<n;i++){
            mul*=10.0;
        }
        
        for(int i=0;i<sz;i++){
            long v=(long)(getAt(i)*mul);
            sb.append(String.format("%0"+n+"i", v));
            sb.append('|');
        }
        
        return sb.toString();
    }

    
}
