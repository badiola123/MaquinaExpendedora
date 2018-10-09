/**
 * @file CombeRenderer.java
 * @author Edgar Azpiazu
 * @brief This file specifies the way the combo is rendered
 */

package vistas;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ComboRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;

   /**
	 * Identifies components that can be used as "rubber stamps" to paint the cells in a JList
	 * @param list The JList we are painting
	 * @param value The product returned by list.getModel().getElementAt(index)
	 * @param index The cells index
   * @param isSelected True if the specified cell was selected
   * @param cellHasFocus True if the specified cell has the focus
	 * @return A component whose paint() method will render the specified value
	 */
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

    	setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    	
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
              setFont(new Font("Courier", Font.PLAIN, 25));
        return this;
    }
}
