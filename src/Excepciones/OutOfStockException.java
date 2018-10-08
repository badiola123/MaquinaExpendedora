/**
 * @file OutOfStockException.java
 * @author Imanol Badiola
 * @brief This file contains an exception for the case of being a product out of stock
 */

package Excepciones;

/**
 * Constructor that outputs a string to console informing about the exception
 */
public class OutOfStockException extends Exception {
	public OutOfStockException (){
		super ("No queda stock de este producto ");
	}
}
