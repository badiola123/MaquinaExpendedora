/**
 * @file PanelStock.java
 * @author Edgar Azpiazu
 * @brief This file creates the panel to create stocks of products in the machines
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import adaptadores.AdaptadorListaProductos;
import adaptadores.ComboRenderer;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import excepciones.OutOfStockException;
import maquinas.Maquina;
import maquinas.Maquinaria;
import productos.Inventario;
import productos.Producto;
import stock.Stock;

public class PanelStock extends JPanel implements ActionListener{
	
	Principal principal;
	MyDataAccess conexion;
	JScrollPane panelScroll;
	JPanel panelOpciones;
	Toolkit toolkit;
	Comandos comandos;
	
	JList<Producto> inventario;
	DefaultListModel<Producto> modelo;
	
	JLabel labelFecha, labelHora, labelPos;
	JTextField textFecha, textHora;
	
	JPanel pCombo;
	JComboBox<Maquina> cMaquinas;
	JComboBox<String> cPos;
	
	int ano, mes, dia, horas, mins, segs;
	
	static final String IMAGEN_OK = "img/ok.png";
	static final String IMAGEN_ATRAS = "img/atras.png";
	private static final String IM_ERROR = "img/error.png";
	private static final String IM_COMPLETADO = "img/completado.png";
	private final static Logger LOGGER = Logger.getLogger(PanelStock.class.getName());
	
  /**
	 * Constructor of the class which initializes the needed parameters to display it
	 * @param v The JPanel from where this panel is accessible
	 * @param conexion Instance if the conection to the database
	 */
	public PanelStock(JFrame v, MyDataAccess conexion){
		super(new BorderLayout(0, 15));
		this.principal = (Principal) v;
		this.conexion = conexion;
		comandos = new Comandos(conexion);
		toolkit = Toolkit.getDefaultToolkit();
		labelFecha = new JLabel("Fecha(año-mes-dia):", SwingConstants.RIGHT);
		textFecha = new JTextField (20);
		labelHora = new JLabel("Hora(hh:mm):", SwingConstants.RIGHT);
		textHora = new JTextField (20);
		labelPos = new JLabel("Husillo:", SwingConstants.RIGHT);
		panelScroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelOpciones = new JPanel(new BorderLayout());
		pCombo = new JPanel();
		this.add(crearPanelMaquinas(),BorderLayout.NORTH);
		this.add(crearPanelProductos(),BorderLayout.CENTER);
		this.add(crearPanelOpciones(),BorderLayout.SOUTH);
	}

  /**
	 * Updates the different elements displayed
	 */
	public void actualizar(){
		this.add(crearPanelMaquinas(),BorderLayout.NORTH);
		this.add(crearPanelProductos(),BorderLayout.CENTER);
		this.add(crearPanelOpciones(),BorderLayout.SOUTH);
		textFecha.setText("");
		textHora.setText("");
	}
	
  /**
	 * Creates a panel with different options to create a stock
	 * @return The created panel
	 */
	private Component crearPanelOpciones() {
		panelOpciones.removeAll();
		panelOpciones.add(crearPanelDatos(), BorderLayout.CENTER);
		panelOpciones.add(crearPanelBotones(), BorderLayout.SOUTH);
		
		return panelOpciones;
	}
	
  /**
	 * Creates a panel with the fields that have to be filled to fulfill the stock creation
	 * @return The created panel
	 */
	private Component crearPanelDatos() {
		JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,40));
		panel.add(crearPanelCampo(labelFecha, textFecha));
		panel.add(crearPanelCampo(labelHora, textHora));
		panel.add(crearPanelPos(labelPos));
		return panel;
	}

  /**
	 * Creates a panel with a combo box representing the available spindles to create the stock
	 * @return The created panel
	 */
	private Component crearPanelPos(JLabel label) {
		JPanel pComboPos = new JPanel(new GridLayout(1,2,20,25));
		pComboPos.setBackground(Color.WHITE);
		cPos = new JComboBox<>(cargarPos());
		cPos.setFont(new Font("Arial", Font.BOLD, 15));
		cPos.setRenderer(new ComboRenderer());
		label.setFont(new Font("Arial", Font.BOLD, 20));
		pComboPos.add(label);
		pComboPos.add(cPos);
		cPos.setBackground(Color.WHITE);
		return pComboPos;		
	}

  /**
	 * Charges the free positions of a machine from the database
	 * @return An array of Strings with the free positions
	 */
	private String[] cargarPos() {
		String primaryKey = Maquina.getNombreColumnas()[0] + " = " + ((Maquina) cMaquinas.getSelectedItem()).getId();
		List<String> listaPos = new ArrayList<>();
		String[] columnas = new String[1];
		columnas[0] = Stock.getNombreColumnas()[4];
		try {
			ResultSet resultado = comandos.select(columnas, Principal.getTablastock(), primaryKey, null, null, false, 0);
			while(resultado.next()){
				listaPos.add(resultado.getString(1));
			}
		} catch (SQLException e) {
			toolkit.beep();
			JOptionPane.showMessageDialog(null, "Error al cargar datos",
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
		}
		
		String[] posLibre = new String[3-listaPos.size()];
		int posCargada = 0;
		for(int i = 1; i < (Maquina.getNumhusillosmaquina() + 1); i++){
			if(comprobarPosLibre(i, listaPos)){
				posLibre[posCargada] = String.valueOf(i);
				posCargada++;
			}
		}
		return posLibre;		
	}

  /**
	 * Checkes wheter a spindle position from a machine is free
   * @param num The spindle number that is going to be checked
   * @param listaPos A list with the occupied spindle positions
	 * @return True if the checked position is free, false if not
	 */
	private boolean comprobarPosLibre(int num, List<String> listaPos) {
		
		for(int i = 0; i < listaPos.size(); i++){
			if(Integer.valueOf(listaPos.get(i)) == num) return false;
		}
		return true;
	}

  /**
	 * Includes a label inside a panel with the desired style as well as a text field
   * @param label Label to give style
   * @param text Text field to write down the information specified in the label
	 * @return The created panel
	 */
	private JPanel crearPanelCampo(JLabel label, JTextField text) {
		JPanel panel = new JPanel(new GridLayout(1,2,20,25));
		panel.setBackground(Color.WHITE);
		text.setBorder(BorderFactory.createLoweredBevelBorder());
		label.setFont(new Font("Arial", Font.BOLD, 22));
		panel.add(label);
		panel.add(text);
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
	 * Creates a panel with a list of the available products to create a stock
	 * @return The created panel
	 */
	private Component crearPanelProductos() {
		inventario = new JList<>();
		inventario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		modelo = new DefaultListModel<>();
		inventario.setModel(modelo);
		AdaptadorListaProductos adaptador = new AdaptadorListaProductos();
		inventario.setCellRenderer(adaptador);
		List<Producto> listaProductos = Inventario.cargarDatos(conexion, -1);
		for(int i = 0; i < listaProductos.size(); i++){
			modelo.addElement(listaProductos.get(i));
		}
		panelScroll.setViewportView(inventario);
		return panelScroll;
	}

  /**
	 * Creates a panel with a list of the available machines to create a stock in
	 * @return The created panel
	 */
	private Component crearPanelMaquinas() {		
		cMaquinas = new JComboBox<>(cargarMaquinas());
		cMaquinas.setFont(new Font("Arial", Font.BOLD, 25));
		cMaquinas.setRenderer(new ComboRenderer());
		cMaquinas.setActionCommand("cambio");
		cMaquinas.addActionListener(this);
		pCombo.removeAll();
		pCombo.add(cMaquinas);
		cMaquinas.setBackground(Color.WHITE);
		return pCombo;
	}

  /**
	 * Loads the different machines from the database
	 * @return An array with the different machines
	 */
	private Maquina[] cargarMaquinas() {
		List<Maquina> listaMaquinas = Maquinaria.cargarDatos(conexion);
		Maquina[] maquinaria = new Maquina [listaMaquinas.size()];
		for(int i = 0; i < listaMaquinas.size(); i++){
			maquinaria[i] = listaMaquinas.get(i);
		}
		return maquinaria;
	}

  /**
	 * Overridden method to specify the action when the buttons with an action listener are pressed 
	 * @param e The event which contains information about the action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")){
			principal.volver();
		}
		else if(e.getActionCommand().equals("ok")){

			if(comprobarFecha()){
				try {
					
					crearStock();
					
					JOptionPane.showMessageDialog(null, "Se ha insertado el dato",
							"Dato insertado",JOptionPane.INFORMATION_MESSAGE, new ImageIcon(IM_COMPLETADO));
					actualizarPos();
					
					} catch (SQLException e1) {
						try {
							conexion.setQuery("rollback");
						} catch (SQLException e2) {
							LOGGER.log(Level.ALL, e2.getMessage());
						}
						toolkit.beep();
						JOptionPane.showMessageDialog(null, e1,
								"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
					}catch(ArrayIndexOutOfBoundsException e1){
						toolkit.beep();
						JOptionPane.showMessageDialog(null, "Selecciona todos los campos",
								"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
					}catch(NumberFormatException e1){
						toolkit.beep();
						JOptionPane.showMessageDialog(null, "Datos incorrectos",
								"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
					} catch (OutOfStockException e1) {
						toolkit.beep();
						JOptionPane.showMessageDialog(null, "No hay stock de este producto",
								"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
					}
				}
			}
		else if(e.getActionCommand().equals("cambio")){
			actualizarPos();
		}
	}
	
    /**
	   * Updates the list of avaliable positions in a machine
	   */
    private void actualizarPos() {
    	cPos.removeAllItems();
    	String[] posLibre = cargarPos();
    	for(int i = 0; i < posLibre.length; i++){
    		cPos.addItem(posLibre[i]);
    	}
	}

  /**
	 * Effects the creation of a new empty stock in a machine introducing a new input in the database
	 */
	private void crearStock() throws ArrayIndexOutOfBoundsException, NumberFormatException, SQLException, OutOfStockException{
		String[] datos = new String[Stock.getNombreColumnas().length];
    	Maquina maquina =(Maquina) cMaquinas.getSelectedItem();
    	datos[0] = String.valueOf(maquina.getId());
    	Producto producto = inventario.getSelectedValue();
    	datos[1] = String.valueOf(producto.getId());
    	datos[2] = ano + "-" + mes + "-" + dia + "  " + horas + ":" + mins + ":" + segs;
    	datos[3] = "0"; //Se crea el stock sin productos
    	datos[4] = (String) cPos.getSelectedItem();

    	comandos.insertar(Stock.getFormatoColumnas(), datos, Principal.getTablastock());
    	
    	}  

  /**
	 * Checks if a date is correct or not 
	 * @return true if the date is correct and false if it is incorrect
	 */
	private boolean comprobarFecha() {
        if(!textFecha.getText().toString().equals("") && !textHora.getText().toString().equals("")){

            String fecha = textFecha.getText().toString();
            String [] anoMesDia = fecha.split("[-]");
            String hora = textHora.getText().toString();
            String [] horaMin = hora.split("[:]");

            if(anoMesDia.length == 3) {
                try {
                    ano = Integer.valueOf(anoMesDia[0]);
                    mes = Integer.valueOf(anoMesDia[1]);
                    dia = Integer.valueOf(anoMesDia[2]);
                    horas = Integer.valueOf(horaMin[0]);
                    mins = Integer.valueOf(horaMin[1]);
                    segs = Integer.valueOf(horaMin[2]);
                    
                    if ((horas >= 0 && horas < 24) && (mins >= 0 && mins < 60) && (segs >= 0 && segs < 60) && (mes > 0 && mes <= 12) && (ano > 0)) {
                        if (comprobarDiaMeses(dia, mes, ano)) {
                        	return true;
                        }
                    }
                }catch (NumberFormatException e){
                	LOGGER.log(Level.ALL, e.getMessage());
                }
            }
        }
        else{
        	Calendar cal;
        	cal = Calendar.getInstance();
        	
        	ano = cal.get(Calendar.YEAR);
        	mes = cal.get(Calendar.MONTH)+1;
        	dia = cal.get(Calendar.DAY_OF_MONTH);
        	horas = cal.get(Calendar.HOUR_OF_DAY);
            mins = cal.get(Calendar.MINUTE);
            segs = cal.get(Calendar.SECOND);
            return  true;
        }
        return false;
    }

    /**
	   * Checks if the specified days for a month are correct 
	   * @return true if it is correct and false if it is incorrect
	   */
    private boolean comprobarDiaMeses(int dia, int mes, int ano) {
        int numdias = 31;
        switch (mes){
            case	4:
            case	6:
            case	9:
            case   11: numdias = 30; break;
            case	2: numdias= (ano%4==0) ? 29:28;
        }
        if (dia > numdias || dia<1){
            return false;
        }
        return true;
    }
}
