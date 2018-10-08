/**
 * @file FechaIncorrecta.java
 * @author Edgar Azpiazu
 * @brief This file contains an exception created for an incorrect date
 */

package vistas;

public class FechaIncorrecta extends Exception {
	
  /**
	* Constructor of the exception that specifies the text shown when it is thrown
	*/
  public FechaIncorrecta (){
		super ("Introduce una fecha valida ");
	}
}
