/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registerTheoric.goban;

import registerTheoric.bitMaps.IRegBitMap;

/**
 *
 * @author denis
 * @param <TGoban>
 */
public interface IGobanFunct<TGoban extends IGoban> {
    
    IRegBitMap getPseudoEyes(TGoban gob);
    IRegBitMap getPotentialEyes(TGoban gob);
    IRegBitMap getUnliberted(TGoban gob,int side);
    
    

    
    
}
