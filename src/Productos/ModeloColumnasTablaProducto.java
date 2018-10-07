package Productos;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;


public class ModeloColumnasTablaProducto extends DefaultTableColumnModel {

	TrazadorTablaProducto trazador;
	
	public ModeloColumnasTablaProducto(TrazadorTablaProducto trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID",0,25));
		this.addColumn(crearColumna("Descripción",1,100));
		this.addColumn(crearColumna("Precio",2,100));
		this.addColumn(crearColumna("Tipo ID",3,75));
	}
	
	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);

		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}
}
