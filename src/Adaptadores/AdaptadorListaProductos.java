/**
 * @file AdaptadorListaProductos.java
 * @author Imanol Badiola
 * @brief This file contains the style of the product list
 */
package Adaptadores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import Productos.Producto;

public class AdaptadorListaProductos  implements ListCellRenderer<Producto> {
/**
 * This function is overridden to change the list adapter,
 * which changes the style of the elements of the lists of products
 * @param list List which contains the product wanted to change style
 * @param producto Specific product to change style
 * @param index Index of product in the list
 * @param isSelected Specifies if the element is selected
 * @param cellHasFocus Specifies if the cell has the focus
 * @return The panel with specified style
 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Producto> list,
					 Producto producto,
				     int index,
				     boolean isSelected,
				     boolean cellHasFocus) {
			
	 JPanel panel = new JPanel();
	 panel.setLayout(new BorderLayout());
	 panel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
	 JLabel etiqueta = new JLabel();
	 
     etiqueta.setText(producto.toString());
     etiqueta.setFont(new Font("Arial",isSelected ? Font.BOLD : Font.PLAIN,20));
     panel.setBackground(isSelected ? Color.CYAN : Color.white);
     panel.setForeground(isSelected ? Color.WHITE : Color.black);
     etiqueta.setBackground(isSelected ? Color.CYAN : Color.white);
     
     panel.add(etiqueta, BorderLayout.CENTER);
     etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
     etiqueta.setVerticalAlignment(SwingConstants.CENTER);
     etiqueta.setOpaque(true);
     
     return panel;
   }
}
