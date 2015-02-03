/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bots;

/**
 *
 * @author denis
 */
public interface IGoBot {
    void setBoardSize(int sz);
    void clearBoard(double komi);
    void forceMove(int m,int phase);
    
    /**
     * -1 pass; -2 resign;
     * @return 
     */
    int genMove();
    String name();
    String version();
    String[] infoOnPathDetailed(int[] path);
    String[] infoOnPathShort(int[] path);
}
