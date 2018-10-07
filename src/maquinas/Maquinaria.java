package maquinas;

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
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import vistas.Principal;

public class Maquinaria extends AbstractTableModel{
	
	List<Maquina> listaEntera;
	List<Maquina> lista;
	ModeloColumnasTablaMaquina columnas;
	MyDataAccess conexion;
	
	private static final String IM_ERROR = "img/error.png";
	
	public Maquinaria (ModeloColumnasTablaMaquina columnas, MyDataAccess conexion){
		super();
		this.conexion = conexion;
		lista = new ArrayList<>();
		lista = cargarDatos(conexion);
		listaEntera = lista;
		this.columnas = columnas;
	}
	
	static public List<Maquina> cargarDatos(MyDataAccess conexion) {
		Maquina maquina;
		List<Maquina> lista = new ArrayList<>();
		String[] datos = new String[Maquina.getNombreColumnas().length];
		ResultSet resultado = null;
		Comandos comandos = new Comandos(conexion);	
		
		try {
			resultado = comandos.select(null, Principal.getTablamaquina(), null, null, null, false, 0);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error al cargar maquinas",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
	    try {
	        while(resultado.next()){
	        	
	        	for(int i = 1; i < (Maquina.getNombreColumnas().length + 1); i++){
	        		datos[i-1] = resultado.getString(i);
	        		System.out.println(datos[i-1]);
	        	}
		        System.out.println("\n");
		        
		        maquina = new Maquina(Integer.valueOf(datos[0]), datos[1], datos[2], Integer.valueOf(datos[3]), datos[4]);
		        
		        if(maquina != null){
		        	lista.add(maquina);
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
		Maquina maquina = lista.get(fila);
		return maquina.getFieldAt(columna);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}
	
	public Maquina getMaquina(int pos){
		return lista.get(pos);
	}
}
