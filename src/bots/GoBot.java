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
public interface GoBot {
    void setBoardSize();
    void clearBoard();
    void forceMove(int phase);
    int genMove();
    String name();
    String version();
    String[] infoOnPathDetailed(int[] path);
    String[] infoOnPathShort(int[] path);
}
