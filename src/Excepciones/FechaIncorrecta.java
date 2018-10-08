/**
 * @file FechaIncorrecta.java
 * @author Imanol Badiola
 * @brief This file contains an exception for the case of introducing an incorrect date
 */

package Excepciones;

/**
 * Constructor that outputs a string to console informing about the exception
 */
public class FechaIncorrecta extends Exception {
	public FechaIncorrecta (){
		super ("Introduce una fecha valida ");
	}
}
