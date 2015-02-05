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
public class L64fbase {

    public static final long RMASK = 0x7f7f7f7f7f7f7f7fL;
    public static final long LMASK = 0xFEFEFEFEFEFEFEFEL;

    public static final long scramble(long mem) {
        long acc = mem;
        acc |= ((mem >>> 1) & RMASK);
        acc |= ((mem << 1) & LMASK);
        acc |= (mem >>> 8);
        acc |= (mem << 8);
        return acc;
    }

    public static final long deadFull(long mem, long lib, long externalLib) {
        long alive;
        alive = ((scramble(lib)|externalLib) & mem);

        long last = alive;

        while (last != 0) {
            last = alive;
            alive = scramble(alive) & mem;
            last ^= alive;
        }

        return (~alive) & mem;
    }

    public static final long count(long mem) {
        long v = mem;
        long c;
        v = v - ((v >>> 1) & 0x5555555555555555L);                           // temp
        v = (v & (0x3333333333333333L)) + ((v >>> 2) & (0x3333333333333333L));      // temp
        v = (v + (v >>> 4)) & 0xF0F0F0F0F0F0F0FL;                      // temp
        c = (v * (0x101010101010101L)) >>> ((7) * 8); // count
        return c;
    }

    public static final long selectNth(long in, int at) {
        int n;
        long res = 0;
        for (n = 0; in != 0 && n <= at; n++) {
            res = in;
            in &= in - 1;
        }
        return res ^ in;
    }

    public static final long rule30(long mem) {
        //(l xor (c or r))
        long l = (mem >>> 1 | mem << 63);
        long r = (mem >>> 63 | mem << 1);
        return (l ^ (mem | r));
    }

    public static final int getAt(long mem, int x, int y) {
        int dec = (y << 3) + x;
        return (int) ((mem >>> (63 - dec)) & 1L);
    }

    public static final long setAt(long mem, int x, int y, int value) {
        int dec = (y << 3) + x;
        long bcl = 1L << (63 - dec);
        bcl = ~bcl;
        mem &= bcl;
        long v = value;
        mem |= (v << (63 - dec));

        return mem;
    }

    public static final long inputString(String model, int player) {
        long res = 0;
        int pos = 0;
        player++;
        boolean ignore = false;
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {

                int out = 0;

                boolean found = false;

                while (!found) {
                    char in = model.charAt(pos++);
                    found = true;
                    switch (in) {
                        case '-':
                            out = 0;
                            break;
                        case 'X':
                            out = 1;
                            break;
                        case 'O':
                            out = 2;
                            break;
                        case '#':
                            out = 3;
                            break;
                        case '<':
                            ignore = true;
                            break;
                        case '>':
                            ignore = false;
                            found = false;
                            break;
                        default:
                            found = false;
                    }
                    if (ignore) {
                        found = false;
                    }
                }//while
                //System.out.print(""+model.charAt(pos-1));
                res = setAt(res, i, j, ((out & 1) & player) | ((out & player) >>> 1));
            }//For width
        }//For height
        return res;
    }

    public static final String outString(long mem0, long mem1) {
        StringBuilder sb = new StringBuilder();
        sb.append("<GOBAN>" + "\n");
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {

                int v = getAt(mem0, i, j) | (getAt(mem1, i, j) << 1);
                char out = ' ';

                switch (v) {
                    case 0:
                        out = '-';
                        break;
                    case 1:
                        out = 'X';
                        break;
                    case 2:
                        out = 'O';
                        break;
                    case 3:
                        out = '#';
                        break;
                }
                sb.append(out);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static final String outString(long mem, char t, char f) {
        StringBuilder sb = new StringBuilder();
        sb.append("<BITMAP>\n");
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                char ap;
                int v = getAt(mem, x, y);

                if (v > 0) {
                    ap = t;
                } else {
                    ap = f;
                }

                sb.append(ap);
                sb.append(' ');
            }
            sb.append("\n");

        }
        return sb.toString();
    }

    public static final long inString(char t, char f, String in) {
        StringBuilder sb = new StringBuilder();
        long mem = 0;
        int ci = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                char ap = 0;
                while (ap != t && ap != f) {
                    ap = in.charAt(ci++);
                }

                if (ap == t) {
                    mem = setAt(mem, x, y, 1);
                } else {
                    mem = setAt(mem, x, y, 0);
                }

            }

        }
        return mem;
    }

    public static final long tool_rMask() {
        long mask = 0;
        for (int i = 0; i < 64; i++) {
            if ((i & 0x7) == 7) {
                mask |= 1L << i;
            }
        }
        return ~mask;
    }

    public static final long tool_lMask() {
        long mask = 0;
        for (int i = 0; i < 64; i++) {
            if ((i & 0x7) == 0) {
                mask |= 1L << i;
            }
        }
        return ~mask;
    }

    public static final class gob64Struct {

        public long p0 = 0;
        public long p1 = 0;
        public long externLibs=0;
        public long rand = 1;
        public long phase = 0;
        public long repet = 0;

        public long past0 = 0;
        public long past1 = 0;
        public long past2 = 0;
        public long past3 = 0;

        public final void reset() {
            p0 = p1 = 0;
            phase=0;
            past0=past1=past2=past3=0;
        }

        public final void copy(gob64Struct src) {
            externLibs=src.externLibs;
            
            p0 = src.p0;
            p1 = src.p1;
            phase = src.phase;

            past0 = src.past0;
            past1 = src.past1;
            past2 = src.past2;
            past3 = src.past3;

            repet = src.repet;
            this.rand = src.rand;
        }

        public final void init() {
            reset();
            for (int i = 0; i < 30; i++) {
                rand = rule30(rand);
            }
        }

        public final double scoreGame(double komi, int metaphase) {

            double k = komi;
            if (metaphase != 0) {
                k = -k;
            }
            if ((phase ^ metaphase) == 0) {
                return scoreBoard() - k;
            } else {
                return -scoreBoard() - k;
            }

        }

        public final long scoreBoard() {
            long notP0 = ~p0;
            long eyePos0 = ~((notP0 >>> 8) | (notP0 << 8) | ((notP0 << 1) & LMASK) | ((notP0 >>> 1) & RMASK));
            notP0 = ~p1;
            long eyePos1 = ~((notP0 >>> 8) | (notP0 << 8) | ((notP0 << 1) & LMASK) | ((notP0 >>> 1) & RMASK));

            long s0 = count(eyePos0 | p0);
            long s1 = count(eyePos1 | p1);

            return s0 - s1;
        }

        public final double finishRandNoSuicide(double komi, int metaphase) {
            int pass = 0;
            for (int i = 0; i < 64 * 3; i++) {
                //System.out.println(g.debug_show());
                long move = this.playOneRandNoSuicide();
                if (move == 0) {
                    pass++;
                } else {
                    pass = 0;
                }
                if (pass == 2) {
                    return scoreGame(komi, metaphase);
                }
            }

            return komi;
        }

        public final long playOneRandNoSuicide() {
            return playOneRandNoSuicide(0L);
        }

        public final long playOneRandNoSuicide(long extforb) {

            long notP0 = ~p0;
            long eyePos = ~((notP0 >>> 8) | (notP0 << 8) | ((notP0 << 1) & LMASK) | ((notP0 >>> 1) & RMASK));
            long forbid = (~pseudoEyes() & (eyePos)) | extforb;
            long empty = ~(p0 | p1 | forbid);
            if (empty == 0) {
                return empty;
            }
            long pl = 0;

            long libs = ~(p0 | p1);
            long dead = 0;
            long past = 0;
            while (pl == 0) {
                pl = selectOneFree(empty);
                p0 ^= pl;
                libs ^= pl;
                past = p0;
                long suicide = deadFull(p0, libs , externLibs);
                dead = deadFull(p1, libs , externLibs);
                if (suicide != 0 && dead == 0) {
                    p0 ^= pl;
                    empty ^= pl;
                    libs ^= pl;
                    pl = 0;

                    if (empty == 0) {
                        break;
                    }
                }
            }

            if (past == past2 || past == past0) {
                repet++;
            } else {
                repet = 0;
            }

            past0 = past1;
            past1 = past2;
            past2 = past3;
            past3 = past;

            long sw = p0;
            p0 = p1;
            p1 = sw;
            phase ^= 1;
            //if(true)
            p0 ^= dead;
            if (repet >= 4) {
                return 0;
            }
            return pl;
        }

        public final long pseudoEyes() {
            long libs = ~(p0 | p1);

            long notP0 = ~p0;
            long eyePos = ~((notP0 >>> 8) | (notP0 << 8) | ((notP0 << 1) & LMASK) | ((notP0 >>> 1) & RMASK));

            long one = 0;
            long two = 0;
            long diag;

            diag = ((p1 << 9) & LMASK);
            two = (diag & one) | two;
            one = diag | one;

            diag = ((p1 << 7) & RMASK);
            two = (diag & one) | two;
            one = diag | one;

            diag = ((p1 >>> 7) & LMASK);
            two = (diag & one) | two;
            one = diag | one;

            diag = ((p1 >>> 9) & RMASK);
            two = (diag & one) | two;
            one = diag | one;
            long border = ~RMASK | ~LMASK | ~(-1L >>> 8) | ~(-1L << 8);

            long res = libs & eyePos & ((border & one) | two);
            return res;
        }
        
        public final int convertToNormalisedMove(long move){
            if(move==0) return -1;
            
            for(int i=0;i<64;i++){
                if(
                        ((
                            (move >>>(63-i))
                        &1  )!= 0)
                    ) return i;
            }
            throw new RuntimeException("Impossible path");
        }
        public final void forceNormalisedMove(int m,int phase){
            if(phase!=this.phase) throw new RuntimeException("Inconsistent phase "+this.phase+" mais "+phase);
            if(m==-1){ passMove(); return;}
            
            if(m<=0) m=0;
            int curr= getAt(p0|p1,m%8, m/8);
            if(curr!=0) throw new RuntimeException("playing non empty");
            p0=setAt(p0, m%8, m/8, 1);
            
                long empty = ~(p0 | p1) ;
                long dead0 = deadFull(p0, empty, externLibs);
               
            
                empty = ~(p0 | p1 ) ;
                //System.out.println("examined "+outString(p1, empty));
                long dead1 = deadFull(p1, empty, externLibs);
                p1 ^= dead1;
                
                //System.out.println("captured "+outString(dead1, 0));
                
                if(dead0!=0 && dead1==0) throw new RuntimeException("playing suicidal move");
                
                passMove();
            
        }

        public final long playOneRandomMove() {

            long forbid = 0;
            long empty = ~(p0 | p1 | forbid);
            if (empty == 0) {
                return empty;
            }
            long pl = selectOneFree(empty);

            pl |= p0;
            p0 = p1;
            p1 = pl;
            phase ^= 1;
            //if(true)
            {
                empty = ~(p0 | p1);
                long dead = deadFull(p0, empty, externLibs);
                p0 ^= dead;
            }
            return pl;
        }

        public final long selectOneFree(long empty) {
            long nbBit = count(empty);
            rand = rule30(rand);
            int sel = (int) ((rand & 63) % nbBit);
            //System.out.println("  "+sel);
            long pl = selectNth(empty, sel);
            return pl;
        }

        public final void randomizeAccelNoConflict() {
            rand = rule30(rand);
            rand = rule30(rand);

            long split = rand;

            rand = rule30(rand);
            rand = rule30(rand);

            long hit = rand;
            long free = ~(p0 | p1);
            hit &= free;

            long p0pre = p0;
            long p1pre = p1;

            p0 |= hit & split;
            p1 |= hit & ~split;

            long freeAft = ~(p0 | p1) ;

            long e0 = deadFull(p0 ^ p0pre, ~p0,0);
            long e1 = deadFull(p1 ^ p1pre, ~p1,0);

            //System.out.println("other "+outString(~p0pre, ~p1pre));
            //System.out.println("Asphixie "+outString(e0, e1));
            p0 = p0 & (~e0 | p0pre);
            p1 = p1 & (~e1 | p1pre);

            long d0 = deadFull(p0, freeAft, externLibs);
            long d1 = deadFull(p1, freeAft, externLibs);

            long d0voi = scramble(d0) & d1;
            long d1voi = scramble(d1) & d0;

            long noconf0 = deadFull(p0, d0voi,0);
            long noconf1 = deadFull(p1, d1voi,0);

            p0 = p0pre | noconf0;
            p1 = p1pre | noconf1;

            long freeFin = ~(p0 | p1) | externLibs;

            long clean0 = deadFull(p0, freeFin, externLibs);
            long clean1 = deadFull(p1, freeFin, externLibs);

            p0 ^= clean0;
            p1 ^= clean1;

        }

        public String debug_show() {
            if (phase == 0) {
                return outString(p0, p1);
            } else {
                return outString(p1, p0);
            }
        }

        public void debug_input(String input) {
            p0 = inputString(input, 0);
            p1 = inputString(input, 1);
            phase = 0;
        }

        public void passMove() {
            long sw = p0;
            p0 = p1;
            p1 = sw;
            phase ^= 1;
        }
    }

}
