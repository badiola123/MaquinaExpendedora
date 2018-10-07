package tipoProductos;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TrazadorTablaTipoProductos extends DefaultTableCellRenderer {

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
		case 1: this.setHorizontalAlignment(CENTER);break;
		}
		return this;
	}
}
