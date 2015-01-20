/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package registerTheoric.registers;

/**
 *
 * @author Jahan
 */
public class ComposedRegister implements IRegister<ComposedRegister> {
    
    private final int nbBits;
    private final int nbBlocks;
    private final IRegister finalMask;
    private final IRegister data[];
    
    private final IRegister tmp;

    public ComposedRegister(int nbBits,IRegFactory fact) {
        this.nbBits = nbBits;
        
        IRegister mask=fact.alloc();
        
        int blocSz=mask.size();
        
        int lnbBlocks=nbBits/blocSz;
        int nbBitLast=nbBits%blocSz;
        lnbBlocks+=((nbBits%blocSz)==0?0:1);
        nbBlocks=lnbBlocks;
        if(nbBitLast==0) nbBitLast=blocSz;
        
        mask.xor(mask);
        finalMask=mask;
        for(int i=0;i<nbBitLast;i++) {finalMask.setAt(i, 1);}
        
        data=new IRegister[nbBlocks];
        for(int i=0;i<nbBlocks;i++){
            data[i]=fact.alloc();
        }
        
        tmp=fact.alloc();
        
    }

    @Override
    public void and(ComposedRegister r) {
        for(int i=0;i<nbBlocks;i++){
            data[i].and(r.data[i]);
        }
    }

    @Override
    public void or(ComposedRegister r) {
        for(int i=0;i<nbBlocks;i++){
            data[i].or(r.data[i]);
        }
    }

    @Override
    public void xor(ComposedRegister r) {
        for(int i=0;i<nbBlocks;i++){
            data[i].xor(r.data[i]);
        }
    }

    @Override
    public void cp(ComposedRegister r) {
        for(int i=0;i<nbBlocks;i++){
            data[i].cp(r.data[i]);
        }
    }

    @Override
    public void not() {
        for(int i=0;i<nbBlocks;i++){
            data[i].not();
        }
    }

    @Override
    public void shl() {
        for(int i=1;i<nbBlocks;i++){
            int prev=i-1;
            data[prev].shl();
            tmp.cp(data[i]);
            tmp.shr(tmp.size()-1);
            data[prev].or(tmp);
        }
        data[nbBlocks-1].shl();
    }

    @Override
    public void shr() {
        for(int i=nbBlocks-2;i>=0;i--){
            int prev=i+1;
            data[prev].shr();
            tmp.cp(data[i]);
            tmp.shl(tmp.size()-1);
            data[prev].or(tmp);
        }
        data[0].shr();
        data[nbBlocks-1].and(finalMask);
    }

    @Override
    public int getAt(int i) {
        int b=i/tmp.size();
        int in=i%tmp.size();
        
        return data[b].getAt(in);
    }

    @Override
    public void setAt(int i, int v) {
        int b=i/tmp.size();
        int in=i%tmp.size();
        
        data[b].setAt(in,v);
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

    @Override
    public void cp(ComposedRegister r, int start, int nb) {
        for(int i=0;i<nb;i++){
            int ind=start+i;
            setAt(ind, r.getAt(ind));
        }
    }

    


    
    

}
