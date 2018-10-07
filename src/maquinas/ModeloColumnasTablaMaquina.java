package maquinas;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaMaquina extends DefaultTableColumnModel {

	TrazadorTablaMaquina trazador;
	
	public ModeloColumnasTablaMaquina(TrazadorTablaMaquina trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID",0,25));
		this.addColumn(crearColumna("Provincia",1,100));
		this.addColumn(crearColumna("Pueblo",2,100));
		this.addColumn(crearColumna("Código postal",3,75));
		this.addColumn(crearColumna("Calle",4,100));
	}
	
	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);
		
		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}
}
