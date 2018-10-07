package oferta;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaOferta extends DefaultTableColumnModel {

	TrazadorTablaOferta trazador;
	
	public ModeloColumnasTablaOferta(TrazadorTablaOferta trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID maquina",0,25));
		this.addColumn(crearColumna("ID producto",1,25));
		this.addColumn(crearColumna("Fecha de reposicion",2,100));
		this.addColumn(crearColumna("Cantidad",3,75));
	}
	
	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);
		
		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}
}
