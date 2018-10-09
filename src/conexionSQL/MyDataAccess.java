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

public class MyDataAccess {

 private String _usuario;
 private String _pwd;
 private static String _bd="maquina";
 static String _url = "jdbc:mysql://localhost/"+_bd;
 private Connection conn = null;
 
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
     if(conn != null) {
       System.out.println("Conexión a base de datos "+_url+" . . . Ok");
     }
   }
   catch(SQLException ex) {
      System.out.println("Hubo un problema al intentar conecarse a la base de datos"+_url);
   }
   catch(ClassNotFoundException ex) {
      System.out.println(ex);
   }  
 }
 /**
   * Takes the SQL query result
   * @param _query The SQL query to be executed
   * @return ResultSet of the query 
   */
 public ResultSet getQuery(String _query) {
    Statement state = null;
    ResultSet resultado = null;
    try{
      state = (Statement) conn.createStatement();
      resultado = state.executeQuery(_query);
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
    return resultado;
 }
 /**
   * Sets the query to execute it
   * @param _query The SQL query to be executed
   * @throws SQLException
   */
 public void setQuery(String _query) throws SQLException{
	 Statement state = null;   
	 state=(Statement) conn.createStatement();
	 state.execute(_query);
 }
  /**
   * @return The SQL connexion
   */
 public Connection getConn() {
	 return conn;
 } 
}
