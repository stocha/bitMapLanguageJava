/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L64p.vrac;

/**
 *
 * @author denis
 */
public class Reg381 {

    static final long finalMask;
    static final int blocSz = 64;
    static final int nbBits = 381;
    static final int nbBitLast;
    
    
    public static Reg381[] allocBuff(int nb){
        Reg381 res[]=new Reg381[nb];
        for(int i=0;i<nb;i++) res[i]=new Reg381();
        return res;
    }

    static {

        //int lnbBlocks = nbBits / blocSz;
        nbBitLast = nbBits % blocSz;

        long mask = 0;
        mask = ~mask;
        //System.out.println("mask init "+mask);
        long finalMaskr = mask << (blocSz - nbBitLast);

        finalMask = finalMaskr;
    }

    long a;
    long b;
    long c;
    long d;
    long e;
    long f;

    private long[] totab() {
        return new long[]{a, b, c, d, e, f};
    }

    public void setAt(int i, long v) {
        int bloa = i / blocSz;
        int in = i % blocSz;

        long at = 1L << (blocSz - 1 - in);
        at = ~at;

        long datb;

        switch (bloa) {
            case 0:
                datb = a;
                break;
            case 1:
                datb = b;
                break;
            case 2:
                datb = c;
                break;
            case 3:
                datb = d;
                break;
            case 4:
                datb = e;
                break;
            case 5:
                datb = f;
                break;
            default:
                throw new RuntimeException("Forbiden path");
        }

        datb &= at;
        at = v;
        at = at << (blocSz - 1 - in);
        datb |= at;

        switch (bloa) {
            case 0:
                a = datb;
                break;
            case 1:
                b = datb;
                break;
            case 2:
                c = datb;
                break;
            case 3:
                d = datb;
                break;
            case 4:
                e = datb;
                break;
            case 5:
                f = datb;
                break;
            default:
                throw new RuntimeException("Forbiden path");
        }
    }

    public long getAt(int i) {
        int bloa = i / blocSz;
        int in = i % blocSz;

        long datb;

        switch (bloa) {
            case 0:
                datb = a;
                break;
            case 1:
                datb = b;
                break;
            case 2:
                datb = c;
                break;
            case 3:
                datb = d;
                break;
            case 4:
                datb = e;
                break;
            case 5:
                datb = f;
                break;
            default:
                throw new RuntimeException("Forbiden path");
        }
        return (int) ((datb >>> (blocSz - 1 - in)) & 1);
    }

    public void cp(Reg381 s) {
        a = s.a;
        b = s.b;
        c = s.c;
        d = s.d;
        e = s.e;
        f = s.f;
    }

    public long tst0() {
        return a | b | c | d | e | f;
    }

    public long cmp(Reg381 r) {
        long acc = 0;
        acc |= r.a ^ a;
        acc |= r.b ^ b;
        acc |= r.c ^ c;
        acc |= r.d ^ d;
        acc |= r.e ^ e;
        acc |= r.f ^ f;
        return acc;
    }

    public void xor(Reg381 r) {
        a ^= r.a;
        b ^= r.b;
        c ^= r.c;
        d ^= r.d;
        e ^= r.e;
        f ^= r.f;
    }

    public void or(Reg381 r) {
        a |= r.a;
        b |= r.b;
        c |= r.c;
        d |= r.d;
        e |= r.e;
        f |= r.f;
    }

    public void and(Reg381 r) {
        a &= r.a;
        b &= r.b;
        c &= r.c;
        d &= r.d;
        e &= r.e;
        f &= r.f;
    }

    public void not() {
        a = ~a;
        b = ~b;
        c = ~c;
        d = ~d;
        e = ~e;
        f = ~f;
        f &= finalMask;
    }
    
    public void regShl(){
        a=(a<<1)|(b>>>63);
        b=(b<<1)|(c>>>63);
        c=(c<<1)|(d>>>63);
        d=(d<<1)|(e>>>63);
        e=(e<<1)|(f>>>63);
        f=(f<<1);
    }
    public void regShr(){
        f=((f>>>1)|(e<<63))&finalMask;
        e=(e>>>1)|(d<<63);
        d=(d>>>1)|(c<<63);
        c=(c>>>1)|(b<<63);
        b=(b>>>1)|(a<<63);
        a=(a>>>1);        
    }    
    
    public void regRol(){
        long oa=a;
        a=(a<<1)|(b>>>63);
        b=(b<<1)|(c>>>63);
        c=(c<<1)|(d>>>63);
        d=(d<<1)|(e>>>63);
        e=(e<<1)|(f>>>63);
        f=((f<<1)|(oa>>>(nbBitLast-1)))&finalMask;        
    }
    
    public void regRor(){
        long oa=f;
        f=((f>>>1)|(e<<63))&finalMask;
        e=(e>>>1)|(d<<63);
        d=(d>>>1)|(c<<63);
        c=(c>>>1)|(b<<63);
        b=(b>>>1)|(a<<63);
        a=(a>>>1)|(oa<<(nbBitLast-1));        
    }
    
    public void rule30(Reg381[] buff){
        Reg381 l=buff[0];
        Reg381 r=buff[1];
        
        l.cp(this);
        r.cp(this);
        
        l.regRor();
        r.regRol();
        
        this.or(r);
        this.xor(l);
    }

    String debug_regOut() {
        String res = "";
        for (int i = 0; i < nbBits; i++) {
            long v = getAt(i);
            char c = v == 0 ? '-' : 'X';
            res += c;
        }
        return res;
    }
;
}
