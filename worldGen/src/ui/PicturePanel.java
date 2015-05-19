package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class PicturePanel extends JPanel {
	
	private Image image;
	 
	   PicturePanel(Image image) {
	      this.image = image;
	      
	   }
	 
	    @Override
	   protected void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      if(image != null) {
	         g.drawImage(image, 0, 0, this);
	      }
	   }
	    
	    public void setPic(Image image){
	    	this.image = image;
	    	paintComponent(getGraphics());
	    }

}
