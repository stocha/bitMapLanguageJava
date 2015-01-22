/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L64p.vrac;

import registerTheoric.bitMaps.IRegBitMap;

/**
 *
 * @author denis
 */
public class L64Bm implements IRegBitMap{
    long mem;
    final long maskl;
    final long maskr;

    public L64Bm() {
        long mask=0;
        for(int i=0;i<64;i++){
            if((i&7)==0){ mask|=1L<<i;};
        }
        maskr=~mask;
        
        mask=0;
        for(int i=0;i<64;i++){
            if((i&7)==0){ mask|=1L<<i;};
        }
        maskl=~mask;
    }
    
    

    @Override
    public int getWidth() {
        return 8;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public int getAt(int x, int y) {
        int dec=(y<<3) +x;
        return (int)((mem>>>(63-dec))&1L);
    }

    @Override
    public void setAt(int x, int y, int value) {
        int dec=(y<<3) +x;
        long bcl=1L<<(63-dec);bcl=~bcl;
        mem&=bcl;
        long v=value;
        mem|=(v<<(63-dec));
    }

    @Override
    public void shiftl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shiftr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shiftd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void shiftu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void and(Object a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void or(Object a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void xor(Object a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cp(Object a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cp(Object a, int x, int y, int widht, int height, int dx, int dy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void not() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean tstNot0() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int countBitsAtOne() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String outString( char t, char f) {
        StringBuilder sb = new StringBuilder();
        L64Bm reg=this;
        sb.append("<BITMAP>\n");
        for (int y = 0; y < reg.getHeight(); y++) {
            for (int x = 0; x < reg.getWidth(); x++) {
                char ap;
                int v = reg.getAt(x,y);

                if (v > 0) {
                    ap = t;
                } else {
                    ap = f;
                }

                sb.append(ap);
            }
            sb.append("\n");
           
        }
         return sb.toString();
    }
      

      
       public String inString( char t, char f,String in) {
        StringBuilder sb = new StringBuilder();
        L64Bm reg=this;
        int ci=0;
        for (int y = 0; y < reg.getHeight(); y++) {
            for (int x = 0; x < reg.getWidth(); x++) {
                char ap=0;
                while(ap!=t && ap!=f){
                    ap=in.charAt(ci++);
                }
                
                if(ap==t){
                    reg.setAt(x, y, 1);
                }else{
                    reg.setAt(x, y, 0);
                }
  
            }
           
        }
         return sb.toString();
    }           
}
