/**
 * @file TrazadorTabla.java
 * @author Imanol Badiola
 * @brief This file contains the adapter for client table styling
 */

package clientes;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TrazadorTabla extends DefaultTableCellRenderer {

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
		columnaSwitch(columna);
		return this;
	}

	private void columnaSwitch(int columna) {
		switch (columna ){
		case 0: {
			evaluateCase();
			break;
		}
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12: this.setHorizontalAlignment(CENTER);break;
		default: this.setHorizontalAlignment(CENTER);break;
		}
	}

	private void evaluateCase() {
		this.setHorizontalAlignment(CENTER);
		this.setFont(new Font("Arial", Font.BOLD, 22));
	}
}
