/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.registerTheoric.bitMaps;

/**
 *
 * @author denis
 */
public class RegBitMapsUtils {
    
    
    final IRegBitMap.Factory fact;
    
    final int default_w;
    final int default_h;
    
    public RegBitMapsUtils(IRegBitMap.Factory fact,int default_w,int default_h)
    {
        this.fact=fact;
        this.default_h=default_h;
        this.default_w=default_w;
    }    
    
    public RegBitMapsUtils(){
        fact=null;
        default_w=2;
        default_h=2;
    }
    
    public IRegBitMap alloc(IRegBitMap model){
        return fact.alloc(model.getWidth(), model.getHeight());
        
    }
    
    public IRegBitMap and(IRegBitMap a,IRegBitMap b){
        IRegBitMap res=alloc(a);
        res.cp(a);
        res.and(b);
        return res;
    };
    
    public IRegBitMap or(IRegBitMap a,IRegBitMap b){
        IRegBitMap res=alloc(a);
        res.cp(a);
        res.or(b);
        return res;
    };
    
    public IRegBitMap xor(IRegBitMap a,IRegBitMap b){
        IRegBitMap res=alloc(a);
        res.cp(a);
        res.xor(b);
        return res;
    };
    
    public IRegBitMap not(IRegBitMap a){
        IRegBitMap res=alloc(a);
        res.cp(a);
        return res;
    };
    
    public IRegBitMap nop(IRegBitMap a){
        IRegBitMap res=alloc(a);
        res.cp(a);
        return res;
    }; 
    public IRegBitMap shiftu(IRegBitMap a){
        IRegBitMap res=alloc(a);
        res.cp(a);
        res.shiftu();
        return res;
    };   
    public IRegBitMap shiftd(IRegBitMap a){
        IRegBitMap res=alloc(a);
        res.cp(a);
        res.shiftd();
        return res;
    }; 
    public IRegBitMap shiftl(IRegBitMap a){
        IRegBitMap res=alloc(a);
        res.cp(a);
        res.shiftl();
        return res;
    }; 
    public IRegBitMap shiftr(IRegBitMap a){
        IRegBitMap res=alloc(a);
        res.cp(a);
        res.shiftr();
        return res;
    };     
    
    public IRegBitMap cp(IRegBitMap a){
        IRegBitMap res=alloc(a);
        res.cp(a);
        return res;
    };  
    
    public IRegBitMap scramble(IRegBitMap a){
        IRegBitMap res=alloc(a);
        res.xor(res);
        res.or(shiftl(a));
        res.or(shiftu(a));
        res.or(shiftd(a));
        res.or(shiftr(a));
        return res;
    };      
    
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
