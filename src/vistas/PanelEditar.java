/**
 * @file PanelEditar.java
 * @author Edgar Azpiazu
 * @brief This file creates the panel to edit different existing objects
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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import clientes.Cliente;
import clientes.Clientela;
import clientes.ModeloColumnasTabla;
import clientes.TrazadorTabla;
import comunicacion.ModuloXBee;
import comunicacion.Recepcion;
import conexion_sql.Comandos;
import conexion_sql.MyDataAccess;
import dialogos.DialogoEdicion;
import maquinas.Maquina;
import maquinas.Maquinaria;
import maquinas.ModeloColumnasTablaMaquina;
import maquinas.TrazadorTablaMaquina;
import oferta.ListaOfertas;
import oferta.ModeloColumnasTablaOferta;
import oferta.Oferta;
import oferta.TrazadorTablaOferta;
import productos.Inventario;
import productos.ModeloColumnasTablaProducto;
import productos.Producto;
import productos.TrazadorTablaProducto;
import stock.ListaStock;
import stock.ModeloColumnasTablaStock;
import stock.Stock;
import stock.TrazadorTablaStock;
import tipo_productos.ModeloColumnasTablaTipoProductos;
import tipo_productos.TipoProducto;
import tipo_productos.TiposLista;
import tipo_productos.TrazadorTablaTipoProductos;
import venta.ListaVentas;
import venta.ModeloColumnasTablaVenta;
import venta.TrazadorTablaVenta;
import venta.Venta;

public class PanelEditar extends JPanel implements ItemListener, ActionListener, Observer{
	
	JTable vTabla;
	JScrollPane panelScrollTabla;
	transient Toolkit toolkit;
	
	Clientela clientela;
	TrazadorTabla trazador;
	ModeloColumnasTabla columnas;	
	
	Maquinaria maquinaria;
	TrazadorTablaMaquina trazadorMaquina;
	ModeloColumnasTablaMaquina columnasMaquina;
	
	Inventario inventario;
	TrazadorTablaProducto trazadorProducto;
	ModeloColumnasTablaProducto columnasProducto;
	
	TiposLista listaTipoP;
	TrazadorTablaTipoProductos trazadorTipoP;
	ModeloColumnasTablaTipoProductos columnasTipoP;
	
	ListaOfertas listaOfertas;
	TrazadorTablaOferta trazadorOferta;
	ModeloColumnasTablaOferta columnasOferta;
	
	ListaVentas listaVentas;
	TrazadorTablaVenta trazadorVenta;
	ModeloColumnasTablaVenta columnasVenta;
	
	ListaStock listaStock;
	TrazadorTablaStock trazadorStock;
	ModeloColumnasTablaStock columnasStock;
	
	PanelTrabajador panelTrabajador;
	JFrame principal;
	transient MyDataAccess conexion;
	transient Comandos comandos;
	transient ModuloXBee xBee;
	
	JPanel panelOpciones;
	ButtonGroup opciones;
	JRadioButton rbCliente;
	JRadioButton rbMaquina;
	JRadioButton rbProducto;
	JRadioButton rbTipoP;
	JRadioButton rbOferta;
	JRadioButton rbVenta;
	JRadioButton rbStock;
	
	static final String IMAGEN_OK = "img/ok.png";
	static final String IMAGEN_ATRAS = "img/atras.png";
	static final String IMAGEN_BORRAR = "img/delete.png";
	private static final String IM_ERROR = "img/error.png";
	private static final String IM_COMPLETADO = "img/completado.png";
	
	
  /**
	 * Constructor of the class which initializes the needed parameters to display it
	 * @param v The JPanel from where this panel is accessible
	 * @param conexion Instance if the conection to the database
	 * @param principal Main class where different parameters are defined
   * @param xBee Instance of the XBee module
	 */
	public PanelEditar(JPanel v, MyDataAccess conexion, Principal principal, ModuloXBee xBee){
		super(new BorderLayout());
		this.principal = principal;
		toolkit = Toolkit.getDefaultToolkit();
		panelTrabajador = (PanelTrabajador) v;
		this.conexion = conexion;
		this.xBee = xBee;
		if(xBee != null) xBee.getRecibir().addObserver(this);
		comandos = new Comandos(conexion);
		panelScrollTabla = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(crearPanelOpciones(),BorderLayout.NORTH);
		this.add(crearPanelClientes(),BorderLayout.CENTER);
		this.add(crearPanelBotones(),BorderLayout.SOUTH);
	}

  /**
	 * Creates the panel for the OK, cancel and delete buttons
	 * @return The created panel
	 */
	private Component crearPanelBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0,(int)toolkit.getScreenSize().getWidth()/4,0,(int)toolkit.getScreenSize().getWidth()/4)); 
		panel.add(botonTransparente(IMAGEN_OK, "ok"), BorderLayout.WEST);
		panel.add(botonTransparente(IMAGEN_ATRAS, "cancel"), BorderLayout.EAST);
		panel.add(botonTransparente(IMAGEN_BORRAR, "delete"), BorderLayout.CENTER);
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
		
		rbCliente = crearRadioButton("Clientes");
		rbMaquina = crearRadioButton("Máquinas");
		rbProducto = crearRadioButton("Productos");
		rbTipoP = crearRadioButton("Tipo productos");
		rbOferta = crearRadioButton("Recargas");
		rbVenta = crearRadioButton("Ventas");
		rbStock = crearRadioButton("Stock");
		
		rbCliente.setSelected(true);
		
		opciones.add(rbCliente);
		opciones.add(rbMaquina);
		opciones.add(rbProducto);
		opciones.add(rbTipoP);
		opciones.add(rbOferta);
		opciones.add(rbVenta);
		opciones.add(rbStock);
		
		panelOpciones.add(rbCliente);
		panelOpciones.add(rbMaquina);
		panelOpciones.add(rbProducto);
		panelOpciones.add(rbTipoP);
		panelOpciones.add(rbOferta);
		panelOpciones.add(rbVenta);
		panelOpciones.add(rbStock);
		
		return panelOpciones;
	}
	
  /**
	 * Creates a radio button with the specified design
   * @param titulo Text that the radio button will display
	 * @return The created radio button
	 */
	public JRadioButton crearRadioButton(String titulo){
		JRadioButton boton = new JRadioButton(titulo);
		boton.setHorizontalAlignment(JLabel.LEFT);
		boton.setFont(new Font("Arial", Font.PLAIN, 30));
		boton.setBackground(Color.CYAN);
		boton.addItemListener(this);
		return boton;
	}

  /**
	 * Creates a table with the different clients' information
	 * @return The scroll pane to which this table has been asigned
	 */
	private Component crearPanelClientes() {
		trazador = new TrazadorTabla();
		columnas = new ModeloColumnasTabla (trazador);
		clientela = new Clientela(columnas, conexion);
		
		crearTabla(clientela, columnas);
	
		return panelScrollTabla;
	}
	
  /**
	 * Creates a table with the different clients' information
   * @param dm The tablo model to follow
   * @param cm The table column model to follow
	 */
	private void crearTabla(TableModel dm, TableColumnModel cm) {
		vTabla = new JTable(dm, cm);
		vTabla.setFillsViewportHeight(true);
		vTabla.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 25));
		panelScrollTabla.setViewportView(vTabla);
	}
	
  /**
	 * Creates a table with the different machines' information
	 * @return The scroll pane to which this table has been asigned
	 */
	private Component crearPanelMaquinas() {
		trazadorMaquina = new TrazadorTablaMaquina();
		columnasMaquina = new ModeloColumnasTablaMaquina (trazadorMaquina);
		maquinaria = new Maquinaria(columnasMaquina, conexion);
		
		crearTabla(maquinaria, columnasMaquina);
	
		return panelScrollTabla;
	}
	/**
	 * Creates a table with the different products' information
	 * @return The scroll pane to which this table has been asigned
	 */
	private Component crearPanelProductos() {
		trazadorProducto = new TrazadorTablaProducto();
		columnasProducto = new ModeloColumnasTablaProducto (trazadorProducto);
		inventario = new Inventario(columnasProducto, conexion);
		
		crearTabla(inventario, columnasProducto);
	
		return panelScrollTabla;
	}
	
  /**
	 * Creates a table with the different product types' information
	 * @return The scroll pane to which this table has been asigned
	 */
	private Component crearPanelTipoProductos() {
		trazadorTipoP = new TrazadorTablaTipoProductos();
		columnasTipoP = new ModeloColumnasTablaTipoProductos (trazadorTipoP);
		listaTipoP = new TiposLista(columnasTipoP, conexion);
		
		crearTabla(listaTipoP, columnasTipoP);
	
		return panelScrollTabla;
	}
	
  /**
	 * Creates a table with the different offers' information
	 * @return The scroll pane to which this table has been asigned
	 */
	private Component crearPanelOferta() {
		trazadorOferta = new TrazadorTablaOferta();
		columnasOferta = new ModeloColumnasTablaOferta (trazadorOferta);
		listaOfertas = new ListaOfertas(columnasOferta, conexion);
		
		crearTabla(listaOfertas, columnasOferta);
	
		return panelScrollTabla;
	}
	
  /**
	 * Creates a table with the different sales' information
	 * @return The scroll pane to which this table has been asigned
	 */
	private Component crearPanelVenta() {
		trazadorVenta = new TrazadorTablaVenta();
		columnasVenta = new ModeloColumnasTablaVenta (trazadorVenta);
		listaVentas = new ListaVentas(columnasVenta, conexion);
		
		crearTabla(listaVentas, columnasVenta);
	
		return panelScrollTabla;
	}
	
  /**
	 * Creates a table with the different stocks' information
	 * @return The scroll pane to which this table has been asigned
	 */
	private Component crearPanelStock() {
		trazadorStock = new TrazadorTablaStock();
		columnasStock = new ModeloColumnasTablaStock (trazadorStock);
		listaStock = new ListaStock(columnasStock, conexion);
		
		crearTabla(listaStock, columnasStock);
	
		return panelScrollTabla;
	}

  /**
	 * Overridden method to specify the action when a different radio button is selected 
	 * @param e The event which contains information about the radio buttons' status
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getStateChange()==ItemEvent.SELECTED){
			if ((JRadioButton)e.getSource()==rbCliente){
				this.add(crearPanelClientes(),BorderLayout.CENTER);
			}
			else if ((JRadioButton)e.getSource()==rbMaquina){
				this.add(crearPanelMaquinas(),BorderLayout.CENTER);
			}
			else if ((JRadioButton)e.getSource()==rbProducto){
				this.add(crearPanelProductos(),BorderLayout.CENTER);
			}
			else if ((JRadioButton)e.getSource()==rbTipoP){
				this.add(crearPanelTipoProductos(),BorderLayout.CENTER);
			}
			else if ((JRadioButton)e.getSource()==rbOferta){
				this.add(crearPanelOferta(),BorderLayout.CENTER);
			}
			else if ((JRadioButton)e.getSource()==rbVenta){
				this.add(crearPanelVenta(),BorderLayout.CENTER);
			}
			else if ((JRadioButton)e.getSource()==rbStock){
				this.add(crearPanelStock(),BorderLayout.CENTER);
			}
		}
		
	}

  /**
	 * Overridden method to specify the action when the buttons with an action listener are pressed 
	 * @param e The event which contains information about the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")){
			panelTrabajador.volver();
		}
		else if(e.getActionCommand().equals("ok")){
			checkSelectedRbOk();
		}
		else if(e.getActionCommand().equals("delete")){
			checkSelectedRbDelete();
		}
		actualizar();
	}
	
  private void checkSelectedRbDelete() {
		String nombreTabla = null;
		String primaryKey = null;
		int pos = vTabla.getSelectedRow();
		
		if(rbCliente.isSelected()){
			Cliente cliente = clientela.getCliente(pos);
			primaryKey = cliente.getPrimaryKey();
			nombreTabla = Principal.getTablacliente();
		}
		else if(rbMaquina.isSelected()){
			Maquina maquina = maquinaria.getMaquina(pos);
			primaryKey = maquina.getPrimaryKey();
			nombreTabla = Principal.getTablamaquina();
		}
		else if(rbProducto.isSelected()){
			Producto producto = inventario.getProducto(pos);
			primaryKey = producto.getPrimaryKey();
			nombreTabla = Principal.getTablaproducto();
		}
		else if(rbTipoP.isSelected()){
			TipoProducto tipoP = listaTipoP.getTipoP(pos);
			primaryKey = tipoP.getPrimaryKey();
			nombreTabla = Principal.getTablatipop();
		}
		else if(rbOferta.isSelected()){
			Oferta oferta = listaOfertas.getOferta(pos);
			primaryKey = oferta.getPrimaryKey();
			nombreTabla = Principal.getTablaoferta();
		}
		else if(rbVenta.isSelected()){
			Venta venta = listaVentas.getVenta(pos);
			primaryKey = venta.getPrimaryKey();
			nombreTabla = Principal.getTablaventa();
		}
		else if(rbStock.isSelected()){
			Stock stock = listaStock.getStock(pos);
			primaryKey = stock.getPrimaryKey();
			nombreTabla = Principal.getTablastock();
		}
		
		borrar(nombreTabla, primaryKey);
  }

private void checkSelectedRbOk() {
	    int pos = vTabla.getSelectedRow();
		if(rbCliente.isSelected()){
			Cliente cliente = clientela.getCliente(pos);
			new DialogoEdicion(principal, "Editar usuario", true, cliente, conexion, xBee);
		}
		else if(rbMaquina.isSelected()){
			Maquina maquina = maquinaria.getMaquina(pos);
			new DialogoEdicion(principal, "Editar maquina", true, maquina, conexion, xBee);
		}
		else if(rbProducto.isSelected()){
			Producto producto = inventario.getProducto(pos);
			new DialogoEdicion(principal, "Editar producto", true, producto, conexion, xBee);
		}
		else if(rbTipoP.isSelected()){
			TipoProducto tipoP = listaTipoP.getTipoP(pos);
			new DialogoEdicion(principal, "Editar tipo de producto", true, tipoP, conexion, xBee);
		}
		else if(rbOferta.isSelected()){
			Oferta oferta = listaOfertas.getOferta(pos);
			new DialogoEdicion(principal, "Editar reposición", true, oferta, conexion, xBee);
		}
		else if(rbVenta.isSelected()){
			Venta venta = listaVentas.getVenta(pos);
			new DialogoEdicion(principal, "Editar venta", true, venta, conexion, xBee);
		}
		else if(rbStock.isSelected()){
			Stock stock = listaStock.getStock(pos);
			new DialogoEdicion(principal, "Editar stock", true, stock, conexion, xBee);
		}
  }

/**
	 * Removes the entry with the specified primary key from the given table name
	 * @param nombreTabla Table name to search for
   * @param primaryKey Primary key to search for
	 */
	private void borrar(String nombreTabla, String primaryKey) {
		try {
			
			comandos.borrar(nombreTabla, primaryKey);
			
			JOptionPane.showMessageDialog(null, "Se ha eliminado el dato",
					"Dato eliminado",JOptionPane.INFORMATION_MESSAGE, new ImageIcon(IM_COMPLETADO));
			
			} catch (SQLException e1) {
				toolkit.beep();
				JOptionPane.showMessageDialog(null, e1,
						"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
			}
	}

  /**
	 * Recharges the panel assigned to the selected radio button
	 */
	public void actualizar(){
		if(rbCliente.isSelected()){
			this.add(crearPanelClientes(),BorderLayout.CENTER);
		}
		else if(rbMaquina.isSelected()){
			this.add(crearPanelMaquinas(),BorderLayout.CENTER);
		}
		else if(rbProducto.isSelected()){
			this.add(crearPanelProductos(),BorderLayout.CENTER);
		}
		else if(rbTipoP.isSelected()){
			this.add(crearPanelTipoProductos(),BorderLayout.CENTER);
		}
		else if(rbOferta.isSelected()){
			this.add(crearPanelOferta(),BorderLayout.CENTER);
		}
		else if(rbVenta.isSelected()){
			this.add(crearPanelVenta(),BorderLayout.CENTER);
		}
		else if(rbStock.isSelected()){
			this.add(crearPanelStock(),BorderLayout.CENTER);
		}
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
			if(rbVenta.isSelected() && recepcion.getNumActualizacion() == 2){
				this.add(crearPanelVenta(),BorderLayout.CENTER);
			}
		}
	}
}
