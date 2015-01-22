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
                res=setAt(res, i, j, (out & player) | ((out & player) >>> 1));
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
}
