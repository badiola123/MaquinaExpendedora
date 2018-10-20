/**
 * @file PanelUsuario.java
 * @author Edgar Azpiazu
 * @brief This file creates the panel to recharge different products into machines
 */

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import clientes.Cliente;
import clientes.Clientela;
import clientes.Mapeador;
import clientes.ModeloColumnasTabla;
import clientes.TrazadorTabla;
import conexion_sql.MyDataAccess;
import dialogos.DialogoInfoUsuario;

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

  /**
	 * Constructor of the class which initializes the needed parameters to display it
	 * @param v The JPanel from where this panel is accessible
	 * @param conexion Instance if the conection to the database
	 */
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
	
  /**
	 * Updates the client list from the database and the displayed panel
	 */
	public void actualizar(){
		clientela.actualizar();
		this.add(crearPanelCentral(),BorderLayout.CENTER);
	}
	
  /**
	 * Creates a toolbar with different buttons to sort clients and another one for additional information of them
	 * @return The created tool bar
	 */
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
	
  /**
	 * Creates a splitted panel where users can be sorted and filtered following different criteria
	 * @return The created panel
	 */
	private Component crearPanelCentral() {
		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		panel.setLeftComponent(crearPanelOpciones());
		panel.setRightComponent(crearPanelClientes());
		panelCentral.setViewportView(panel);
		return panelCentral;
	}
	
  /**
	 * Creates the left component of the splitted panel with a list of the different values to filter clients
	 * @return The created panel
	 */
	private Component crearPanelOpciones() {
		listaAgregados = new JList<>(clientela.getListaOpciones());
		listaAgregados.setSelectedIndex(0);
		listaAgregados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaAgregados.addListSelectionListener(this);
		
		panelOpciones.setViewportView(listaAgregados);
		return panelOpciones;
		
	}
	
  /**
	 * Creates the right component of the splitted panel with a list of sorted clients that pass the applied filter
	 * @return An scroll bar to which a panel has been asignated
	 */
	private Component crearPanelClientes() {
		clientela.volverACargar();
		clientela.getListaClientesOpcion(listaAgregados.getSelectedValue());
		crearTabla();
	
		return panelScrollTabla;
	}
	
  /**
	 * Creates a table with the different clients' information
   * @param dm The tablo model to follow
   * @param cm The table column model to follow
	 */
	private void crearTabla() {
		vTabla = new JTable(clientela,columnas);
		vTabla.setFillsViewportHeight(true);
		vTabla.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 25));
		panelScrollTabla.setViewportView(vTabla);
	}

  /**
	 * Creates the different sorting methods
	 */
	private void crearAcciones() {
		accAgregarID = new AccionAgregarID ("ID",new ImageIcon(IMAGEN_ID),"Agregar por ID",KeyEvent.VK_I);
		accAgregarApellido= new AccionAgregarApellido ("Apellido",new ImageIcon(IMAGEN_APELLIDO),"Agregar por apellido",KeyEvent.VK_N);
		accAgregarEdad = new AccionAgregarEdad ("Edad",new ImageIcon(IMAGEN_EDAD),"Agregar por edad",KeyEvent.VK_E);
		accAgregarCP= new AccionAgregarCP ("CP", new ImageIcon(IMAGEN_CP),"Agregar por código postal", KeyEvent.VK_C);
	}
	
	public class AccionAgregarID extends AbstractAction{
		String texto;
    
    /**
	   * Creates the sorting method by ID
     * @param texto The text that describes the sorting method
     * @param image The image that is displayed inside the button
     * @param descrip A short descripton of the method
     * @param nemonic Shortcut key
	   */
		public AccionAgregarID (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}
    
    /**
	   * Overridden method to specify the action when the buttons with an action listener are pressed 
	   * @param e The event which contains information about the action
	   */
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
    
    /**
	   * Creates the sorting method by surname
     * @param texto The text that describes the sorting method
     * @param imagen The image that is displayed inside the button
     * @param descrip A short descripton of the method
     * @param nemonic Shortcut key
	   */
		public AccionAgregarApellido (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}
    
    /**
	   * Overridden method to specify the action when the buttons with an action listener are pressed 
	   * @param e The event which contains information about the action
	   */
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
    
    /**
	   * Creates the sorting method by age
     * @param texto The text that describes the sorting method
     * @param image The image that is displayed inside the button
     * @param descrip A short descripton of the method
     * @param nemonic Shortcut key
	   */
		public AccionAgregarEdad (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}
    
    /**
	   * Overridden method to specify the action when the buttons with an action listener are pressed 
	   * @param e The event which contains information about the action
	   */
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
    
    /**
	   * Creates the sorting method by postal code
     * @param texto The text that describes the sorting method
     * @param imagen The image that is displayed inside the button
     * @param descrip A short descripton of the method
     * @param nemonic Shortcut key
	   */
		public AccionAgregarCP (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			this.texto = texto;
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
		}
    
    /**
	   * Overridden method to specify the action when the buttons with an action listener are pressed 
	   * @param e The event which contains information about the action
	   */
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

  /**
	 * Overridden method called whenever the value of the selection changes 
	 * @param evento The event that characterizes the change
	 */
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
