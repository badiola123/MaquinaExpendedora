/**
 * @file ModeloColumnasTablaMProducto.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains the table column model for the productos
 */

package productos;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;


public class ModeloColumnasTablaProducto extends DefaultTableColumnModel {

	TrazadorTablaProducto trazador;
	/**
	 * Constructor of the class, adds columns for each needed field for product information
	 * @param trazador Adapter for visualization style
	 */

	public ModeloColumnasTablaProducto(TrazadorTablaProducto trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID",0,25));
		this.addColumn(crearColumna("Descripción",1,100));
		this.addColumn(crearColumna("Precio",2,100));
		this.addColumn(crearColumna("Tipo ID",3,75));
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
