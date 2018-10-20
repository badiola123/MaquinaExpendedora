/**
 * @file ImagenFondo.java
 * @author Edgar Azpiazu
 * @brief Class that  anages the background image
 */

package vistas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagenFondo extends JPanel{

	private static final Logger LOGGER = Logger.getLogger(ImagenFondo.class.getName());
	private static final long serialVersionUID = 1L;
	private transient BufferedImage imagen;

  /**
	* Constructor of the class which loads the image from a file and sets a layout
  * @param n File from which the image has to be loaded
  * @param l Instance of a layout manager for the display
	*/
	public ImagenFondo(String n, LayoutManager l) {
		this.setLayout(l);
		try {
			imagen = ImageIO.read(new File(n));
		} catch (IOException e) {
			LOGGER.log(Level.ALL, e.getMessage());
		}
	}
  
  /**
  * Overridden method to paint the image
  * @param g The Graphics object to protect
  */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g.create();
		if (imagen != null) {
			g2d.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			setOpaque(false);
		} else {
			setOpaque(true);
		}
		g2d.dispose();
	}
	
  /**
  * Sets the background image and repaints it
  * @param imagen The image to set in the background
  */
	public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
		repaint();
	}
}
