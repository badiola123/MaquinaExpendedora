/**
 * @file DialogoInfoUsuario.java
 * @author Imanol Badiola
 * @brief This file manages the dialog of the information of a user
 */


package dialogos;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;


import clientes.Cliente;
import conexion_sql.Comandos;
import conexion_sql.MyDataAccess;
import maquinas.Maquina;

import productos.Producto;
import tipoProductos.TipoProducto;

import vistas.Principal;

public class DialogoInfoUsuario extends JDialog {

	Toolkit toolkit;
	MyDataAccess conexion;
	Cliente cliente;
	Comandos comandos;
	
	JLabel labelMaquina;
	JLabel labelTipo;
	JLabel labelProducto;	
	
	/**
	  * Constructor of the class, sets size, closing operation, specifies the panel 
	  * that will be put inside and the data to show
	  * @param ventana Parent windows
	  * @param titulo Title of the dialog
      * @param modo Parameter that blocks any interaction with the parent window
      * @param conexion Database connection instance
      * @param cliente Client to show at dialog
	  */
	public DialogoInfoUsuario(JFrame ventana, String titulo, boolean modo, MyDataAccess conexion, Cliente cliente){
		super(ventana, titulo, modo);
		this.conexion = conexion;
		comandos = new Comandos(conexion);
		this.cliente = cliente;
		toolkit = Toolkit.getDefaultToolkit();
		this.setLocation((int)toolkit.getScreenSize().getWidth()*3/8, (int)toolkit.getScreenSize().getHeight()/5);
		this.setSize((int)toolkit.getScreenSize().getWidth()/4, (int)toolkit.getScreenSize().getHeight()/2);
		this.setContentPane(crearPanelVentana());
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	  * Creates the panel inside the dialog  
	  * @return The dialog panel object
	  */
	private Container crearPanelVentana() {
		JPanel panel = new JPanel (new GridLayout(3, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(cargarMaquina());
		panel.add(cargarTipo());
		panel.add(cargarProducto());
		panel.setBackground(Color.WHITE);
		return panel;
	}

	/**
	  * Loads from database user's most used vending machine
	  * @return Panel with information of the machine
	  */
	private Component cargarMaquina() {
		labelMaquina = new JLabel();
		JPanel panel = crearLabel(labelMaquina, "Máquina mas frecuentada");
		String[] datos = new String[Maquina.getNombreColumnas().length];
		Maquina maquina;
		String[] columnas = Maquina.getNombreColumnas();
		String[] columnasConPrefijo = new String[columnas.length];
		for(int i = 0; i < columnas.length; i++){
			columnasConPrefijo[i] = columnas[i];
		}
		
		columnasConPrefijo[0] = Principal.getTablamaquina() + "." + columnasConPrefijo[0];
		//String seleccion = "maquina m JOIN venta v ON m.maquina_id = v.maquina_id";
		String seleccion = Principal.getTablamaquina() + " join " + Principal.getTablaventa() 
		+ " on " + Principal.getTablamaquina() + "." + Maquina.getNombreColumnas()[0] + " = " 
				+ Principal.getTablaventa() + "." + Maquina.getNombreColumnas()[0];
		
		//String agrupacion = "v.maquina_id";
		String agrupacion = Principal.getTablaventa() + "." + Maquina.getNombreColumnas()[0];
		String orden = "count(*)";
		boolean sentidoOrden = false;
		int limitar = 1;
		
		try {
			ResultSet resultado = comandos.select(columnasConPrefijo, seleccion, cliente.getPrimaryKey(), agrupacion, orden, sentidoOrden, limitar);
        	if(resultado.next()){
    			for(int i = 1; i < (Maquina.getNombreColumnas().length + 1); i++){
            		datos[i-1] = resultado.getString(i);
            		System.out.println(datos[i-1]);
            	}
    			
    			 maquina = new Maquina(Integer.valueOf(datos[0]), datos[1], datos[2], Integer.valueOf(datos[3]), datos[4]);
    			 labelMaquina.setText(maquina.toString());
        	}
		} catch (SQLException e) {
			labelMaquina.setText("No se ha podido cargar una maquina favorita");
		}
		
		return panel;
	}

	/**
	  * Loads from database user's most bought product type
	  * @return Panel with information of the product type
	  */
	private Component cargarTipo() {
		labelTipo = new JLabel();
		JPanel panel = crearLabel(labelTipo, "Tipo de producto más comprado");
		String[] datos = new String[TipoProducto.getNombreColumnas().length];
		TipoProducto tipoP;
		String[] columnas = TipoProducto.getNombreColumnas();
		String[] columnasConPrefijo = new String[columnas.length];
		for(int i = 0; i < columnas.length; i++){
			columnasConPrefijo[i] = columnas[i];
		}
		
		columnasConPrefijo[0] = Principal.getTablatipop() + "." + columnasConPrefijo[0];		
		//String seleccion = "venta v JOIN producto p ON v.producto_id = p.producto_id JOIN tipo_producto tp ON p.p_tipo_id = tp.tipo_producto_id";
		String seleccion = Principal.getTablaventa() + " JOIN " + Principal.getTablaproducto() + " ON " + Principal.getTablaventa() + "." 
						  + Producto.getNombreColumnas()[0] + " = " + Principal.getTablaproducto() + "." + Producto.getNombreColumnas()[0] + " JOIN " 
						  + Principal.getTablatipop() + " ON " + Principal.getTablaproducto() + "." + Producto.getNombreColumnas()[3] + " = " 
						  + Principal.getTablatipop() + "." + TipoProducto.getNombreColumnas()[0];
		
		//String agrupacion = "tp.tipo_producto_id";
		String agrupacion = Principal.getTablatipop() + "." + TipoProducto.getNombreColumnas()[0];
				
		String orden = "count(*)";
		boolean sentidoOrden = false;
		int limitar = 1;
		
		try {
			ResultSet resultado = comandos.select(columnasConPrefijo, seleccion, cliente.getPrimaryKey(), agrupacion, orden, sentidoOrden, limitar);
        	if(resultado.next()){
    			for(int i = 1; i < (TipoProducto.getNombreColumnas().length + 1); i++){
            		datos[i-1] = resultado.getString(i);
            		System.out.println(datos[i-1]);
            	}
    			
    			tipoP = new TipoProducto(Integer.valueOf(datos[0]), datos[1]);
    			 labelTipo.setText(tipoP.toString());
        	}
		} catch (SQLException e) {
			labelTipo.setText("No se ha podido cargar un tipo de producto favorito");
		}
		
		return panel;
	}

	/**
	  * Loads from database user's most bought product
	  * @return Panel with information of the product
	  */
	private Component cargarProducto() {
		labelProducto = new JLabel();
		JPanel panel = crearLabel(labelProducto, "Producto favorito");
		String[] datos = new String[Producto.getNombreColumnas().length];
		Producto producto;
		String[] columnas = Producto.getNombreColumnas();
		String[] columnasConPrefijo = new String[columnas.length];
		for(int i = 0; i < columnas.length; i++){
			columnasConPrefijo[i] = columnas[i];
		}
		
		columnasConPrefijo[0] = Principal.getTablaproducto() + "." + columnasConPrefijo[0];
		
		//String seleccion = "venta v JOIN producto p ON v.producto_id = p.producto_id";
		String seleccion = Principal.getTablaventa() + " JOIN " + Principal.getTablaproducto() 
						   + " ON " + Principal.getTablaventa() + "." + Producto.getNombreColumnas()[0]
						   + " = " + Principal.getTablaproducto() + "." + Producto.getNombreColumnas()[0];
		
		//String agrupacion = "p.producto_id";
		String agrupacion = Principal.getTablaproducto() + "." + Producto.getNombreColumnas()[0];
		
		String orden = "count(*)";
		boolean sentidoOrden = false;
		int limitar = 1;
		
		try {
			ResultSet resultado = comandos.select(columnasConPrefijo, seleccion, cliente.getPrimaryKey(), agrupacion, orden, sentidoOrden, limitar);
        	if(resultado.next()){
    			for(int i = 1; i < (Producto.getNombreColumnas().length + 1); i++){
            		datos[i-1] = resultado.getString(i);
            		System.out.println(datos[i-1]);
            	}
    			
    			producto = new Producto(Integer.valueOf(datos[0]), datos[1], Double.valueOf(datos[2]), Integer.valueOf(datos[3]));
    			labelProducto.setText(producto.toString());
        	}
		} catch (SQLException e) {
			labelProducto.setText("No se ha podido cargar un producto favorito");
		}
		
		return panel;
	}

	/**
	  * Creates a panel with a label
	  * @return The panel object
	  */
	private JPanel crearLabel(JLabel label, String titulo) {
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.blue), titulo));
		
		panel.add(label);
		panel.setBackground(Color.WHITE);
		return panel;
	}
}
