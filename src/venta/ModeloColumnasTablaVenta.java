package venta;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import tipoProductos.TrazadorTablaTipoProductos;

public class ModeloColumnasTablaVenta extends DefaultTableColumnModel {

	TrazadorTablaVenta trazador;
	
	public ModeloColumnasTablaVenta(TrazadorTablaVenta trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID usuario",0,25));
		this.addColumn(crearColumna("ID maquina",1,25));
		this.addColumn(crearColumna("ID producto",2,25));
		this.addColumn(crearColumna("Fecha",3,100));
		this.addColumn(crearColumna("Precio",4,25));
	}
	
	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);

		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}
}