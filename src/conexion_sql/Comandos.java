/**
 * @file Comandos.java
 * @author Ainhoa Arruabarrena
 * @brief This file contains the commands to execute in the data base
 */

package conexion_sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comandos {
	
	MyDataAccess conexion;
	private static final String DB_WHERE = " where ";
	
	public Comandos(MyDataAccess conexion){
		this.conexion = conexion;
	}
	/**
	 * Deletes data from table
	 * @param nombreTabla The database table in which the change has to be made
	 * @param primaryKey Option to get clients
	 */
	public void borrar(String nombreTabla, String primaryKey) throws SQLException {
		String comandoSQL;			
		comandoSQL = "delete from " + nombreTabla + DB_WHERE + primaryKey;
		conexion.setQuery(comandoSQL);
	}
	/**
	 * Adds data to a table
	 * @param nombreTabla The database table in which the change has to be made
	 * @param formatoColumnas the format in which the SQL query has to be written
	 * @param datos the data that has to be introduced in the database
	 */
	public void insertar(boolean[] formatoColumnas, String[] datos, String nombreTabla) throws SQLException {		
		StringBuilder buildCommand = new StringBuilder();
		buildCommand.append("insert into " + nombreTabla + " values (");
		
		for(int i = 0; i < datos.length-1; i++){
			buildCommand.append((formatoColumnas[i] ? "'" + datos[i] + "'" : datos[i]) + ", ");
		}
		buildCommand.append((formatoColumnas[datos.length-1] ? "'" + datos[datos.length-1] + "'" : datos[datos.length-1]) + ")");
		String comandoSQL = buildCommand.toString();
		conexion.setQuery(comandoSQL);
	}
	/**
	 * Adds data to a table
	 * @param nombreColumnas The database table column names
	 * @param formatoColumnas the format in which the SQL query has to be written
	 * @param datos the data that has to be introduced in the database
	 * @param nombreTabla The database table in which the change has to be made
	 * @param primaryKey Option to get clients
	 */
	public void update(boolean[] formatoColumnas, String[] datos, String[] nombreColumnas, String nombreTabla, String primaryKey) throws SQLException {		
		StringBuilder buildCommand = new StringBuilder();
		buildCommand.append("update " + nombreTabla + " set ");
		
		for(int i = 0; i < datos.length-1; i++){
			buildCommand.append(nombreColumnas[i] + " = " + (formatoColumnas[i] ? "'" + datos[i] + "'" : datos[i]) + ", ");
		}
		buildCommand.append(nombreColumnas[datos.length-1] + " = " + (formatoColumnas[datos.length-1] ? "'" + datos[datos.length-1] + "'" : datos[datos.length-1]) + DB_WHERE + primaryKey);
		String comandoSQL = buildCommand.toString();
		conexion.setQuery(comandoSQL);
	}
	/**
	 * Adds data to a table
	 * @param nombreColumnas The database table column names
	 * @param agrupacion 
	 * @param orden 
	 * @param sentidoOrden 
	 * @param limitar 
	 * @param nombreTabla The database table in which the change has to be made
	 * @param primaryKey Option to get clients
	 */
	public ResultSet select(String[] nombreColumnas, String nombreTabla, String primaryKey, String agrupacion, String orden, boolean sentidoOrden, int limitar) {	//Sentido orden true = asc, false = desc	
		
		StringBuilder buildCommand = new StringBuilder();
		buildCommand.append("select ");
		
		if(nombreColumnas != null){
			for(int i = 0; i < nombreColumnas.length-1; i++){
				buildCommand.append(nombreColumnas[i] + ", ");
			}
			buildCommand.append(nombreColumnas[nombreColumnas.length-1]);
		}
		else{
			buildCommand.append("* ");
		}
		buildCommand.append(" from " + nombreTabla);
		
		if(primaryKey != null){
			buildCommand.append(DB_WHERE + primaryKey);
		}
		
		if(agrupacion != null){
			buildCommand.append(" group by " + agrupacion);
		}
		
		if(orden != null){
			buildCommand.append(" order by " + orden + (sentidoOrden ? "" : " desc"));
		}
		
		if(limitar != 0){
			buildCommand.append(" limit " + limitar);
		}
		
		String comandoSQL = buildCommand.toString();
		return conexion.getQuery(comandoSQL);
	}
}
