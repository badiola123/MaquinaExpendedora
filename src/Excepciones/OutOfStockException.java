package Excepciones;

public class OutOfStockException extends Exception {
	public OutOfStockException (){
		super ("No queda stock de este producto ");
	}
}
