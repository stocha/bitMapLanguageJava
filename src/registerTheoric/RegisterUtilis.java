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
    
    static class AssertException extends Exception{

        public AssertException(String string) {
            super(string);
        }
        
    }
    
    static String toString(IRegister reg){
        StringBuilder sb=new StringBuilder();
        
        for(int i=0;i<reg.size();i++){
            char ap;
            int v=reg.getAt(i);
            
            if(v>0) ap='X'; else ap='O';
            
            sb.append(ap);
        }
        
        return sb.toString();
    };
    
    static boolean assertEqual(IRegister reg, String s) throws AssertException{
        boolean r= s.equals(reg.toString());
        
        if(r) {return r;}
        else {
            throw new AssertException(""+reg+" NOT EQ "+s);
        }
    }
    
    static void inputString(IRegister reg,String s,char truec){
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            
            if(c==truec) reg.setAt(i, 1); else reg.setAt(i, 0);
        }
    }
 
    final IRegFactory fact;
    RegisterUtilis(IRegFactory fact){
        this.fact=fact;
    }
    
   IRegister and(IRegister a,IRegister b){
        IRegister v=fact.alloc();

        v.cp(a);
        v.and(b);
        return v;
    }
    
    IRegister or(IRegister a,IRegister b){
        IRegister v=fact.alloc();

        v.cp(a);
        v.or(b);
        return v;
    }   
    
    IRegister xor(IRegister a,IRegister b){
        IRegister v=fact.alloc();

        v.cp(a); 
        v.xor(b);
        return v;
    }      
    
    IRegister not(IRegister a){
        IRegister v=fact.alloc();

        v.cp(a); 
        v.not();
        return v;
    } 
    
    
    IRegister nop(IRegister a){
        IRegister v=fact.alloc();

        v.cp(a); 
        return v;
    }     
    
}
