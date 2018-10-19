/**
 * @file MyDataAccess.java
 * @author Ainhoa Arruabarrena
 * @brief Class to get data from the data base
 */

package conexionSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDataAccess {

 private String _usuario;
 private String _pwd;
 private static String _bd="maquina";
 static String _url = "jdbc:mysql://localhost/"+_bd;
 private Connection conn = null;
 private final static Logger LOGGER = Logger.getLogger(MyDataAccess.class.getName());
 
 /**
 * Constructor that initalizes the connection with the database
 * @param _usuario The SQL username
 * @param _pwd The SQL user password
 */
 public MyDataAccess(String _usuario, String _pwd) {
  
	 this._usuario = _usuario;
	 this._pwd = _pwd;
	 
   try{
	 Class.forName("com.mysql.jdbc.Connection");
     conn = (Connection)DriverManager.getConnection(_url, _usuario, _pwd);
   }
   catch(SQLException ex) {
      System.out.println("Hubo un problema al intentar conecarse a la base de datos"+_url);
   }
   catch(ClassNotFoundException ex) {
      System.out.println(ex);
   }finally {
	   if(conn != null) {
		   System.out.println("Conexión a base de datos "+_url+" . . . Ok");
	   }
   }
 }
 
 /**
   * Takes the SQL query result
   * @param _query The SQL query to be executed
   * @return ResultSet of the query 
   */
 public ResultSet getQuery(String _query) {
    ResultSet resultado = null;
    try(Statement state = (Statement) conn.createStatement()){
      resultado = state.executeQuery(_query);
    }
    catch(SQLException e) {
    	LOGGER.log(Level.ALL, e.getMessage());
    }
    return resultado;
 }
 /**
   * Sets the query to execute it
   * @param _query The SQL query to be executed
   * @throws SQLException
   */
 public void setQuery(String _query) throws SQLException{
	 try(Statement state =(Statement) conn.createStatement()){
		 state.execute(_query);
	 }
 }
  /**
   * @return The SQL connexion
   */
 public Connection getConn() {
	 return conn;
 } 
}
