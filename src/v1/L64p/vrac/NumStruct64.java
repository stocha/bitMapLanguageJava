/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

/**
 *
 * @author denis
 */
public class NumStruct64 {

    private final long[] d = new long[6];
    
    public void set(int pos, long val){                    
        for(int i=0;i<d.length;i++){
            //int k=d.length-1-i;   
            long bit=(val>>>(i))&1L; 
       
           d[i]= L64fbase.setAt(d[i], pos, bit);
        }
        
    }

    public long get(int pos) {
        long res = 0;
        res |= ((d[5] >>> (63 - pos)) & 1L);
        res <<= 1;
        res |= ((d[4] >>> (63 - pos)) & 1L);
        res <<= 1;
        res |= ((d[3] >>> (63 - pos)) & 1L);
        res <<= 1;
        res |= ((d[2] >>> (63 - pos)) & 1L);
        res <<= 1;
        res |= ((d[1] >>> (63 - pos)) & 1L);
        res <<= 1;
        res |= ((d[0] >>> (63 - pos)) & 1L);

        return res;
    }

    public String out() {
        String res = "";
        for (int i = 0; i < 64; i++) {

            res += "" + String.format("%02d|", get(i));
            if ((i & 7) == 7) {
                res += "\n";
            }
        }

        return res;
    }

    public void identity() {
        d[0] = 0xAAAAAAAAAAAAAAAAL;
        d[1] = 0xCCCCCCCCCCCCCCCCL;
        d[2] = 0xF0F0F0F0F0F0F0F0L;
        d[3] = 0xFF00FF00FF00FF00L;
        d[4] = 0xFFFF0000FFFF0000L;
        d[5] = 0xFFFFFFFF00000000L;
        //System.out.println(""+L64fbase.outString(d[1],0));
    }
    
    public void clear() {
        d[0] = 0;
        d[1] = 0;
        d[2] = 0;
        d[3] = 0;
        d[4] = 0;
        d[5] = 0;
        //System.out.println(""+L64fbase.outString(d[1],0));
    }    

    public void findGroups(long a, long b) {
        identity();
        int nb = 0;
        while (groupStep(a, b)) {
            //System.out.println("find group\n" + this.out());
            if (true && nb++ > 64) {
                break;
            }
        }
    }

    public boolean groupStep(long a, long b) {
        long isH = 0;
        long isS=0;
        long hasH = 0;

        long lay;
        long dir;

        long cond = 0;

        for (int i = d.length-1; i>=0; i--) {
            lay = d[i];
            dir = d[i] >>> 8;
            isH = isH |( (dir & ~lay)& ~isS);
            isS = isS|(dir^lay);
        }
        for (int i = 0; i < d.length; i++) {
            dir = d[i] >>> 8;
            cond = isH & ((a & (a >>> 8))|((b & (b >>> 8))));
            d[i] &= ~(cond);
            d[i] |= dir & cond;
        }
        hasH |= cond;
        isH = 0;isS=0;
        cond = 0;

        for (int i = d.length-1; i>=0; i--) {
            lay = d[i];
            dir = d[i] << 8;
            isH = isH |( (dir & ~lay)& ~isS);
            isS = isS|(dir^lay);
        }
        for (int i = 0; i < d.length; i++) {
            dir = d[i] << 8;
            cond = isH & ((a & (a << 8))|((b & (b << 8))));
            d[i] &= ~(cond);
            d[i] |= dir & cond;
        }
        hasH |= cond;
        isH = 0;isS=0;
        cond = 0;

        for (int i = d.length-1; i>=0; i--) {
            lay = d[i];
            //System.out.println("lay"+" "+L64fbase.outString(lay,0));
            dir = ((d[i] << 1) & L64fbase.LMASK);            
            isH = isH |( (dir & ~lay)& ~isS);
            isS = isS|(dir^lay);
        }
        for (int i = 0; i < d.length; i++) {
            dir = ((d[i] << 1) & L64fbase.LMASK);
            cond = isH & ((a & (a << 1)& L64fbase.LMASK)|((b & (b << 1)& L64fbase.LMASK)));
            d[i] &= ~(cond);
            d[i] |= dir & cond;
        }
        hasH |= cond;
        isH = 0;isS=0;
        cond = 0;

        for (int i = d.length-1; i>=0; i--) {
            lay = d[i];
            dir = (d[i] >>> 1) & L64fbase.RMASK;
            isH = isH |( (dir & ~lay)& ~isS);
            isS = isS|(dir^lay);
        }
        for (int i = 0; i < d.length; i++) {
            dir = (d[i] >>> 1) & L64fbase.RMASK;
            cond = isH & ((a & (a >>> 1)& L64fbase.RMASK)|((b & (b >>> 1)& L64fbase.RMASK)));
            d[i] &= ~(cond);
            d[i] |= dir & cond;
        }
        hasH |= cond;
        isH = 0;isS=0;
        cond = 0;
        return (hasH != 0L);

    }
}
