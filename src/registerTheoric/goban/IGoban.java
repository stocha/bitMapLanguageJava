/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registerTheoric.goban;

import registerTheoric.bitMaps.IRegBitMap;
import registerTheoric.bitMaps.RegBitMapsUtils;

/**
 *
 * @author denis
 */
public interface IGoban {

    int getWidth();

    int getHeight();

    String outString();

    void inputString(String model);

    int getAt(int x, int y);

    void setAt(int x, int y, int value);

    public interface Factory {

        IGoban alloc(int w, int h);
    }

    public static class IGobanBitMap implements IGoban {

        final int width;
        final int height;

        public final IRegBitMap.Factory fact;

        public final IRegBitMap color[] = new IRegBitMap[2];

        public final registerTheoric.bitMaps.RegBitMapsUtils m;

        public IGobanBitMap(int width, int height, IRegBitMap.Factory fact) {
            this.width = width;
            this.height = height;
            this.fact = fact;

            color[0] = fact.alloc(width, height);
            color[1] = fact.alloc(width, height);

            m = new RegBitMapsUtils(fact, width, height);
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public String outString() {
            StringBuilder sb = new StringBuilder();
            sb.append("<GOBAN>" + "\n");
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {

                    int v = getAt(j, j);
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
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        @Override
        public void inputString(String model) {

            int pos = 0;
            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {

                    int out = 0;

                    boolean found = false;

                    while (!found) {
                        char in = model.charAt(pos);
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
                            default:
                                found = false;
                        }
                        pos++;
                    }//while

                    setAt(i, j, out);
                }//For width
            }//For height
        }

        @Override
        public int getAt(int x, int y) {
            int res = 0;
            res += color[1].getAt(x, y);
            res = res << 1;
            res += color[0].getAt(x, y);

            return res;
        }

        @Override
        public void setAt(int x, int y, int value) {
            color[0].setAt(x, y, value & 1);
            color[1].setAt(x, y, (value >>> 1) & 1);

        }

    }

}
