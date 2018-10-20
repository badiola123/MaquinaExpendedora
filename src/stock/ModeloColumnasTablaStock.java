
/**
 * @file ModeloColumnasTablaStock.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains the table column model for the stock
 */
package stock;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;


public class ModeloColumnasTablaStock extends DefaultTableColumnModel {

	TrazadorTablaStock trazador;
	/**
	 * Constructor of the class, adds columns for each needed field for stock information
	 * @param trazador Adapter for visualization style
	 */

	public ModeloColumnasTablaStock(TrazadorTablaStock trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID maquina",0,25));
		this.addColumn(crearColumna("ID producto",1,25));
		this.addColumn(crearColumna("Fecha cambio",2,100));
		this.addColumn(crearColumna("Total",3,75));
		this.addColumn(crearColumna("Posicion",4,75));
	}
	/**
	 * Creates columns with given text, index, size and style
	 * @param text Text for the column
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
