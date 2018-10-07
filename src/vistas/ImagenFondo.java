package vistas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagenFondo extends JPanel{

	private static final long serialVersionUID = 1L;
	private BufferedImage imagen;

	public ImagenFondo(String n, LayoutManager l) {
		this.setLayout(l);
		try {
			imagen = ImageIO.read(new File(n));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (imagen != null) {
			this.imagen = imagen;
		}
	}

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
	
	public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
		repaint();
	}
}
