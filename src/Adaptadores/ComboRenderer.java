package Adaptadores;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ComboRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;

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
