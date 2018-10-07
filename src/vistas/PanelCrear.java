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
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import Dialogos.DialogoEdicion;
import Productos.Inventario;
import Productos.ModeloColumnasTablaProducto;
import Productos.Producto;
import Productos.TrazadorTablaProducto;
import clientes.Cliente;
import clientes.Clientela;
import clientes.ModeloColumnasTabla;
import clientes.TrazadorTabla;
import comunicacion.ModuloXBee;
import comunicacion.Recepcion;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import maquinas.Maquina;
import maquinas.Maquinaria;
import maquinas.ModeloColumnasTablaMaquina;
import maquinas.TrazadorTablaMaquina;
import tipoProductos.ModeloColumnasTablaTipoProductos;
import tipoProductos.TipoProducto;
import tipoProductos.TiposLista;
import tipoProductos.TrazadorTablaTipoProductos;

public class PanelCrear extends JPanel implements ItemListener, ActionListener, Observer{
	JTable vTabla;
	JScrollPane panelScrollCampos;
	Toolkit toolkit;

	
	PanelTrabajador panelTrabajador;
	JFrame principal;
	MyDataAccess conexion;
	ModuloXBee xBee;
	
	JPanel panelOpciones;
	ButtonGroup opciones;
	JRadioButton rbCliente, rbMaquina, rbProducto, rbTipoP;
	boolean selected = false;
	
	JTextField[] datosTextField;
	JLabel[] datosLabel;
	String[] datosItem;
	
	static final String IMAGEN_OK = "img/ok.png";
	static final String IMAGEN_ATRAS = "img/atras.png";
	private static final String IM_ERROR = "img/error.png";
	private static final String IM_COMPLETADO = "img/completado.png";
	
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

	private Component crearCamposClientes() {		
		datosItem = Cliente.getOpcionescliente();
		datosLabel = new JLabel[datosItem.length];
		datosTextField = new JTextField[datosItem.length];
		
		crearCampos();
		
		return panelScrollCampos;
	}
	
	private Component crearCamposMaquinas() {		
		datosItem = Maquina.getOpcionesmaquina();
		datosLabel = new JLabel[datosItem.length];
		datosTextField = new JTextField[datosItem.length];
		
		crearCampos();
		
		return panelScrollCampos;
	}
	
	private Component crearCamposProductos() {		
		datosItem = Producto.getOpcionesproducto();
		datosLabel = new JLabel[datosItem.length];
		datosTextField = new JTextField[datosItem.length];
		
		crearCampos();
		
		return panelScrollCampos;
	}
	
	private Component crearCamposTipoProductos() {		
		datosItem = TipoProducto.getOpcionestipop();
		datosLabel = new JLabel[datosItem.length];
		datosTextField = new JTextField[datosItem.length];
		
		crearCampos();
		
		return panelScrollCampos;
	}

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

	private JPanel crearPanelCampo(String tituloLabel, int i) {
		JPanel panel = new JPanel(new GridLayout(1,2,20,25));
		datosLabel[i]= new JLabel(tituloLabel);
		datosTextField[i] = new JTextField (20);
		datosTextField[i].setBorder(BorderFactory.createLoweredBevelBorder());
		panel.add(datosLabel[i]);
		panel.add(datosTextField[i]);
		return panel;
	}
	
	private Component crearPanelBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0,(int)toolkit.getScreenSize().getWidth()/4,0,(int)toolkit.getScreenSize().getWidth()/4)); 
		panel.add(botonTransparente(IMAGEN_OK, "ok"), BorderLayout.WEST);
		panel.add(botonTransparente(IMAGEN_ATRAS, "cancel"), BorderLayout.EAST);
		panel.setBackground(Color.WHITE);
		return panel;
	}
	
	private Component botonTransparente(String i, String c){
		JButton b = new JButton(new ImageIcon(i));
		b.setActionCommand(c);
		b.addActionListener(this);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		return b;
	}

	private Component crearPanelOpciones() {
		panelOpciones = new JPanel (new GridLayout(1,2,60,60));
		panelOpciones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panelOpciones.setBackground(Color.CYAN);
		opciones = new ButtonGroup();	
		
		rbCliente = new JRadioButton("Clientes");
		rbCliente.setHorizontalAlignment(JLabel.RIGHT);
		rbCliente.setFont(new Font("Arial", Font.PLAIN, 30));
		rbCliente.setBackground(Color.CYAN);
		rbCliente.addItemListener(this);
		
		rbMaquina = new JRadioButton("Máquinas");
		rbMaquina.setHorizontalAlignment(JLabel.CENTER);
		rbMaquina.setFont(new Font("Arial", Font.PLAIN, 30));
		rbMaquina.setBackground(Color.CYAN);
		rbMaquina.addItemListener(this);
		
		rbProducto = new JRadioButton("Productos");
		rbProducto.setHorizontalAlignment(JLabel.LEFT);
		rbProducto.setFont(new Font("Arial", Font.PLAIN, 30));
		rbProducto.setBackground(Color.CYAN);
		rbProducto.addItemListener(this);
		
		rbTipoP = new JRadioButton("Tipo productos");
		rbTipoP.setHorizontalAlignment(JLabel.LEFT);
		rbTipoP.setFont(new Font("Arial", Font.PLAIN, 30));
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getStateChange()==ItemEvent.SELECTED){
			
			if ((JRadioButton)e.getSource()==rbCliente){
				this.add(crearCamposClientes(),BorderLayout.CENTER);
				if(xBee != null) xBee.mandarTramaDatosNuevoUsuario();
				System.out.println("mandando nuevo usuario");
				selected = true;
			}
			else{
				if(selected){
					if(xBee != null) xBee.mandarTramaDatosNuevoUsuarioCancelado();
					System.out.println("mandando nuuevo usuario cancelado");
					selected = false;
				}
				
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
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")){
			if(rbCliente.isSelected() && xBee != null) xBee.mandarTramaDatosNuevoUsuarioCancelado();
			System.out.println("mandando nuuevo usuario cancelado");
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

	private void limpiarTextField() {
		for(int i = 0; i < datosTextField.length; i++){
			datosTextField[i].setText("");
		}
	}

	private void crearTipoP(Comandos comandos, String[] datos) throws SQLException {
		boolean[] formatoColumnas = null;
		formatoColumnas = TipoProducto.getFormatoColumnas();
		
		comandos.insertar(formatoColumnas, datos, Principal.getTablatipop());
	}

	private void crearProducto(Comandos comandos, String[] datos) throws SQLException {
		boolean[] formatoColumnas = null;
		formatoColumnas = Producto.getFormatoColumnas();
		
		comandos.insertar(formatoColumnas, datos, Principal.getTablaproducto());
	}

	private void crearMaquina(Comandos comandos, String[] datos) throws SQLException {
		boolean[] formatoColumnas = null;
		formatoColumnas = Maquina.getFormatoColumnas();
		
		comandos.insertar(formatoColumnas, datos, Principal.getTablamaquina());
	}

	private void crearCliente(Comandos comandos, String[] datos) throws SQLException {
		boolean[] formatoColumnas = null;
		formatoColumnas = Cliente.getFormatoColumnas();
		
		comandos.insertar(formatoColumnas, datos, Principal.getTablacliente());
	}
	
	private String[] cargarDatosTF() {
		String[] textos = new String[datosTextField.length];
		
		for(int i = 0; i < datosTextField.length; i++){
			textos[i] = datosTextField[i].getText();
		}
		
		return textos;
	}

	@Override
	public void update(Observable o, Object obj) {
		if (obj instanceof Recepcion) {
			Recepcion recepcion = (Recepcion) obj;
			if(recepcion.getNumActualizacion() == 1) datosTextField[0].setText(recepcion.getUsuarioID());
		}
	}
}
