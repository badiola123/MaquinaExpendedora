/**
 * @file DialogoEdicion.java
 * @author Imanol Badiola
 * @brief This file manages the dialog created when editing a client, 
 * machine, product, type of product, offer, sale or stock.
 */


package dialogos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clientes.Cliente;
import comunicacion.ModuloXBee;
import comunicacion.Recepcion;
import conexion_sql.Comandos;
import conexion_sql.MyDataAccess;
import maquinas.Maquina;
import oferta.Oferta;
import productos.Producto;
import stock.Stock;
import tipo_productos.TipoProducto;
import venta.Venta;
import vistas.Principal;

public class DialogoEdicion<T> extends JDialog implements ActionListener, Observer{

	private static final String IM_ERROR = "img/error.png";
	transient Toolkit toolkit;
	transient MyDataAccess conexion;
	transient ModuloXBee xBee;
	
	String[] opcion;
	
	JPanel panelDatos;
	transient Cliente cliente;
	transient Maquina maquina;
	transient Producto producto;
	transient TipoProducto tipoP;
	transient Oferta oferta;
	transient Venta venta;
	transient Stock stock;
	
	JTextField[] datos;
	String[] datosItem;
	int clase = -1;
	String primaryKey;
	
	/**
	  * Constructor of the class, sets size, closing operation, specifies the panel 
	  * that will be put inside and the options to include
	  * @param ventana Parent windows
	  * @param titulo Title of the dialog
    * @param modo Parameter that blocks any interaction with the parent window
    * @param dato The object to edit
    * @param conexion Database connection instance
    * @param xBee XBee connection instance
	  */
	public DialogoEdicion(JFrame ventana, String titulo, boolean modo, T dato, MyDataAccess conexion, ModuloXBee xBee){
		super(ventana, titulo, modo);
		this.conexion = conexion;
		this.xBee = xBee;
		if(xBee != null) xBee.getRecibir().addObserver(this);
		toolkit = Toolkit.getDefaultToolkit();
		this.setLocation((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()/100);
		this.cargarOpciones(dato);
		this.setContentPane(crearPanelVentana());
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/**
	  * Loads options to include in the dialog, depending on the editing element's class
	  * @param dato Object of element to edit
	  */
	private void cargarOpciones(T dato) {
		
		if(dato.getClass().equals(Cliente.class)){
			opcion = Cliente.getOpcionescliente();
			cliente = (Cliente) dato;
			datosItem = cliente.getDatos();
			primaryKey = cliente.getPrimaryKey();
			clase = 0;
			this.setSize((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()*9/10);
			if(xBee != null) xBee.mandarTramaDatosNuevoUsuario();
		}
		else if(dato.getClass().equals(Maquina.class)){
			opcion = Maquina.getOpcionesmaquina();
			maquina = (Maquina) dato;
			datosItem = maquina.getDatos();
			primaryKey = maquina.getPrimaryKey();
			clase = 1;
			this.setSize((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()*5/10);
		}
		else if(dato.getClass().equals(Producto.class)){
			opcion = Producto.getOpcionesproducto();
			producto = (Producto) dato;
			datosItem = producto.getDatos();
			primaryKey = producto.getPrimaryKey();
			clase = 2;
			this.setSize((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()*4/10);
		}
		else if(dato.getClass().equals(TipoProducto.class)){
			opcion = TipoProducto.getOpcionestipop();
			tipoP = (TipoProducto) dato;
			datosItem = tipoP.getDatos();
			primaryKey = tipoP.getPrimaryKey();
			clase = 3;
			this.setSize((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()*2/10);
		}
		else if(dato.getClass().equals(Oferta.class)){
			opcion = Oferta.getOpcionesoferta();
			oferta = (Oferta) dato;
			datosItem = oferta.getDatos();
			primaryKey = oferta.getPrimaryKey();
			clase = 4;
			this.setSize((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()*4/10);
		}
		else if(dato.getClass().equals(Venta.class)){
			opcion = Venta.getOpcionesVenta();
			venta = (Venta) dato;
			datosItem = venta.getDatos();
			primaryKey = venta.getPrimaryKey();
			clase = 5;
			this.setSize((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()*5/10);
		}
		else if(dato.getClass().equals(Stock.class)){
			opcion = Stock.getOpcionesstock();
			stock = (Stock) dato;
			datosItem = stock.getDatos();
			primaryKey = stock.getPrimaryKey();
			clase = 6;
			this.setSize((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()*5/10);
		}
	}

	/**
	  * Creates the panel inside the dialog 
	  * @return The panel object to put inside dialog
	  */
	private Container crearPanelVentana() {
		JPanel panel = new JPanel (new BorderLayout(0,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearPanelDatos(),BorderLayout.CENTER);
		panel.add(crearPanelBotones(),BorderLayout.SOUTH);
		return panel;
	}
	
	/**
	  * Creates the panel in which data is displayed
	  * @return The panel object 
	  */
	private Component crearPanelDatos() {
		panelDatos = new JPanel (new GridLayout(opcion.length,1,20,20));
		
		datos = new JTextField[opcion.length];
		for(int i = 0; i < datos.length; i++){
			datos[i] = new JTextField(20);
			datos[i].setText(datosItem[i]);
			panelDatos.add(crearTextField(datos[i],opcion[i]));
		}
		
		return panelDatos;
	}
	/**
	  * Creates a text field with a text and title
	  * @param text Text to put inside field
	  * @param titulo Title of the text field
	  * @return The text field object
	  */
	private Component crearTextField(JTextField text, String titulo) {
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.blue), titulo));
		
		panel.add(text);
		return panel;
	}
	
	/**
	  * Creates the panel in which buttons are put
	  * @return The panel object 
	  */
	private Component crearPanelBotones() {
		JPanel panel = new JPanel (new GridLayout(1,2,20,0));
		JButton bOk = new JButton ("OK");
		bOk.setActionCommand("ok");
		bOk.addActionListener(this);
		
		JButton bCancel = new JButton ("Cancelar");
		bCancel.setActionCommand("cancel");
		bCancel.addActionListener(this);
		panel.add(bOk);
		panel.add(bCancel);
		panel.setBackground(Color.WHITE);
		return panel;
	}

	/**
	  * Method called by the Button class when pressed, to react to different button's pulsation
	  * @param e Event occurred
	  */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ok")){
			guardarDatos();
		}
		else if(e.getActionCommand().equals("cancel")){
			this.dispose();
			if(clase == 0 && xBee != null) xBee.mandarTramaDatosNuevoUsuarioCancelado();
		}
	}

	/**
	  * Saves the data introduced in the dialog
	  */
	private void guardarDatos() {
		String nombreTabla = null;
		String[] nombreColumnas = null;
		boolean[] formatoColumnas = null;
		String[] datosUpdate = cargarDatosTF();
		Comandos comandos = new Comandos(conexion);
		
		try {
		switch(clase){
		case 0:
			nombreTabla = Principal.getTablacliente();
			nombreColumnas = Cliente.getNombreColumnas();
			formatoColumnas = Cliente.getFormatoColumnas();
			break;
		case 1:
			nombreTabla = Principal.getTablamaquina();
			nombreColumnas = Maquina.getNombreColumnas();
			formatoColumnas = Maquina.getFormatoColumnas();
			break;
		case 2:
			nombreTabla = Principal.getTablaproducto();
			nombreColumnas = Producto.getNombreColumnas();
			formatoColumnas = Producto.getFormatoColumnas();
			break;
		case 3:
			nombreTabla = Principal.getTablatipop();
			nombreColumnas = TipoProducto.getNombreColumnas();
			formatoColumnas = TipoProducto.getFormatoColumnas();
			break;
		case 4:
			nombreTabla = Principal.getTablaoferta();
			nombreColumnas = Oferta.getNombreColumnas();
			formatoColumnas = Oferta.getFormatoColumnas();
			break;
		case 5:
			nombreTabla = Principal.getTablaventa();
			nombreColumnas = Venta.getNombreColumnas();
			formatoColumnas = Venta.getFormatoColumnas();
			break;
		case 6:
			nombreTabla = Principal.getTablastock();
			nombreColumnas = Stock.getNombreColumnas();
			formatoColumnas = Stock.getFormatoColumnas();
			break;
		default: break;
		}
		if(nombreTabla != null){
			comandos.update(formatoColumnas, datosUpdate, nombreColumnas, nombreTabla, primaryKey);
		}
		this.dispose();
		} catch (SQLException e) {
			toolkit.beep();
			JOptionPane.showMessageDialog(null, e,
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
	}

	/**
	  * Loads the data introduced in the fields in an array of strings, to afterwards store in the method "guardarDatos()"
	  * @return Array of strings with the data of the fields
	  */
	private String[] cargarDatosTF() {
		String[] textos = new String[datos.length];
		
		for(int i = 0; i < datos.length; i++){
			textos[i] = datos[i].getText();
		}
		
		return textos;
	}

	/**
	  * Overridden method called for Observable-Observer pattern, to update when user detection is reported by the XBee receiver
	  * @param o Observable instance
    * @param obj Updated object
	  */
	@Override
	public void update(Observable o, Object obj) {
		if (obj instanceof Recepcion) {
			Recepcion recepcion = (Recepcion) obj;
			if(recepcion.getNumActualizacion() == 1) datos[0].setText(recepcion.getUsuarioID());
		}
	}
}
