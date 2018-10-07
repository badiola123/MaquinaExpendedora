package Dialogos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Productos.Producto;
import Stock.Stock;
import clientes.Cliente;
import comunicacion.ModuloXBee;
import comunicacion.Recepcion;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import maquinas.Maquina;
import oferta.Oferta;
import tipoProductos.TipoProducto;
import venta.Venta;
import vistas.Principal;

public class DialogoEdicion<T> extends JDialog implements ActionListener, Observer{

	private static final String IM_ERROR = "img/error.png";
	Toolkit toolkit;
	MyDataAccess conexion;
	ModuloXBee xBee;
	
	String[] opcion;
	
	JPanel panelDatos;
	Cliente cliente;
	Maquina maquina;
	Producto producto;
	TipoProducto tipoP;
	Oferta oferta;
	Venta venta;
	Stock stock;
	
	JTextField[] datos;
	String[] datosItem;
	int clase = -1;
	String primaryKey;
	
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
	
	private void cargarOpciones(T dato) {
		
		if(dato.getClass().equals(Cliente.class)){
			opcion = Cliente.getOpcionescliente();
			cliente = (Cliente) dato;
			datosItem = cliente.getDatos();
			primaryKey = cliente.getPrimaryKey();
			clase = 0;
			this.setSize((int)toolkit.getScreenSize().getWidth()/3, (int)toolkit.getScreenSize().getHeight()*9/10);
			if(xBee != null) xBee.mandarTramaDatosNuevoUsuario();
			System.out.println("mandando nuuevo usuario");
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

	private Container crearPanelVentana() {
		JPanel panel = new JPanel (new BorderLayout(0,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearPanelDatos(),BorderLayout.CENTER);
		panel.add(crearPanelBotones(),BorderLayout.SOUTH);
		return panel;
	}
	
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
	
	private Component crearTextField(JTextField text, String titulo) {
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.blue), titulo));
		
		panel.add(text);
		return panel;
	}
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ok")){
			guardarDatos();
		}
		else if(e.getActionCommand().equals("cancel")){
			this.dispose();
			if(clase == 0 && xBee != null) xBee.mandarTramaDatosNuevoUsuarioCancelado();
			System.out.println("mandando nuuevo usuario cancelado");
		}
	}

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

	private String[] cargarDatosTF() {
		String[] textos = new String[datos.length];
		
		for(int i = 0; i < datos.length; i++){
			textos[i] = datos[i].getText();
		}
		
		return textos;
	}

	@Override
	public void update(Observable o, Object obj) {
		if (obj instanceof Recepcion) {
			Recepcion recepcion = (Recepcion) obj;
			if(recepcion.getNumActualizacion() == 1) datos[0].setText(recepcion.getUsuarioID());
		}
	}
}
