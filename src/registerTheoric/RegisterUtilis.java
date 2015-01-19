/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registerTheoric;

/**
 *
 * @author Jahan
 */
public class RegisterUtilis {

    static class AssertException extends Exception {

        public AssertException(String string) {
            super(string);
        }

    }

    static String toString(IRegister reg) {

        return toString(reg, 'X', 'O');
    }

    ;
    
     public static String toString(IRegister reg, char t, char f) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < reg.size(); i++) {
            char ap;
            int v = reg.getAt(i);

            if (v > 0) {
                ap = t;
            } else {
                ap = f;
            }

            sb.append(ap);
        }

        return sb.toString();
    }

    ;   
    
    static boolean assertEqual(IRegister reg, String s) throws AssertException {
        boolean r = s.equals(reg.toString());

        if (r) {
            return r;
        } else {
            throw new AssertException("" + reg + " NOT EQ " + s);
        }
    }

    static void inputString(IRegister reg, String s, char truec) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == truec) {
                reg.setAt(i, 1);
            } else {
                reg.setAt(i, 0);
            }
        }
    }

    final IRegFactory fact;

    public RegisterUtilis(IRegFactory fact) {
        this.fact = fact;
    }

    IRegister and(IRegister a, IRegister b) {
        IRegister v = fact.alloc();

        v.cp(a);
        v.and(b);
        return v;
    }

    IRegister or(IRegister a, IRegister b) {
        IRegister v = fact.alloc();

        v.cp(a);
        v.or(b);
        return v;
    }

    IRegister xor(IRegister a, IRegister b) {
        IRegister v = fact.alloc();

        v.cp(a);
        v.xor(b);
        return v;
    }

    IRegister not(IRegister a) {
        IRegister v = fact.alloc();

        v.cp(a);
        v.not();
        return v;
    }

    IRegister nop(IRegister a) {
        IRegister v = fact.alloc();

        v.cp(a);
        return v;
    }

    IRegister rol(IRegister a) {
        IRegister v = fact.alloc();
        IRegister f = fact.alloc();

        v.cp(a);
        f.cp(a);
        v.shl();
        f.shr(f.size() - 1);
        v.or(f);

        return v;
    }

    IRegister ror(IRegister a) {
        IRegister v = fact.alloc();
        IRegister f = fact.alloc();

        v.cp(a);
        f.cp(a);
        v.shr();
        f.shl(f.size() - 1);
        v.or(f);
        return v;
    }

    public String outString(IRegister reg, char t, char f) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < reg.size(); i++) {
            char ap;
            int v = reg.getAt(i);

            if (v > 0) {
                ap = t;
            } else {
                ap = f;
            }

            sb.append(ap);
        }

        return sb.toString();
    }

    ;       
     
      public String outString(IRegBitMap reg, char t, char f) {
        StringBuilder sb = new StringBuilder();
        sb.append("<BITMAP>\n\r");
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
            sb.append("\n\r");
           
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

    ;          
    
    public void applyRollRuleOn(IRegister inout, String rule) {
        IRegister res = fact.alloc();
        res.xor(res);

        RegisterUtilis x = new RegisterUtilis(fact);

        IRegister l = x.ror(inout);
        IRegister c = x.nop(inout);
        IRegister r = x.rol(inout);

        for (int b = 7; b >= 0; b--) {
            boolean ap = rule.charAt(7 - b) == '1';

            if (ap) {
                final IRegister lc;
                final IRegister cc;
                final IRegister rc;

                if ((b & 1) == 1) {
                    rc = x.nop(r);
                } else {
                    rc = x.not(r);
                }
                if (((b >> 1) & 1) == 1) {
                    cc = x.nop(c);
                } else {
                    cc = x.not(c);
                }
                if (((b >> 2) & 1) == 1) {
                    lc = x.nop(l);
                } else {
                    lc = x.not(l);
                }

                res.or(x.and(lc, x.and(cc, rc)));
            }
        }

        inout.cp(res);
    }

}
