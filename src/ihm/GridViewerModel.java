/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ihm;

/**
 *
 * @author jahan
 */
public interface GridViewerModel {
    
    interface Delegate{
        void refresh();
    }

    public String viewName();
    public int width();
    public int height();

    public int at(int x,int y);
    
    public void setDelegate(Delegate delegate);
}
