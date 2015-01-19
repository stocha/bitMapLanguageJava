/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author denis
 */
public class TestFramework {
    static HashMap<String,Integer> res=new  HashMap<String,Integer>(1000);
    static HashMap<String,Integer> tot=new  HashMap<String,Integer>(1000);
    static HashMap<String,String> modelVal=new  HashMap<String,String>(1000);
    static HashMap<String,String> actualVal=new  HashMap<String,String>(1000);
    
    
    public static void assertEquals(String model,String result){
        
        String key="NOKEY";
        try{
            throw new RuntimeException();
        }catch(Exception e){
            int l=e.getStackTrace().length;
            
            
            key="";
            //System.out.println("debug ps ===================");
            for(int i=2;i<l;i++){
                            StackTraceElement st=e.getStackTrace()[i];
                            //String vt=st.getClassName()+"."+st.getMethodName()+"."+st.getLineNumber();
                            //System.out.println("debug ps "+key);
                            key+=st.getMethodName()+"["+st.getLineNumber()+"].";
            }
            {
            StackTraceElement st=e.getStackTrace()[1];
            key+=st.getClassName()+"."+st.getMethodName()+"."+st.getLineNumber();
            }
        }
        
        if(!res.containsKey(key)){
            res.put(key,0);
            tot.put(key, 0);
        }
        
        tot.put(key, tot.get(key)+1);
        
        if(model.equals(result)){
            res.put(key, res.get(key)+1);
        }else{
            modelVal.put(key, model);
            actualVal.put(key, result);
        }
        
    }
    
    public static void showResults(){
        List<String> a=new  ArrayList<String>();
        a.addAll(tot.keySet());
        
        Collections.sort(a);
        
        for(String s :a ){
                    System.out.println(""+s+" "+res.get(s)+"/"+tot.get(s));
                    
                    if(res.get(s)!=tot.get(s)){
                        System.out.println(""+modelVal.get(s));
                        System.out.println(""+actualVal.get(s));
                    }
        }
        

    }
}
