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
public class T2BitReg implements IRegister<T2BitReg> {
    
    boolean[] val=new boolean[2];

    @Override
    public void and(T2BitReg r) {
        val[0]=r.val[0]&&val[0];
        val[1]=r.val[1]&&val[1];
    }

    @Override
    public void or(T2BitReg r) {
        val[0]=r.val[0]||val[0];
        val[1]=r.val[1]||val[1];
    }

    @Override
    public void xor(T2BitReg r) {
        val[0]=r.val[0]^val[0];
        val[1]=r.val[1]^val[1];
    }

    @Override
    public void cp(T2BitReg r) {
        val[0]=r.val[0];
        val[1]=r.val[1];
    }

    @Override
    public void not() {
        val[0]=!val[0];
        val[1]=!val[1];
    }

    @Override
    public void shl() {
        val[0]=val[1];
        val[1]=false;
    }

    @Override
    public void shr() {
        val[1]=val[0];
        val[0]=false;
    }

    @Override
    public int getAt(int i) {
        if(val[i]) return 1; else return 0;
    }

    @Override
    public void setAt(int i, int v) {
        val[i]=(v!=0);
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public void shl(int n) {
        for(int i=0;i<n;i++){
            shl();
        }
    }

    @Override
    public void shr(int n) {
        for(int i=0;i<n;i++){
            shr();
        }
    }

    @Override
    public void cp(T2BitReg r, int start, int nb) {
        for(int i=0;i<nb;i++){
            int ind=start+i;
            setAt(ind, r.getAt(ind));
        }
    }
    
}
