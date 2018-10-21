/**
 * @file ModeloColumnasTablaTipoProducto.java
 * @author Edgar Azpiazu
 * @brief This file contains the table column model for product types
 */

package tipo_productos;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaTipoProductos extends DefaultTableColumnModel {

	TrazadorTablaTipoProductos trazador;
	
  /**
	 * Constructor of the class, adds columns for each needed field for product type information
	 * @param trazador Adapter for visualization style
	 */
	public ModeloColumnasTablaTipoProductos(TrazadorTablaTipoProductos trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID",0,25));
		this.addColumn(crearColumna("Descripción",1,100));
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
