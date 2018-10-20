/**
 * @file MyDataAccess.java
 * @author Ainhoa Arruabarrena
 * @brief Class to get data from the data base
 */

package conexion_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDataAccess {

 private String usuario;
 private String pwd;
 private static String bd="maquina";
 static String url = "jdbc:mysql://localhost/"+bd;
 private Connection conn = null;
 private static final Logger LOGGER = Logger.getLogger(MyDataAccess.class.getName());
 
 /**
 * Constructor that initalizes the connection with the database
 * @param _usuario The SQL username
 * @param _pwd The SQL user password
 */
 public MyDataAccess(String usuario, String pwd) {
  
	 this.usuario = usuario;
	 this.pwd = pwd;
	 
   try{
	 Class.forName("com.mysql.jdbc.Connection");
     conn = DriverManager.getConnection(url, this.usuario, this.pwd);
   }
   catch(SQLException ex) {
	  LOGGER.info("Hubo un problema al intentar conecarse a la base de datos"+url);
   }
   catch(ClassNotFoundException ex) {
      LOGGER.info(ex.getMessage());
   }finally {
	   if(conn != null) {
		   LOGGER.info("Conexión a base de datos "+url+" . . . Ok");
	   }
   }
 }
 
 /**
   * Takes the SQL query result
   * @param _query The SQL query to be executed
   * @return ResultSet of the query 
   */
 public ResultSet getQuery(String query) {
    ResultSet resultado = null;
    try(Statement state = conn.createStatement()){
      resultado = state.executeQuery(query);
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
 public void setQuery(String query) throws SQLException{
	 try(Statement state = conn.createStatement()){
		 state.execute(query);
	 }
 }
  /**
   * @return The SQL connexion
   */
 public Connection getConn() {
	 return conn;
 } 
}
