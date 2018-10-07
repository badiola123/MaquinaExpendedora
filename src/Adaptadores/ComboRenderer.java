/**
 * @file ComboRenderer.java
 * @author Imanol Badiola
 * @brief This file contains the style of the Combo boxes
 */

package Adaptadores;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ComboRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;
    /**
     * This function is overridden to put the Combo boxes centered
     * @param list List which contains the elements to center
     * @param value Value of checkbox
     * @param index Index of element in the list
     * @param isSelected Specifies if the element is selected
     * @param cellHasFocus Specifies if the cell has the focus
     * @return The list cell renderer with applied changes
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
