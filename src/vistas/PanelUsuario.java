package vistas;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Dialogos.DialogoInfoUsuario;
import clientes.Cliente;
import clientes.Clientela;
import clientes.Mapeador;
import clientes.ModeloColumnasTabla;
import clientes.TrazadorTabla;
import conexionSQL.MyDataAccess;

public class PanelUsuario extends JPanel implements ListSelectionListener{
	
	AbstractAction accAgregarID, accAgregarApellido,accAgregarEdad,accAgregarCP;
	
	JList<String> listaAgregados;
	
	JTable vTabla;
	Clientela clientela;
	TrazadorTabla trazador;
	ModeloColumnasTabla columnas;	
	JScrollPane panelScrollTabla;
	JScrollPane panelOpciones;
	JScrollPane panelCentral;
	Principal principal;
	MyDataAccess conexion;
	
	static final String IMAGEN_ID = "img/ID.png";
	static final String IMAGEN_APELLIDO = "img/Apellido.png";
	static final String IMAGEN_CP = "img/CP.png";
	static final String IMAGEN_EDAD = "img/edad.png";
	static final String IMAGEN_ATRAS = "img/back.png";
	static final String IMAGEN_INFO = "img/info.png";

	public PanelUsuario(JFrame v, MyDataAccess conexion){
		
		super(new BorderLayout());
		principal = (Principal) v;
		this.conexion = conexion;
		trazador = new TrazadorTabla();
		columnas = new ModeloColumnasTabla (trazador);
		this.crearAcciones();
		clientela = new Clientela(columnas, conexion);
		clientela.agrupar(new Mapeador<Cliente>(){
			@Override
			public String map(Cliente cliente) {
				return String.valueOf(cliente.getId());
			}
		});
		panelScrollTabla = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelOpciones = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelCentral = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(crearToolBar(),BorderLayout.NORTH);
		this.add(crearPanelCentral(),BorderLayout.CENTER);
	}
	
	public void actualizar(){
		clientela.actualizar();
		this.add(crearPanelCentral(),BorderLayout.CENTER);
	}
	
	private JToolBar crearToolBar() {
		JToolBar toolBar = new JToolBar();
		JButton boton;
		toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
		
	    toolBar.add(new JButton (accAgregarID));
		
		toolBar.add(new JButton (accAgregarApellido));
		
		toolBar.add(new JButton (accAgregarEdad));
		toolBar.add(new JButton (accAgregarCP));
		toolBar.add(Box.createHorizontalGlue());

		boton =(JButton) toolBar.add(new JButton (new ImageIcon(IMAGEN_INFO)));
		boton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e){
				int pos = vTabla.getSelectedRow();
				Cliente cliente = clientela.getCliente(pos);				
				DialogoInfoUsuario dialogo = new DialogoInfoUsuario(principal, "Informacion usuario", true, conexion, cliente);
			}
		});
		
		toolBar.addSeparator();
		
		boton =(JButton) toolBar.add(new JButton (new ImageIcon(IMAGEN_ATRAS)));
		boton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e){
				principal.volver();
			}
		});
		return toolBar;
	}
	
	private Component crearPanelCentral() {
		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		panel.setLeftComponent(crearPanelOpciones());
		panel.setRightComponent(crearPanelClientes());
		panelCentral.setViewportView(panel);
		return panelCentral;
	}
	
	private Component crearPanelOpciones() {
		listaAgregados = new JList<>(clientela.getListaOpciones());
		listaAgregados.setSelectedIndex(0);
		listaAgregados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaAgregados.addListSelectionListener(this);
		
		panelOpciones.setViewportView(listaAgregados);
		return panelOpciones;
		
	}
	
	private Component crearPanelClientes() {
		clientela.volverACargar();
		clientela.getListaClientesOpcion(listaAgregados.getSelectedValue());
		crearTabla();
	
		return panelScrollTabla;
	}
	
	private void crearTabla() {
		vTabla = new JTable(clientela,columnas);
		vTabla.setFillsViewportHeight(true);
		vTabla.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 25));
		panelScrollTabla.setViewportView(vTabla);
	}

	private void crearAcciones() {
		accAgregarID = new AccionAgregarID ("ID",new ImageIcon(IMAGEN_ID),"Agregar por ID",KeyEvent.VK_I);
		accAgregarApellido= new AccionAgregarApellido ("Apellido",new ImageIcon(IMAGEN_APELLIDO),"Agregar por apellido",KeyEvent.VK_N);
		accAgregarEdad = new AccionAgregarEdad ("Edad",new ImageIcon(IMAGEN_EDAD),"Agregar por edad",KeyEvent.VK_E);
		accAgregarCP= new AccionAgregarCP ("CP", new ImageIcon(IMAGEN_CP),"Agregar por código postal", KeyEvent.VK_C);
	}
	
	public class AccionAgregarID extends AbstractAction{
		String texto;
		public AccionAgregarID (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			clientela.volverACargar();
			clientela.agrupar(new Mapeador<Cliente>(){

				@Override
				public String map(Cliente cliente) {
					return String.valueOf(cliente.getId());
				}
				
			});
			listaAgregados.setListData(clientela.getListaOpciones());
			listaAgregados.setSelectedIndex(0);
		}
		
	}
	
	public class AccionAgregarApellido extends AbstractAction{
		String texto;
		public AccionAgregarApellido (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			clientela.volverACargar();
			clientela.agrupar(new Mapeador<Cliente>(){

				@Override
				public String map(Cliente cliente) {
					return cliente.getApellido();
				}
				
			});
			listaAgregados.setListData(clientela.getListaOpciones());
			listaAgregados.setSelectedIndex(0);
		}
		
	}
	
	public class AccionAgregarEdad extends AbstractAction{
		String texto;
		public AccionAgregarEdad (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			clientela.volverACargar();
			clientela.agrupar(new Mapeador<Cliente>(){
				@Override
				public String map(Cliente cliente) {
					String edad = cliente.getFechaNacimiento();
					String [] fechas = edad.split("[-]");
					return fechas[0];
				}
			});
			listaAgregados.setListData(clientela.getListaOpciones());
			listaAgregados.setSelectedIndex(0);
			
		}
		
	}
	
	public class AccionAgregarCP extends AbstractAction{
		String texto;
		public AccionAgregarCP (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			clientela.volverACargar();
			clientela.agrupar(new Mapeador<Cliente>(){

				@Override
				public String map(Cliente cliente) {
					return String.valueOf(cliente.getCodigoPostal());
				}
				
			});
			listaAgregados.setListData(clientela.getListaOpciones());
			listaAgregados.setSelectedIndex(0);
			}
		}

	@Override
	public void valueChanged(ListSelectionEvent evento) {
		if (evento.getValueIsAdjusting()) return;
		if (listaAgregados.getSelectedIndex()!=-1){
			clientela.volverACargar();
			clientela.getListaClientesOpcion(listaAgregados.getSelectedValue());
			crearTabla();
		}
	}

}
