/**
 * @file Mapeador.java
 * @author Imanol Badiola
 * @brief Mapper interface
 */

package clientes;


public interface Mapeador<T> {
	
	/**
	 * Mapper method of interface
	 * @param t Generic type for mapping
	 * @return String of 
	 */
	public String map(T t);
}
