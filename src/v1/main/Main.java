/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package v1.main;

import v1.ihm.GridViewerModel;
import v1.ihm.JGrid;
import v1.ihm.Windows;

/**
 *
 * @author Jahan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Windows.createWindow(new JGrid(new GridViewerModel() {

            @Override
            public String viewName() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int width() {
                return 100;
            }

            @Override
            public int height() {
                return 100;
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
        ));
    }
    
}
