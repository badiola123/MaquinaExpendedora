package Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import vistas.Principal;

public class ListaStock extends AbstractTableModel{

	List<Stock> listaEntera;
	List<Stock> lista;
	ModeloColumnasTablaStock columnas;
	MyDataAccess conexion;
	
	private static final String IM_ERROR = "img/error.png";
	
	public ListaStock (ModeloColumnasTablaStock columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	static public List<Stock> cargarDatos(MyDataAccess conexion) {
		Stock stock;
		List<Stock> lista = new ArrayList<>();
		String[] datos = new String[Stock.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		try {
			resultado = comandos.select(null, Principal.getTablastock(), null, null, null, false, 0);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar stock",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
		resultado = conexion.getQuery("select * from " + Principal.getTablastock());
		
	    try {
	        while(resultado.next()){
	        	
	        	for(int i = 1; i < (Stock.getNombreColumnas().length + 1); i++){
	        		datos[i-1] = resultado.getString(i);
	        		System.out.println(datos[i-1]);
	        	}
		        System.out.println("\n");
	        	
		        stock = new Stock(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), datos[2], Integer.valueOf(datos[3]), Integer.valueOf(datos[4]));
		        
		        if(stock != null){
		        	lista.add(stock);
		        }
		       
	        }
	      }catch (SQLException e) {
	        e.printStackTrace();
	     } 
	    return lista;
	}

	@Override
	public int getColumnCount() {
		return columnas.getColumnCount();
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		Stock stock = lista.get(fila);
		return stock.getFieldAt(columna);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	public Stock getStock(int pos){
		return lista.get(pos);
	}
}
