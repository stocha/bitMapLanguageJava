/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package registerTheoric.registers;

/**
 *
 * @author Jahan
 * @param <Conc>
 */
public interface IRegister< Conc extends IRegister> {
    void and(Conc r);
    void or(Conc r);
    void xor(Conc r);
    void cp(Conc r);
    void cp(Conc r, int start,int nb);
    void not();
    
    void shl();
    void shr();
    
    void shl(int n);
    void shr(int n);
    
    boolean tstNot0();
    int countBitsAtOne();
    
    int getAt(int i);
    void setAt(int i,int v);
    int size();
}
