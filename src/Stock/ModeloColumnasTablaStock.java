package Stock;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import oferta.TrazadorTablaOferta;

public class ModeloColumnasTablaStock extends DefaultTableColumnModel {

	TrazadorTablaStock trazador;
	
	public ModeloColumnasTablaStock(TrazadorTablaStock trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID maquina",0,25));
		this.addColumn(crearColumna("ID producto",1,25));
		this.addColumn(crearColumna("Fecha cambio",2,100));
		this.addColumn(crearColumna("Total",3,75));
		this.addColumn(crearColumna("Posicion",4,75));
	}
	
	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);
		
		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}
}
