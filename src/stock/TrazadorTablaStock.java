/**
 * @file TrazadorTablaStock.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains the adapter for stock table styling
 */
package stock;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TrazadorTablaStock extends DefaultTableCellRenderer {
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
		this.setFont(new Font("Arial", Font.PLAIN, 22));		
		switch (columna ){
		case 0: {
			this.setHorizontalAlignment(CENTER);
			this.setFont(new Font("Arial", Font.BOLD, 22));
			break;
		}
		case 1: {
			this.setHorizontalAlignment(CENTER);
			this.setFont(new Font("Arial", Font.BOLD, 22));
			break;
		}
		case 2: this.setHorizontalAlignment(CENTER);break;
		case 3: this.setHorizontalAlignment(CENTER);break;
		case 4: this.setHorizontalAlignment(CENTER);break;
		default: this.setHorizontalAlignment(CENTER);break;
		}
		return this;
	}
}
