/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.L64p.vrac;

import static v1.L64p.vrac.L64fbase.outString;

/**
 *
 * @author denis
 */

    public class AmafResult {

        public long bamaf;
        public long wamaf;
        public double score;

        public void cp(AmafResult src) {
            this.bamaf = src.bamaf;
            this.wamaf = src.wamaf;
        }

        public void addMove(long move, long phase) {
            if ((move & bamaf) != 0 || (move & wamaf) != 0) {
                return;
            }
            if (phase == 0) {
                bamaf |= move;
            } else {
                wamaf |= move;
            }
        }

        public String out() {
            return "sc=" + score + " " + outString(bamaf, wamaf);
        }

        public boolean appartient(AmafResult am) {
            long a = bamaf & am.bamaf;
            long b = wamaf & am.wamaf;

            a ^= bamaf;
            b ^= wamaf;
            a |= b;

            return a == 0;
        }
    }
