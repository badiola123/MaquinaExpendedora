/**
 * @file ModeloColumnasTablaOferta.java
 * @author Imanol Badiola
 * @brief This file contains the table column model for offers
 */

package oferta;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaOferta extends DefaultTableColumnModel {

	TrazadorTablaOferta trazador;
	
	/**
	 * Constructor of the class, adds columns for each needed field for offer information
	 * @param trazador Adapter for visualization style
	 */
	public ModeloColumnasTablaOferta(TrazadorTablaOferta trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID maquina",0,25));
		this.addColumn(crearColumna("ID producto",1,25));
		this.addColumn(crearColumna("Fecha de reposicion",2,100));
		this.addColumn(crearColumna("Cantidad",3,75));
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
