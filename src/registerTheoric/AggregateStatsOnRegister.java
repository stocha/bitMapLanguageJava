/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package registerTheoric;

import registerTheoric.registers.IRegister;

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
        nb++;
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
            sb.append(String.format("%0"+n+"d", v));
            //sb.append(getAt(i));
            sb.append('|');
        }
        
        return sb.toString();
    }
    
    
    public String dispCount(int n){
        StringBuilder sb=new StringBuilder();
        
        for(int i=0;i<sz;i++){
            long v=(hit[i]);
            sb.append(String.format("%0"+n+"d", v));
            //sb.append(getAt(i));
            sb.append('|');
        }
        
        return sb.toString();
    }    

    
}
