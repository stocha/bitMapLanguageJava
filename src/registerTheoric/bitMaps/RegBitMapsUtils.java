/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registerTheoric.bitMaps;

/**
 *
 * @author denis
 */
public class RegBitMapsUtils {
    
      public String outString(IRegBitMap reg, char t, char f) {
        StringBuilder sb = new StringBuilder();
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
      

      
       public String intString(IRegBitMap reg, char t, char f,String in) {
        StringBuilder sb = new StringBuilder();
        
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
