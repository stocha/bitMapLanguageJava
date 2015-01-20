/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registerTheoric;

import testsEnVrac.AggregateStatsOnRegister;

/**
 *
 * @author Jahan
 * @param <T>
 */
public interface IRegBitMap<T> {

    public interface Randomizer<T> {

        void setSeed(long l);

        void apply(T m);
    }

    public interface Factory {

        IRegBitMap alloc(int w,int h);
    }

    public int getWidth();

    public int getHeight();

    public int getAt(int x, int y);

    public void setAt(int x, int y, int value);

    public void shiftl();

    public void shiftr();

    public void shiftw();

    public void shiftu();

    public void and(T a);

    public void or(T a);

    public void xor(T a);

    public void cp(T a);
    
    public void cp(T a,int x,int y, int widht,int height,int dx,int dy);

    public void not();

    public static class RegBitMapImpl implements IRegBitMap<RegBitMapImpl> {

        final int w;
        final int h;
        final IRegFactory fact;
        final IRegister mem;
        final IRegister separator;
        final int wdec;

        final IRegister r1;

        public RegBitMapImpl(int w, int h, IRegFactory fact) {
            this.h = h;
            this.w = w;
            this.fact = fact;
            wdec = w + 1;

            mem = fact.alloc();
            r1 = fact.alloc();
            separator = fact.alloc();

            separator.xor(separator);

            for (int i = 0; i < h; i++) {
                separator.setAt(i * wdec + w - 1, 1);
            }
            for (int i = ((h - 1) * wdec + w - 1); i < separator.size(); i++) {
                separator.setAt(i, 1);
            }

            separator.not();
        }

        @Override
        public int getWidth() {
            return w;
        }

        @Override
        public int getHeight() {
            return h;
        }

        @Override
        public int getAt(int x, int y) {
            return mem.getAt(y * wdec + x);
        }

        @Override
        public void setAt(int x, int y, int value) {
            mem.setAt(y * wdec + x, value);
        }

        @Override
        public void and(RegBitMapImpl a) {
            mem.and(a.mem);
        }

        @Override
        public void or(RegBitMapImpl a) {
            mem.or(a.mem);
        }

        @Override
        public void xor(RegBitMapImpl a) {
            mem.xor(a.mem);
        }

        @Override
        public void cp(RegBitMapImpl a) {
            mem.cp(a.mem);
        }

        @Override
        public void not() {
            mem.not();
        }

        @Override
        public void shiftl() {
            mem.shl();
            mem.and(separator);
        }

        @Override
        public void shiftr() {
            mem.shr();
            mem.and(separator);
        }

        @Override
        public void shiftu() {
            mem.shl(wdec);
            mem.and(separator);
        }

        @Override
        public void shiftw() {
            mem.shr(wdec);
            mem.and(separator);
        }

        public Randomizer<RegBitMapImpl> newRandomizer() {
            return new Randomizer<RegBitMapImpl>() {

                final IRegFactory fa = fact;

                RandomizeReg rr = null;

                @Override
                public void setSeed(long l) {
                    rr = new RandomizeReg.Impl(fact);

                    IRegister mem = fact.alloc();
                    
                    for(int i=0;i<64 && i<mem.size();i++){
                        mem.setAt(i, (int)((l>>>i)&1L));
                    }
                    
                    mem.setAt(0, 1);
                    
                    
                    rr.seed(mem);

                    for (int i = 0; i < 100; i++) {
                        IRegister r = rr.next();
                        //System.out.println(RegisterUtilis.toString(r, '#', '.'));
                    }
                    mem.cp(rr.next());
                    rr.seed(mem);
                     for (int i = 0; i < 100; i++) {
                        IRegister r = rr.next();
                        //System.out.println(RegisterUtilis.toString(r, '#', '.'));
                    }                   

                }

                @Override
                public void apply(RegBitMapImpl m) {
                    RegBitMapImpl.this.mem.cp(rr.next());
                    mem.and(RegBitMapImpl.this.separator);
                }
            };
        }

        @Override
        public void cp(RegBitMapImpl a, int x, int y, int width, int height, int dx,int dy) {
            for(int i=0;i<height;i++){
                for(int j=0;j<width;j++){
                    setAt(dx+j, dy+i, a.getAt(x+j, y+i));
                }
            }
        }

    }

}
