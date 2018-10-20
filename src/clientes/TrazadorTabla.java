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
		if(columna == 0) {
			evaluateCase();
		}
		else {
			this.setHorizontalAlignment(CENTER);
		}
	}

	private void evaluateCase() {
		this.setHorizontalAlignment(CENTER);
		this.setFont(new Font("Arial", Font.BOLD, 22));
	}
}
