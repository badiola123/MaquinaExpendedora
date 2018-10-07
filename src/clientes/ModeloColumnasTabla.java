/**
 * @file ModeloColumnasTabla.java
 * @author Imanol Badiola
 * @brief This file contains the table column model for clients
 */

package clientes;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTabla extends DefaultTableColumnModel {
	
	TrazadorTabla trazador;
	
	/**
	 * Constructor of the class, adds columns for each needed field for client information
	 * @param trazador Adapter for visualization style
	 */
	public ModeloColumnasTabla(TrazadorTabla trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID",0,25));
		this.addColumn(crearColumna("Nombre",1,100));
		this.addColumn(crearColumna("Apellido",2,100));
		this.addColumn(crearColumna("DNI",3,100));
		this.addColumn(crearColumna("Fecha de nacimiento",4,100));
		this.addColumn(crearColumna("Teléfono",5,100));
		this.addColumn(crearColumna("Correo",6,200));
		this.addColumn(crearColumna("Número de cuenta",7,100));
		this.addColumn(crearColumna("Provincia",8,100));
		this.addColumn(crearColumna("Pueblo",9,100));
		this.addColumn(crearColumna("Código postal",10,75));
		this.addColumn(crearColumna("Calle",11,100));
		this.addColumn(crearColumna("Número vivienda",12,50));
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
