package tipoProductos;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaTipoProductos extends DefaultTableColumnModel {

	TrazadorTablaTipoProductos trazador;
	
	public ModeloColumnasTablaTipoProductos(TrazadorTablaTipoProductos trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID",0,25));
		this.addColumn(crearColumna("Descripción",1,100));
	}
	
	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);

		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}
}
