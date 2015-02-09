/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package v1.ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

/**
 *
 * @author Jahan
 */
public class JGrid extends JComponent {
    GridViewerModel theModel;

    MouseListener mouseAct=new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("MouseClicked "+e.getButton()+" "+e.getX()+"/"+e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    public JGrid(GridViewerModel theModel) {
        setPreferredSize(new Dimension(800, 800));
        setOpaque(true);
        
        this.theModel=theModel;
        
        this.addMouseListener(mouseAct);
    }
    
    
    
    @Override
    protected void paintComponent(Graphics gb) {
        
        
        int cellx=getWidth()/theModel.width();
        int celly=getHeight()/theModel.height();
        
        if (isOpaque()) { //paint background
            gb.setColor(Color.BLACK);
            gb.fillRect(0, 0, getWidth(), getHeight());
        }
        
        
            Color[] cols=new Color[]{Color.lightGray,Color.magenta};

            Graphics2D g = (Graphics2D)gb.create();
            
            for(int i=0;i<theModel.width();i++)
            for(int j=0;j<theModel.height();j++)
            {
               int val=theModel.at(i, j);
               // int val=j&1 & i&1;
               g.setColor(cols[val%cols.length]);
               
               if(cellx>5 && celly>5)
                g.fillRect(i*cellx +1 , j*celly +1,cellx -2, celly -2);
               else{
                   g.fillRect(i*cellx , j*celly,cellx, celly);
               }
            }
            
 
            

        g.dispose(); //clean up
    }
}
