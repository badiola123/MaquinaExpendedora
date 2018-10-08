/**
 * @file ModeloColumnasTablaMaquina.java
 * @author Imanol Badiola
 * @brief This file contains the table column model for machines
 */


package maquinas;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaMaquina extends DefaultTableColumnModel {

	TrazadorTablaMaquina trazador;
	
	/**
	 * Constructor of the class, adds columns for each needed field for machine information
	 * @param trazador Adapter for visualization style
	 */
	public ModeloColumnasTablaMaquina(TrazadorTablaMaquina trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID",0,25));
		this.addColumn(crearColumna("Provincia",1,100));
		this.addColumn(crearColumna("Pueblo",2,100));
		this.addColumn(crearColumna("C�digo postal",3,75));
		this.addColumn(crearColumna("Calle",4,100));
	}
	
	/**
	 * Creates columns with given text, index, size and style
	 * @param texto Text for the column
	 * @param indice Index for column positioning
	 * @param ancho Width of the column
	 * @return Column created to insert into table
	 */
	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);
		
		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}
}
