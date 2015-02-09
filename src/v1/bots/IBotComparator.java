/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1.bots;

/**
 *
 * @author denis
 */
public interface IBotComparator {
    void setBots (IGoBot a,IGoBot b);
    void setUp();
    void loadState(byte [] dat);
    void setSpooler (DatSpool spooler);
    void setGameSpooler(GameSpool gameSpool);
    String displayResultAsText();
    
    /**
     * Joue la partie suivante dans l'évaluation.
     */
    void addNextComparison();
    
    public interface DatSpool{
        void spoolOut(byte [] dat);
    }
    
    public interface GameSpool{
        void spoolOut(String gameDesc);
    }
}
