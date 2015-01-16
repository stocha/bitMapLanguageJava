/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package registerTheoric;

/**
 *
 * @author Jahan
 * @param <T>
 */
public interface IRegBitMap<T> {
    
    public interface Factory{
        IRegBitMap alloc();
    }
    
    public int getWidth();
    public int getHeight();
    public int getAt(int x,int y);
    public void setAt(int x,int y,int value);
    
    public void shiftl();
    public void shiftr();
    public void shiftw();
    public void shiftu();
    
    public void and(T a);
    public void or(T a);
    public void xor(T a);
    public void cp(T a);
    public void not();
    

    
    public static class RegBitMapDef implements IRegBitMap<RegBitMapDef>{
        
        final int w;
        final int h;
        final IRegFactory fact;
        final IRegister mem;
        final IRegister separator;
        final int wdec;
        
        final IRegister r1;
        
        RegBitMapDef(int w,int h, IRegFactory fact){
            this.h=h;
            this.w=w;
            this.fact=fact;
            wdec=w+1;
            
            mem=fact.alloc();
            r1=fact.alloc();
            separator=fact.alloc();
            
            separator.xor(separator);
            
            for(int i=0;i<h;i++){
                separator.setAt(i*wdec + w-1, 1);
            }
            for(int i=((h-1)*wdec +w-1);i<separator.size();i++){
                separator.setAt(i, 1);
            }
            
            separator.not();
        }
        
        @Override
        public int getWidth(){
            return w;
        }
        
        @Override
        public int getHeight(){
            return h;
        }
        
        @Override
        public int getAt(int x,int y){
            return mem.getAt(y*wdec + x);
        }
        
        @Override
        public void setAt(int x,int y,int value){            
            mem.setAt(y*wdec + x, value);
        }        
        
        @Override
        public void and(RegBitMapDef a){
            mem.and(a.mem);
        }
        @Override
        public void or(RegBitMapDef a){
            mem.or(a.mem);
        }
        @Override
        public void xor(RegBitMapDef a){
            mem.xor(a.mem);
        }   
        @Override
        public void cp(RegBitMapDef a){
            mem.cp(a.mem);
        }     
        @Override
        public void not(){
            mem.not();
        }   
        
        @Override
        public void shiftl(){
            mem.shl();
            mem.and(separator);
        }
        @Override
        public void shiftr(){
            mem.shr();
            mem.and(separator);
        }     
        @Override
        public void shiftu(){
            mem.shl(wdec);
            mem.and(separator);
        }     
        @Override
        public void shiftw(){
            mem.shr(wdec);
            mem.and(separator);
        }            
        
    }
    
}
