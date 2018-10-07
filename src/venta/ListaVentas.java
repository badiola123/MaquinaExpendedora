package venta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import clientes.ModeloColumnasTabla;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import oferta.Oferta;
import vistas.Principal;

public class ListaVentas extends AbstractTableModel{

	List<Venta> listaEntera;
	List<Venta> lista;
	ModeloColumnasTablaVenta columnas;
	MyDataAccess conexion;
	
	private static final String IM_ERROR = "img/error.png";
	
	public ListaVentas (ModeloColumnasTablaVenta columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	static public List<Venta> cargarDatos(MyDataAccess conexion) {
		Venta venta;
		List<Venta> lista = new ArrayList<>();
		String[] datos = new String[Venta.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);		
		
		try {
			resultado = comandos.select(null, Principal.getTablaventa(), null, null, null, false, 0);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar ventas",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
	    try {
	        while(resultado.next()){
	        	
	        	for(int i = 1; i < (Venta.getNombreColumnas().length + 1); i++){
	        		datos[i-1] = resultado.getString(i);
	        		System.out.println(datos[i-1]);
	        	}
		        System.out.println("\n");
	        	
		        venta = new Venta(datos[0], Integer.valueOf(datos[1]), Integer.valueOf(datos[2]), Double.valueOf(datos[3]), datos[4]);
		        
		        if(venta != null){
		        	lista.add(venta);
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
		Venta venta = lista.get(fila);
		return venta.getFieldAt(columna);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	public Venta getVenta(int pos){
		return lista.get(pos);
	}
}
