/**
 * @file TrazadorTablaOferta.java
 * @author Imanol Badiola
 * @brief This file contains the adapter for offer table styling
 */

package oferta;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TrazadorTablaOferta extends DefaultTableCellRenderer {

	private static final String FONT = "Arial";
	
	/**
	 * Overridden method to get table cell renderer with changed style
	 * @param table Table to render
	 * @param valor Value to insert at cell
	 * @param isSelected Determines if the cell is selected
	 * @param hasFocus Determines if the cell has focus
	 * @param fila Row of the table
	 * @param columna Column of the table
	 * @return The table cell renderer component
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object valor,
			boolean isSelected, boolean hasFocus, int fila, int columna) {
		
		super.getTableCellRendererComponent(table, valor, isSelected, hasFocus, fila, columna);
		table.setRowHeight(50);
		switchColumna(columna);
		return this;
	}

	private void switchColumna(int columna){
		this.setHorizontalAlignment(CENTER);
		if(columna == 0 || columna == 1) this.setFont(new Font(FONT, Font.BOLD, 22));
		else this.setFont(new Font(FONT, Font.PLAIN, 22));
	}
}