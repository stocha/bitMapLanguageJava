/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.demostrators;

import v1.L64p.vrac.L64fbase;
import static v1.L64p.vrac.L64fbase.*;


/**
 *
 * @author denis
 */
public class DemonstrationWithTarget {
    
    
    
    
    
    public static class TargetDescr{
        long b;
        long w;
        long t;
        
        public void invert(){
            long tm=b;
            b=w;
            w=tm;
        }
        
        public long scrblZone(final int nb){
            long res=t;
            
            for(int i=0;i<nb;i++)
                res=scramble(res);
            
            return res;
        }
        
        public void inFromStr(String inTarg){
            String in=inTarg.replace("W", "O").replace("B", "X").replace("E", "-");
            //System.out.println("Input \n"+in);
            b=L64fbase.inputString(in, 0);
            w=L64fbase.inputString(in, 1);
            String targS=inTarg.replace("W", "!").replace("B", "!").replace("E", "!")
                    .replace("X", "-").replace("O", "-");
            //System.out.println("targS \n"+targS);
            t=L64fbase.inString('!', '-', targS);
            //System.out.println(L64fbase.outString(t, 0));
        }
        
        public String out(){
        StringBuilder sb = new StringBuilder();
        sb.append("<TARGET>" + "\n");
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {

                int v = getAt(b, i, j) |( (getAt(w, i, j) << 1)|( getAt(t, i, j) << 2));
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
                    case 4:
                        out = 'E';
                        break;
                    case 5:
                        out = 'B';
                        break;
                    case 6:
                        out = 'W';
                        break;
                    case 7:
                        out = '+';
                        break;                        
                }
                sb.append(out);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
        }
        
        public static TargetDescr alloc(){
            return new TargetDescr();
        }
    }
}
