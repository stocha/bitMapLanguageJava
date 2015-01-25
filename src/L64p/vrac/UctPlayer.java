/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package L64p.vrac;

import java.util.ArrayList;

/**
 *
 * @author denis
 */
public class UctPlayer {
    public static class Node{
    final Node parent;
    final ArrayList<Node> child=new ArrayList();
    double score=0;
    long visit=0;
    
        final L64fbase.gob64Struct data;

        public Node(Node parent, L64fbase.gob64Struct data) {
            this.parent = parent;
            this.data=data;
        }
        
        public boolean visit(){
            final int deflatNode=10;
            
            if(visit<deflatNode){
                
            }
            if(visit==deflatNode){
                
            }
            
            
            return selectNode();
        }
        
        public boolean selectNode(){
            return true;
        }
    
    
}
}
