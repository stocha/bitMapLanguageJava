/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package old.ihdebug;

import old.bitmap.BitMap;
import ihm.GridViewerModel;

/**
 *
 * @author Jahan
 */
public class ihdBitMap {
    
        final private BitMap bm;

    

    public ihdBitMap() {
       this.bm = new BitMap(64, 64);
    }
        
        
    
           public final GridViewerModel model=new GridViewerModel() {

            @Override
            public String viewName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int width() {
                return bm.x;
            }

            @Override
            public int height() {
                return bm.y;
            }

            @Override
            public int at(int x, int y) {
                return ((x)*y)&1;
            }

            @Override
            public void setDelegate(GridViewerModel.Delegate delegate) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        }
        ;
    
    
}
