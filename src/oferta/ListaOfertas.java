package oferta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import clientes.Cliente;
import clientes.Mapeador;
import clientes.ModeloColumnasTabla;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import vistas.Principal;

public class ListaOfertas extends AbstractTableModel{

	List<Oferta> listaEntera;
	List<Oferta> lista;
	ModeloColumnasTablaOferta columnas;
	MyDataAccess conexion;
	
	private static final String IM_ERROR = "img/error.png";
	
	public ListaOfertas (ModeloColumnasTablaOferta columnas, MyDataAccess conexion){
		super();
		lista = new ArrayList<>();
		this.conexion = conexion;
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	static public List<Oferta> cargarDatos(MyDataAccess conexion) {
		Oferta oferta;
		List<Oferta> lista = new ArrayList<>();
		String[] datos = new String[Oferta.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		try {
			resultado = comandos.select(null, Principal.getTablaoferta(), null, null, null, false, 0);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar recargas",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
	    try {
	        while(resultado.next()){
	        	
	        	for(int i = 1; i < (Oferta.getNombreColumnas().length + 1); i++){
	        		datos[i-1] = resultado.getString(i);
	        		System.out.println(datos[i-1]);
	        	}
		        System.out.println("\n");
	        	
		        oferta = new Oferta(Integer.valueOf(datos[0]), Integer.valueOf(datos[1]), datos[2], Integer.valueOf(datos[3]));
		        
		        if(oferta != null){
		        	lista.add(oferta);
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
		Oferta oferta = lista.get(fila);
		return oferta.getFieldAt(columna);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	public Oferta getOferta(int pos){
		return lista.get(pos);
	}
}
