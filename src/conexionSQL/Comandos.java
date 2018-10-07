package conexionSQL;

import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Comandos {
	
	MyDataAccess conexion;
	
	public Comandos(MyDataAccess conexion){
		this.conexion = conexion;
	}
	
	public void borrar(String nombreTabla, String primaryKey) throws SQLException {
		String comandoSQL;			
		comandoSQL = "delete from " + nombreTabla + " where " + primaryKey;
		conexion.setQuery(comandoSQL);
	}
	
	public void insertar(boolean[] formatoColumnas, String[] datos, String nombreTabla) throws SQLException {		
		
		String comandoSQL = "insert into " + nombreTabla + " values (";
		
		for(int i = 0; i < datos.length-1; i++){
			comandoSQL += (formatoColumnas[i] ? "'" + datos[i] + "'" : datos[i]) + ", ";
		}
		comandoSQL += (formatoColumnas[datos.length-1] ? "'" + datos[datos.length-1] + "'" : datos[datos.length-1]) + ")";
		conexion.setQuery(comandoSQL);
	}
	
	public void update(boolean[] formatoColumnas, String[] datos, String[] nombreColumnas, String nombreTabla, String primaryKey) throws SQLException {		
		
		String comandoSQL = "update " + nombreTabla + " set ";
		
		for(int i = 0; i < datos.length-1; i++){
			comandoSQL += nombreColumnas[i] + " = " + (formatoColumnas[i] ? "'" + datos[i] + "'" : datos[i]) + ", ";
		}
		comandoSQL += nombreColumnas[datos.length-1] + " = " + (formatoColumnas[datos.length-1] ? "'" + datos[datos.length-1] + "'" : datos[datos.length-1]) + " where " + primaryKey;
		conexion.setQuery(comandoSQL);
	}
	
	public ResultSet select(String[] nombreColumnas, String nombreTabla, String primaryKey, String agrupacion, String orden, boolean sentidoOrden, int limitar) throws SQLException {	//Sentido orden true = asc, false = desc	
		
		String comandoSQL = "select ";
		
		if(nombreColumnas != null){
			for(int i = 0; i < nombreColumnas.length-1; i++){
				comandoSQL += nombreColumnas[i] + ", ";
			}
			comandoSQL += nombreColumnas[nombreColumnas.length-1];
		}
		else{
			comandoSQL += "* ";
		}
		comandoSQL += " from " + nombreTabla;
		
		if(primaryKey != null){
			comandoSQL += " where " + primaryKey;
		}
		
		if(agrupacion != null){
			comandoSQL += " group by " + agrupacion;
		}
		
		if(orden != null){
			comandoSQL += " order by " + orden + (sentidoOrden ? "" : " desc");
		}
		
		if(limitar != 0){
			comandoSQL += " limit " + limitar;
		}
		
		return conexion.getQuery(comandoSQL);
	}
}
