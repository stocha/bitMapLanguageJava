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
public class ComposedRegister implements IRegister<ComposedRegister> {
    
    private final int nbBits;
    private final int nbSub;
    private final int nbBlocks;
    private final IRegister finalMask;
    private final IRegister data[];

    public ComposedRegister(int nbBits, int nbSub,IRegFactory fact) {
        this.nbBits = nbBits;
        this.nbSub = nbSub;
        
        IRegister mask=fact.alloc();
        
        int blocSz=mask.size();
        
        int lnbBlocks=nbBits/blocSz;
        int nbBitLast=nbBits%blocSz;
        lnbBlocks+=((nbBits%blocSz)==0?0:1);
        nbBlocks=lnbBlocks;
        
        mask.xor(mask);
        finalMask=mask;
        for(int i=0;i<nbBitLast;i++) {finalMask.setAt(i, 1);}
        
        data=new IRegister[nbBlocks];
        for(int i=0;i<nbBlocks;i++){
            data[i]=fact.alloc();
        }
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAt(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAt(int i, int v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        return nbBits;
    }

    


    
    

}
