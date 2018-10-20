/**
 * @file PanelEstadisticas.java
 * @author Edgar Azpiazu
 * @brief This file creates the panel to show different statistics about the best sellers from different machines
 */

package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import maquinas.Maquina;
import maquinas.Maquinaria;
import productos.Inventario;
import productos.Producto;

public class PanelEstadisticas extends JPanel implements ListSelectionListener{
	
	JList<Maquina> listaMaquinas;
	DefaultListModel<Maquina> modelo;
	
	JTable vTabla;
	JScrollPane panelScrollDatos;
	JScrollPane panelOpciones;
	JScrollPane panelCentral;
	Principal principal;
	MyDataAccess conexion;
	Comandos comandos;
	
	JLabel labelProducto[];	

	static final String IMAGEN_ATRAS = "img/back.png";
	private static final String IM_ERROR = "img/error.png";

  /**
	 * Constructor of the class which initializes the needed parameters to display it
	 * @param v The JPanel from where this panel is accessible
	 * @param conexion Instance if the conection to the database
	 */
	public PanelEstadisticas(JFrame v, MyDataAccess conexion){
		
		super(new BorderLayout());
		principal = (Principal) v;
		this.conexion = conexion;
		comandos = new Comandos(conexion);
		panelScrollDatos = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelOpciones = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelCentral = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		labelProducto = new JLabel[3];
		crearLabels();
		this.add(crearToolBar(),BorderLayout.NORTH);
		this.add(crearPanelCentral(),BorderLayout.CENTER);
	}
	
  /**
	 * Creates and initializes the labels for the top three product list
	 */
	private void crearLabels() {
		for(int i = 0; i < labelProducto.length; i++){
			labelProducto[i] = new JLabel("No se ha podido encontrar el producto");
		}
	}

  /**
	 * Updates the list's labels and the displayed panel
	 */
	public void actualizar(){
		crearLabels();
		this.add(crearPanelCentral(),BorderLayout.CENTER);
	}

  /**
	 * Creates a toolbar with a back button and asigns it an action for when it is pressed
	 * @return The created tool bar
	 */
	private JToolBar crearToolBar() {
		JToolBar toolBar = new JToolBar();
		JButton boton;
		toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
		toolBar.add(Box.createHorizontalGlue());
		
		boton =(JButton) toolBar.add(new JButton (new ImageIcon(IMAGEN_ATRAS)));
		boton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e){
				principal.volver();
			}
		});
		return toolBar;
	}
	
  /**
	 * Creates a splitted panel where different machines can be selected to see their product statistics
	 * @return The created panel
	 */
	private Component crearPanelCentral() {
		JSplitPane panelSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		panelSplit.setLeftComponent(crearPanelMaquinas());
		panelSplit.setRightComponent(crearPanelProductos());
		panelCentral.setViewportView(panelSplit);
		return panelCentral;
	}
	
  /**
	 * Creates the left component of the splitted panel with a list of the different machines
	 * @return The created panel
	 */
	private Component crearPanelMaquinas() {
		listaMaquinas = new JList<>();
		listaMaquinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaMaquinas.addListSelectionListener(this);
		modelo = new DefaultListModel<>();
		listaMaquinas.setModel(modelo);
		List<Maquina> maquinas = Maquinaria.cargarDatos(conexion);
		for(int i = 0; i < maquinas.size(); i++){
			modelo.addElement(maquinas.get(i));
		}
		panelOpciones.setViewportView(listaMaquinas);
		return panelOpciones;
	}
	
  /**
	 * Creates the right component of the splitted panel with a ranking of the three best seller products
	 * @return The created panel
	 */
	private Component crearPanelProductos() {
		JPanel panel = new JPanel (new GridLayout(3, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearLabel(labelProducto[0], "Producto más vendido", Color.YELLOW));
		panel.add(crearLabel(labelProducto[1], "Segundo producto más vendido", Color.BLUE));
		panel.add(crearLabel(labelProducto[2], "Tercer producto más vendido", Color.RED));
		panel.setBackground(Color.WHITE);
		cargarProductos();
		panelScrollDatos.setViewportView(panel);
		return panelScrollDatos;
	}
  
  /**
	 * Charges the three most sold products from the database
	 */
	private void cargarProductos() {
		String[] datos = new String[Producto.getNombreColumnas().length + 1]; // Para seleccionar productos + cantidad vendida
		String[] cantidades = new String[Maquina.getNumhusillosmaquina()];
		Producto producto;
		String columnas[] = Producto.getNombreColumnas();
		String columnasConPrefijo[] = new String[columnas.length+1];
		for(int i = 0; i < columnas.length; i++){
			columnasConPrefijo[i] = columnas[i];
		}
		columnasConPrefijo[columnas.length] = "count(*)";
		columnasConPrefijo[0] = Principal.getTablaproducto() + "." + columnasConPrefijo[0];
		
		//String seleccion = "venta v JOIN producto p ON v.producto_id = p.producto_id";
		String seleccion = Principal.getTablaventa() + " JOIN " + Principal.getTablaproducto() 
						   + " ON " + Principal.getTablaventa() + "." + Producto.getNombreColumnas()[0] 
						   + " = " + Principal.getTablaproducto() + "." + Producto.getNombreColumnas()[0];
				
		//String agrupacion = "p.producto_id";
		String agrupacion = Principal.getTablaproducto() + "." + Producto.getNombreColumnas()[0];
		
		String orden = "count(*)";
		boolean sentidoOrden = false;
		int limitar = Maquina.getNumhusillosmaquina();
		Maquina maquina = listaMaquinas.getSelectedValue();
		
		if(maquina == null) return;
		try {
			int numProducto = 0;
			ResultSet resultado = comandos.select(columnasConPrefijo, seleccion, maquina.getPrimaryKey(), agrupacion, orden, sentidoOrden, limitar);
        	while(resultado.next()){
    			for(int i = 1; i < (Producto.getNombreColumnas().length + 1) + 1; i++){ // .lenght + 1 porque se empieza a contar desde 1 y el otro + 1 para que lea la cantidad vendida
            		datos[i-1] = resultado.getString(i);
            		System.out.println(datos[i-1]);
            	}
    			cantidades[numProducto] = datos[datos.length-1];
    			
    			producto = new Producto(Integer.valueOf(datos[0]), datos[1], Double.valueOf(datos[2]), Integer.valueOf(datos[3]));
		        if(producto != null){
		        	labelProducto[numProducto].setText("<html>" + producto.toString() + "<br>Cantidad vendida: " + cantidades[numProducto] + "</html>");
		        }
		        numProducto++;
        	}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e,
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
	}

  /**
	 * Creates the panel where a field for the new input is added
   * @param label Label containing information about the product
	 * @param titulo Title for the product label
   * @param color Color for the border that includes the title
	 * @return The created panel holding the label
	 */
	private JPanel crearLabel(JLabel label, String titulo, Color color) {
		JPanel panel = new JPanel(new GridLayout(1,1));
		label.setFont(new Font("Arial", Font.PLAIN, 25));
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(color), titulo));
		
		panel.add(label);
		panel.setBackground(Color.WHITE);
		return panel;
	}

  /**
	 * Overridden method called whenever the value of the selection changes
   * @param evento The event that characterizes the change
	 */
	@Override
	public void valueChanged(ListSelectionEvent evento) {
		if (evento.getValueIsAdjusting()) return;
		if (listaMaquinas.getSelectedIndex()!=-1){
			for(int i = 0; i < labelProducto.length; i++){
				labelProducto[i].setText("No se ha podido encontrar el producto");
			}
			cargarProductos();
		}
	}
}
