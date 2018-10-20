/**
 * @file Principal.java
 * @author Edgar Azpiazu
 * @brief This file creates the panel that represents the main menu of the application
 */

package vistas;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comunicacion.LocalizadorXBee;
import comunicacion.ModuloXBee;
import conexion_sql.MyDataAccess;
import dialogos.DialogoContrasena;

public class Principal extends JFrame implements ActionListener, WindowListener{
	
	JPanel panelCentro, panelUsuario, panelTrabajador, panelRecargas, panelVentas, panelEstadisticas, panelStock;
	MyDataAccess conexion;
	ModuloXBee xBee;
			
	static final String FONDO_PRINCIPAL = "img/fondo_principal.jpg";
	static final String IMAGEN_USUARIOS = "img/usuario.png";
	static final String IMAGEN_VENDING = "img/machineMEGAGUAY.png";
	static final String IMAGEN_TRABAJADOR = "img/trabajador.png";
	static final String IMAGEN_CONSULTAS = "img/venta.png";
	static final String IMAGEN_ESTADISTICAS = "img/estadisticas.png";
	static final String IMAGEN_STOCK = "img/stock.png";
	
	private static final String IM_ERROR = "img/error.png";
	private static final String IM_XBEE = "iconos/xBee.png";
	
	private static final String PANEL_UNO = "panel usuario";
	private static final String PANEL_DOS = "panel trabajador";
	private static final String PANEL_TRES = "panel maquina";
	private static final String PANEL_CUATRO = "panel consultas";
	private static final String PANEL_CINCO = "panel inicio";
	private static final String PANEL_SEIS = "panel estadisticas";
	private static final String PANEL_SIETE = "panel stock";
	
	private static final String tablaCliente = "usuario";
	private static final String tablaMaquina = "maquina";
	private static final String tablaProducto = "producto";
	private static final String tablaTipoP = "tipo_producto";
	private static final String tablaOferta = "reposicion";
	private static final String tablaVenta = "venta";
	private static final String tablaStock = "stock";
	
  /**
	 * Constructor of the class which initializes the needed parameters to display it
	 */
	public Principal(){
		setTitle("Maquina Expendedora");
		setSize(2200, 900);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setIconImage((new ImageIcon(IMAGEN_VENDING).getImage()));
		String contrasena = getContrasena();
		conexion = new MyDataAccess("root", contrasena);
		comprobarConexion();
	}
	
  /**
	 * Gets the password for accessing the database from a dialog
   * @return A string with the password
	 */
	private String getContrasena() {
		DialogoContrasena dialogo = new DialogoContrasena(this, "Identificación", true);
		return dialogo.getPassword();
	}

  /**
	 * Checks wheter the connection with the database has been established in order to procced with the execution
	 */
	private void comprobarConexion() {
		if(conexion.getConn() == null){
			JOptionPane.showMessageDialog(null, "Contraseña incorrecta",
					"Conexión no establecida",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		else{
			inicializarXBee();
			setContentPane(crearPaneles());
			setVisible(true);
			this.addWindowListener(this);
		}
	}

  /**
	 * Checks for a connected XBee module and initializes it if possible
	 */
	private void inicializarXBee() {
		LocalizadorXBee localizador = new LocalizadorXBee();
		String puerto = localizador.getPuertoConectado();
		
		if (puerto != null) {
    		JOptionPane.showMessageDialog(null, "XBee conectada a puerto: "+puerto,
					"XBee encontrada", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(IM_XBEE));
    		xBee = new ModuloXBee(puerto, conexion);
    		
		} else {
			JOptionPane.showMessageDialog(null, "No se ha encontrado ninguna XBee conectada. Es ncesario reiniciar el programa para solucionar el problema.",
					"XBee no encontrada",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
	}

  /**
	 * Creates all the panels used in the application and adds them to a container from where they are accessible
   * @return A container containing all the necessary panels
	 */
	private Container crearPaneles() {
		panelCentro = new JPanel(new CardLayout());

		panelUsuario = new PanelUsuario(this, conexion);
		panelTrabajador = new PanelTrabajador(this, conexion, xBee);
		panelRecargas = new PanelRecargas(this, conexion);
		panelVentas = new PanelVentas(this, conexion);
		panelEstadisticas = new PanelEstadisticas(this, conexion);
		panelStock = new PanelStock(this, conexion);
		
		panelCentro.add(crearPanelPrincipal(), PANEL_CINCO);
		panelCentro.add(panelUsuario, PANEL_UNO);
		panelCentro.add(panelTrabajador, PANEL_DOS);
		panelCentro.add(panelRecargas, PANEL_TRES);
		panelCentro.add(panelVentas, PANEL_CUATRO);		
		panelCentro.add(panelStock, PANEL_SIETE);
		panelCentro.add(panelEstadisticas, PANEL_SEIS);
	
		return panelCentro;
	}

  /**
	 * Creates the main panel
   * @return A container containing the main panel
	 */
	private Container crearPanelPrincipal() {

		ImagenFondo panel = new ImagenFondo(FONDO_PRINCIPAL, new GridBagLayout());
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.add(botonTransparente(IMAGEN_USUARIOS, "usuarios", "INFORMACIÓN USUARIOS"));
		panel.add(botonTransparente(IMAGEN_TRABAJADOR, "trabajadores", "ADMINISTRACIÓN"));
		panel.add(botonTransparente(IMAGEN_VENDING, "vending", "RECARGAS"));
		panel.add(botonTransparente(IMAGEN_CONSULTAS, "consultas", "VENTAS"));
		panel.add(botonTransparente(IMAGEN_STOCK, "stock", "CREAR STOCK"));
		panel.add(botonTransparente(IMAGEN_ESTADISTICAS, "estadisticas", "ESTADÍSTICAS MAQUINAS"));
		return panel;
	}
	
  /**
	 * Creates a transparent button without background
   * @param i The file from where the image is loaded
	 * @param c The text the button includes
	 * @return The created button
	 */
	private Component botonTransparente(String i, String c, String titulo){
		JButton b = new JButton(new ImageIcon(i));
		b.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		b.setActionCommand(c);
		b.setToolTipText(titulo);
		b.addActionListener(this);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		return b;
	}
	
  /**
	 * Overridden method to specify the action when the buttons with an action listener are pressed 
	 * @param e The event which contains information about the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		CardLayout panel = (CardLayout) panelCentro.getLayout();
		if(e.getActionCommand().equals("usuarios")){
			((PanelUsuario) panelUsuario).actualizar();
			panel.show(panelCentro, PANEL_UNO);
		}else if(e.getActionCommand().equals("trabajadores")){
			panel.show(panelCentro, PANEL_DOS);
		}else if(e.getActionCommand().equals("vending")){			
			((PanelRecargas) panelRecargas).actualizar();
			panel.show(panelCentro, PANEL_TRES);
		}else if(e.getActionCommand().equals("consultas")){
			((PanelVentas) panelVentas).actualizar();
			panel.show(panelCentro, PANEL_CUATRO);
		}else if(e.getActionCommand().equals("estadisticas")){
			((PanelEstadisticas) panelEstadisticas).actualizar();
			panel.show(panelCentro, PANEL_SEIS);
		}else if(e.getActionCommand().equals("stock")){
			((PanelStock) panelStock).actualizar();
			panel.show(panelCentro, PANEL_SIETE);
		}
	}
	
  /**
	 * Allows to the user go back to the previous panel
	 */
	public void volver(){
		CardLayout panel = (CardLayout) panelCentro.getLayout();
		panel.show(panelCentro, PANEL_CINCO);
	}

  /**
	 * Getter of the client table from the databse 
	 * @return A String containing tha name of the client table in the database
	 */
	public static String getTablacliente() {
		return tablaCliente;
	}

  /**
	 * Getter of the machine table from the databse 
	 * @return A String containing tha name of the machine table in the database
	 */
	public static String getTablamaquina() {
		return tablaMaquina;
	}

  /**
	 * Getter of the product table from the databse 
	 * @return A String containing tha name of the product table in the database
	 */
	public static String getTablaproducto() {
		return tablaProducto;
	}

  /**
	 * Getter of the product type table from the databse 
	 * @return A String containing tha name of the product type table in the database
	 */
	public static String getTablatipop() {
		return tablaTipoP;
	}
	
  /**
	 * Getter of the offer table from the databse 
	 * @return A String containing tha name of the offer table in the database
	 */
	public static String getTablaoferta() {
		return tablaOferta;
	}
	
  /**
	 * Getter of the sale table from the databse 
	 * @return A String containing tha name of the sale table in the database
	 */
	public static String getTablaventa() {
		return tablaVenta;
	}
	
  /**
	 * Getter of the stock table from the databse 
	 * @return A String containing tha name of the stock table in the database
	 */
	public static String getTablastock() {
		return tablaStock;
	}

  /**
	 * Overridden method invoked when the Window is set to be the active Window
	 * @param e The event which contains information about the action
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

  /**
	 * Overridden method invoked when a window has been closed as the result of calling dispose on the window
	 * @param e The event which contains information about the action
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		if(xBee != null) xBee.cerrarModulo();
	}

  /**
	 * Overridden method invoked when the user attempts to close the window from the window's system menu
	 * @param e The event which contains information about the action
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		if(xBee != null) xBee.cerrarModulo();
	}

  /**
	 * Overridden method invoked when a Window is no longer the active Window
	 * @param e The event which contains information about the action
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

  /**
	 * Overridden method invoked when a window is changed from a minimized to a normal state
	 * @param e The event which contains information about the action
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

  /**
	 * Overridden method invoked when a window is changed from a normal to a minimized state
	 * @param e The event which contains information about the action
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

  /**
	 * Overridden method invoked the first time a window is made visible
	 * @param e The event which contains information about the action
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
