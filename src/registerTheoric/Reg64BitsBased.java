/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package registerTheoric;

/**
 *
 * @author Jahan
 */
public class Reg64BitsBased implements IRegister<Reg64BitsBased>{
    
    private final int nbBits;
    private final int nbBlocks;
    private final long finalMask;
    private final long data[];
    private final int dwsize=64;
    
    public Reg64BitsBased(int nbBits) {
        this.nbBits = nbBits;
        final int blocSz=dwsize;
        
        int lnbBlocks=nbBits/blocSz;
        int nbBitLast=nbBits%blocSz;
        lnbBlocks+=((nbBits%blocSz)==0?0:1);
        nbBlocks=lnbBlocks;
        if(nbBitLast==0) nbBitLast=blocSz;
        
        long mask=0;
        mask=~mask;
        //System.out.println("mask init "+mask);
        finalMask= mask<< (blocSz-nbBitLast);
        //System.out.println("finalshift  "+(blocSz-nbBitLast)+"  "+nbBitLast);

        
        data=new long[nbBlocks];
    }
    

    @Override
    public void and(Reg64BitsBased r) {
        for(int i=0;i<nbBlocks;i++){
            data[i]&=(r.data[i]);
        }
    }

    @Override
    public void or(Reg64BitsBased r) {
        for(int i=0;i<nbBlocks;i++){
            data[i]|=(r.data[i]);
        }
    }

    @Override
    public void xor(Reg64BitsBased r) {
        for(int i=0;i<nbBlocks;i++){
            data[i]^=(r.data[i]);
        }
    }

    @Override
    public void cp(Reg64BitsBased r) {
        for(int i=0;i<nbBlocks;i++){
            data[i]=(r.data[i]);
        }
    }

    @Override
    public void not() {
        for(int i=0;i<nbBlocks;i++){
            data[i]=~data[i];
        }
    }

    @Override
    public void shl() {
        long tmp;
        for(int i=1;i<nbBlocks;i++){
            int prev=i-1;
            data[prev]=data[prev]<<1;
            tmp=(data[i]);
            tmp=tmp>>(dwsize-1);
            data[prev]|=(tmp);
        }
        data[nbBlocks-1]=data[nbBlocks-1]<<1;
    }

    @Override
    public void shr() {
        long tmp;
        for(int i=nbBlocks-2;i>=0;i--){
            int prev=i+1;
            data[prev]=data[prev]>>1;
            tmp=(data[i]);
            tmp=tmp<<(dwsize-1);
            data[prev]|=(tmp);
        }
        data[0]=data[0]>>1;
        data[nbBlocks-1]&=(finalMask);
    }

    @Override
    public int getAt(int i) {
        int b=i/dwsize;
        int in=i%dwsize;
        
        return (int)((data[b]>>(dwsize-1-in))&1);
    }

    @Override
    public void setAt(int i, int v) {
        int b=i/dwsize;
        int in=i%dwsize;
        
        long at=1L<<(dwsize-1-in);
        at=~at;
        
        data[b]&=at;
        at=v;
        at=at<<(dwsize-1-in);
        data[b]|=at;
    }

    @Override
    public int size() {
        return nbBits;
    }

    @Override
    public void shl(int n) {
        for(int i=0;i<n;i++) shl();
    }

    @Override
    public void shr(int n) {
        for(int i=0;i<n;i++) shr();
    }    
    
}
