/**
 * @file OutOfStockException.java
 * @author Edgar Azpiazu
 * @brief This file contains an exception created for a case where there is not stock of an asked product
 */

package vistas;

/**
* Constructor of the exception that specifies the text shown when it is thrown
*/
public class OutOfStockException extends Exception {
	public OutOfStockException (){
		super ("No queda stock de este producto ");
	}
}
