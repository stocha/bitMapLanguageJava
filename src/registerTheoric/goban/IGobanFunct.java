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
    
    public static class IGobandFunctOracle implements IGobanFunct<IGoban.IGobanBitMap>{

        @Override
        public IRegBitMap getPseudoEyes(IGoban.IGobanBitMap gob) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IRegBitMap getPotentialEyes(IGoban.IGobanBitMap gob) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public IRegBitMap getUnliberted(IGoban.IGobanBitMap gob, int side) {
            IRegBitMap libs=gob.allocBm();
            IRegBitMap last=gob.allocBm();
            
            
            registerTheoric.bitMaps.RegBitMapsUtils m=gob.m;
            libs.cp(m.not(m.or(gob.color[0], gob.color[1])));
            libs.cp(m.and(m.scramble(libs),gob.color[side]));
            
            last.xor(last);
            while(last.tstNot0()){
                last.cp(libs);
                libs.cp(m.and(m.scramble(libs),gob.color[side]));
                
            }
            // Renvoyer le resultat.
            //TODO renvoyer le resultat.
        }
        
    }

    
    
}
