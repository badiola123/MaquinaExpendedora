/**
 * @file AdaptadorListaProductos.java
 * @author Edgar Azpiazu
 * @brief This file specifies the way the product list is rendered
 */

package vistas;

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
	 * Identifies components that can be used as "rubber stamps" to paint the cells in a JList
	 * @param list The JList we are painting
	 * @param producto The product returned by list.getModel().getElementAt(index)
	 * @param index The cells index
   * @param isSelected True if the specified cell was selected
   * @param cellHasFocus True if the specified cell has the focus
	 * @return A component whose paint() method will render the specified value
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
