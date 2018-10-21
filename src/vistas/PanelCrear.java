/**
 * @file PanelCrear.java
 * @author Edgar Azpiazu
 * @brief This file creates the panel to create new different objects
 */

package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


import clientes.Cliente;

import comunicacion.ModuloXBee;
import comunicacion.Recepcion;
import conexion_sql.Comandos;
import conexion_sql.MyDataAccess;
import maquinas.Maquina;
import productos.Producto;
import tipo_productos.TipoProducto;


public class PanelCrear extends JPanel implements ItemListener, ActionListener, Observer{
	JTable vTabla;
	JScrollPane panelScrollCampos;
	transient Toolkit toolkit;

	
	PanelTrabajador panelTrabajador;
	JFrame principal;
	transient MyDataAccess conexion;
	transient ModuloXBee xBee;
	
	JPanel panelOpciones;
	ButtonGroup opciones;
	JRadioButton rbCliente;
	JRadioButton rbMaquina;
	JRadioButton rbProducto;
	JRadioButton rbTipoP;
	boolean selected = false;
	
	JTextField[] datosTextField;
	JLabel[] datosLabel;
	String[] datosItem;
	
	static final String IMAGEN_OK = "img/ok.png";
	static final String IMAGEN_ATRAS = "img/atras.png";
	private static final String IM_ERROR = "img/error.png";
	private static final String IM_COMPLETADO = "img/completado.png";
	private static final String USEDFONT = "Arial";
	
  /**
	 * Constructor of the class which initializes the needed parameters to display it
	 * @param v The JPanel from where this panel is accessible
	 * @param conexion Instance if the conection to the database
	 * @param principal Main class where different parameters are defined
   * @param xBee Instance of the XBee module
	 */
	public PanelCrear(JPanel v, MyDataAccess conexion, Principal principal, ModuloXBee xBee){
		super(new BorderLayout());
		this.principal = principal;
		panelTrabajador = (PanelTrabajador) v;
		this.conexion = conexion;
		this.xBee = xBee;
		if(xBee != null) xBee.getRecibir().addObserver(this);
		toolkit = Toolkit.getDefaultToolkit();
		panelScrollCampos = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(crearPanelOpciones(),BorderLayout.NORTH);
		this.add(crearCamposClientes(),BorderLayout.CENTER);
		this.add(crearPanelBotones(),BorderLayout.SOUTH);
	}

  /**
	 * Loads the necessary fields to create a client
	 * @return The scroll pane to which this fields has been asigned
	 */
	private Component crearCamposClientes() {		
		datosItem = Cliente.getOpcionescliente();
		datosLabel = new JLabel[datosItem.length];
		datosTextField = new JTextField[datosItem.length];
		
		crearCampos();
		
		return panelScrollCampos;
	}
	
  /**
	 * Loads the necessary fields to create a machine
	 * @return The scroll pane to which this fields has been asigned
	 */
	private Component crearCamposMaquinas() {		
		datosItem = Maquina.getOpcionesmaquina();
		datosLabel = new JLabel[datosItem.length];
		datosTextField = new JTextField[datosItem.length];
		
		crearCampos();
		
		return panelScrollCampos;
	}
	
  /**
	 * Loads the necessary fields to create a product
	 * @return The scroll pane to which this fields has been asigned
	 */
	private Component crearCamposProductos() {		
		datosItem = Producto.getOpcionesproducto();
		datosLabel = new JLabel[datosItem.length];
		datosTextField = new JTextField[datosItem.length];
		
		crearCampos();
		
		return panelScrollCampos;
	}
	
  /**
	 * Loads the necessary fields to create a product type
	 * @return The scroll pane to which this fields has been asigned
	 */
	private Component crearCamposTipoProductos() {		
		datosItem = TipoProducto.getOpcionestipop();
		datosLabel = new JLabel[datosItem.length];
		datosTextField = new JTextField[datosItem.length];
		
		crearCampos();
		
		return panelScrollCampos;
	}

  /**
	 * Sets the necessary fields to create a new input on a layout and styles it
	 */
	private void crearCampos() {
		JPanel panel = new JPanel(new GridLayout(datosItem.length,1,0,25));
		
		for(int i = 0; i < datosItem.length; i++){
			panel.add(crearPanelCampo(datosItem[i], i));
		}
		
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
						"Datos"),
				BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		
		panelScrollCampos.setViewportView(panel);
	}

  /**
	 * Creates the panel where a field for the new input is added
   * @param tituloLabel Label for the input
	 * @param i The number from the total labels
	 * @return The created panel
	 */
	private JPanel crearPanelCampo(String tituloLabel, int i) {
		JPanel panel = new JPanel(new GridLayout(1,2,20,25));
		datosLabel[i]= new JLabel(tituloLabel);
		datosTextField[i] = new JTextField (20);
		datosTextField[i].setBorder(BorderFactory.createLoweredBevelBorder());
		panel.add(datosLabel[i]);
		panel.add(datosTextField[i]);
		return panel;
	}
	
  /**
	 * Creates the panel for the OK and cancel buttons
	 * @return The created panel
	 */
	private Component crearPanelBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0,(int)toolkit.getScreenSize().getWidth()/4,0,(int)toolkit.getScreenSize().getWidth()/4)); 
		panel.add(botonTransparente(IMAGEN_OK, "ok"), BorderLayout.WEST);
		panel.add(botonTransparente(IMAGEN_ATRAS, "cancel"), BorderLayout.EAST);
		panel.setBackground(Color.WHITE);
		return panel;
	}
	
  /**
	 * Creates a transparent button without background
   * @param i The file from where the image is loaded
	 * @param c The text the button includes
	 * @return The created button
	 */
	private Component botonTransparente(String i, String c){
		JButton b = new JButton(new ImageIcon(i));
		b.setActionCommand(c);
		b.addActionListener(this);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		return b;
	}

  /**
	 * Creates the panel to choose between the different input types
	 * @return The created panel
	 */
	private Component crearPanelOpciones() {
		panelOpciones = new JPanel (new GridLayout(1,2,60,60));
		panelOpciones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panelOpciones.setBackground(Color.CYAN);
		opciones = new ButtonGroup();	
		
		rbCliente = new JRadioButton("Clientes");
		rbCliente.setHorizontalAlignment(JLabel.RIGHT);
		rbCliente.setFont(new Font(USEDFONT, Font.PLAIN, 30));
		rbCliente.setBackground(Color.CYAN);
		rbCliente.addItemListener(this);
		
		rbMaquina = new JRadioButton("Máquinas");
		rbMaquina.setHorizontalAlignment(JLabel.CENTER);
		rbMaquina.setFont(new Font(USEDFONT, Font.PLAIN, 30));
		rbMaquina.setBackground(Color.CYAN);
		rbMaquina.addItemListener(this);
		
		rbProducto = new JRadioButton("Productos");
		rbProducto.setHorizontalAlignment(JLabel.LEFT);
		rbProducto.setFont(new Font(USEDFONT, Font.PLAIN, 30));
		rbProducto.setBackground(Color.CYAN);
		rbProducto.addItemListener(this);
		
		rbTipoP = new JRadioButton("Tipo productos");
		rbTipoP.setHorizontalAlignment(JLabel.LEFT);
		rbTipoP.setFont(new Font(USEDFONT, Font.PLAIN, 30));
		rbTipoP.setBackground(Color.CYAN);
		rbTipoP.addItemListener(this);
		
		rbCliente.setSelected(true);
		
		opciones.add(rbCliente);
		opciones.add(rbMaquina);
		opciones.add(rbProducto);
		opciones.add(rbTipoP);
		
		panelOpciones.add(rbCliente);
		panelOpciones.add(rbMaquina);
		panelOpciones.add(rbProducto);
		panelOpciones.add(rbTipoP);
		
		return panelOpciones;
	}

  /**
	 * Overridden method to specify the action when a different radio button is selected 
	 * @param e The event which contains information about the radio buttons' status
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getStateChange()==ItemEvent.SELECTED){
			
			if ((JRadioButton)e.getSource()==rbCliente){
				this.add(crearCamposClientes(),BorderLayout.CENTER);
				if(xBee != null) xBee.mandarTramaDatosNuevoUsuario();
				selected = true;
			}
			else{
				if(selected){
					if(xBee != null) xBee.mandarTramaDatosNuevoUsuarioCancelado();
					selected = false;
				}
				checkRadioButton(e);
			}
		}
		
	}
	
	private void checkRadioButton(ItemEvent e) {
		if ((JRadioButton)e.getSource()==rbMaquina){
			this.add(crearCamposMaquinas(),BorderLayout.CENTER);
		}
		else if ((JRadioButton)e.getSource()==rbProducto){
			this.add(crearCamposProductos(),BorderLayout.CENTER);
		}
		else if ((JRadioButton)e.getSource()==rbTipoP){
			this.add(crearCamposTipoProductos(),BorderLayout.CENTER);
		}
	}

/**
	 * Overridden method to specify the action when the buttons with an action listener are pressed 
	 * @param e The event which contains information about the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")){
			if(rbCliente.isSelected() && xBee != null) xBee.mandarTramaDatosNuevoUsuarioCancelado();
			panelTrabajador.volver();
		}
		else if(e.getActionCommand().equals("ok")){
			Comandos comandos = new Comandos(conexion);
			String[] datos = cargarDatosTF();
			
			try {
			if(rbCliente.isSelected()){
				crearCliente(comandos, datos);
			}
			else if(rbMaquina.isSelected()){
				crearMaquina(comandos, datos);
			}
			else if(rbProducto.isSelected()){
				crearProducto(comandos, datos);
			}
			else if(rbTipoP.isSelected()){
				crearTipoP(comandos, datos);
			}
			
			limpiarTextField();
			
			JOptionPane.showMessageDialog(null, "Se ha insertado el dato",
					"Dato insertado",JOptionPane.INFORMATION_MESSAGE, new ImageIcon(IM_COMPLETADO));
			
			} catch (SQLException e1) {
				toolkit.beep();
				JOptionPane.showMessageDialog(null, e1,
						"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
			}
		}
	}

  /**
	 * Clears the text fields where the new input information was written
	 */
	private void limpiarTextField() {
		for(int i = 0; i < datosTextField.length; i++){
			datosTextField[i].setText("");
		}
	}

  /**
	 * Cleates a new product type
   * @param comandos Instance of the class Comandos to create commands for the database
   * @param datos Data from the input fields to create the new object
	 */
	private void crearTipoP(Comandos comandos, String[] datos) throws SQLException {
		boolean[] formatoColumnas = null;
		formatoColumnas = TipoProducto.getFormatoColumnas();
		
		comandos.insertar(formatoColumnas, datos, Principal.getTablatipop());
	}

  /**
	 * Cleates a new product
   * @param comandos Instance of the class Comandos to create commands for the database
   * @param datos Data from the input fields to create the new object
	 */
	private void crearProducto(Comandos comandos, String[] datos) throws SQLException {
		boolean[] formatoColumnas = null;
		formatoColumnas = Producto.getFormatoColumnas();
		
		comandos.insertar(formatoColumnas, datos, Principal.getTablaproducto());
	}

  /**
	 * Cleates a new machine
   * @param comandos Instance of the class Comandos to create commands for the database
   * @param datos Data from the input fields to create the new object
	 */
	private void crearMaquina(Comandos comandos, String[] datos) throws SQLException {
		boolean[] formatoColumnas = null;
		formatoColumnas = Maquina.getFormatoColumnas();
		
		comandos.insertar(formatoColumnas, datos, Principal.getTablamaquina());
	}

  /**
	 * Cleates a new client
   * @param comandos Instance of the class Comandos to create commands for the database
   * @param datos Data from the input fields to create the new object
	 */
	private void crearCliente(Comandos comandos, String[] datos) throws SQLException {
		boolean[] formatoColumnas = null;
		formatoColumnas = Cliente.getFormatoColumnas();
		
		comandos.insertar(formatoColumnas, datos, Principal.getTablacliente());
	}
	
  /**
	 * Loads the data from the input fields into an array of Strings
   * @return An array of Strings with the data from the input fields
	 */
	private String[] cargarDatosTF() {
		String[] textos = new String[datosTextField.length];
		
		for(int i = 0; i < datosTextField.length; i++){
			textos[i] = datosTextField[i].getText();
		}
		
		return textos;
	}

  /**
	 * This method is called whenever the observer object is changed
   * @param o The observable object
   * @param obj An argument passed to the notifyObservers method
	 */
	@Override
	public void update(Observable o, Object obj) {
		if (obj instanceof Recepcion) {
			Recepcion recepcion = (Recepcion) obj;
			if(recepcion.getNumActualizacion() == 1) datosTextField[0].setText(recepcion.getUsuarioID());
		}
	}
}
