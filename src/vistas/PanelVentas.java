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
import java.util.Calendar;
import java.util.List;

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

import Adaptadores.ComboRenderer;
import Excepciones.FechaIncorrecta;
import Excepciones.OutOfStockException;
import Productos.Inventario;
import Productos.Producto;
import Stock.Stock;
import clientes.Cliente;
import clientes.Clientela;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import maquinas.Maquina;
import maquinas.Maquinaria;
import venta.Venta;

public class PanelVentas extends JPanel implements ActionListener{

	Principal principal;
	MyDataAccess conexion;
	JScrollPane panelScroll;
	Toolkit toolkit;
	Comandos comandos;
	
	JList<Producto> inventario;
	DefaultListModel<Producto> modelo;
	
	JLabel labelFecha, labelPrecio, labelHora;
	JTextField textFecha, textPrecio, textHora;
	
	JPanel panel;
	JComboBox<Maquina> cMaquinas;
	JComboBox<Producto> cProductos;
	JComboBox<Cliente> cClientes;
	
	int ano, mes, dia, horas, mins, segs;
	
	static final String IMAGEN_OK = "img/ok.png";
	static final String IMAGEN_ATRAS = "img/atras.png";
	private static final String IM_ERROR = "img/error.png";
	private static final String IM_COMPLETADO = "img/completado.png";
	
	public PanelVentas(JFrame v, MyDataAccess conexion){
		super(new BorderLayout(0, 0));
		this.principal = (Principal) v;
		this.conexion = conexion;
		comandos = new Comandos(conexion);
		toolkit = Toolkit.getDefaultToolkit();
		labelFecha= new JLabel("Fecha(a�o-mes-dia):", SwingConstants.RIGHT);
		textFecha = new JTextField (20);
		labelPrecio = new JLabel("Precio:", SwingConstants.RIGHT);
		textPrecio = new JTextField (20);
		labelHora= new JLabel("Hora(hh:mm:ss):", SwingConstants.RIGHT);
		textHora = new JTextField (20);
		panel = new JPanel();
		panel.setLayout(new GridLayout(3,1,0,175));
		panel.setBorder(BorderFactory.createEmptyBorder((int)toolkit.getScreenSize().getWidth()/20, (int)toolkit.getScreenSize().getWidth()/8,
						(int)toolkit.getScreenSize().getWidth()/20, (int)toolkit.getScreenSize().getWidth()/8)); 
		this.add(crearPanelSeleccion(),BorderLayout.NORTH);
		this.add(crearPanelOpciones(),BorderLayout.SOUTH);
	}

	public void actualizar(){
		this.add(crearPanelSeleccion(),BorderLayout.NORTH);
	}
	
	private Component crearPanelOpciones() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(crearPanelDatos(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private Component crearPanelDatos() {
		JPanel panel = new JPanel(new BorderLayout(25, 25));
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(20,200,20,350));
		panel.add(crearPanelCampo(labelFecha, textFecha), BorderLayout.WEST);
		panel.add(crearPanelCampo(labelPrecio, textPrecio), BorderLayout.EAST);
		panel.add(crearPanelCampo(labelHora, textHora), BorderLayout.CENTER);
		return panel;
	}
	
	private JPanel crearPanelCampo(JLabel label, JTextField text) {
		JPanel panel = new JPanel(new GridLayout(1,2,20,25));
		panel.setBackground(Color.WHITE);
		text.setBorder(BorderFactory.createLoweredBevelBorder());
		label.setFont(new Font("Arial", Font.BOLD, 22));
		panel.add(label);
		panel.add(text);
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

	private Component crearPanelSeleccion() {
		panel.removeAll();
		panel.add(crearPanelMaquinas());
		panel.add(crearPanelProductos());
		panel.add(crearPanelClientes());
		panel.setBackground(Color.WHITE);
		return panel;
	}
	
	private Component crearPanelProductos() {
		JPanel pCombo = new JPanel();
		cProductos = new JComboBox<>(cargarProductos());
		cProductos.setFont(new Font("Arial", Font.BOLD, 25));
		cProductos.setRenderer(new ComboRenderer());
		pCombo.add(cProductos);
		cProductos.setBackground(Color.WHITE);
		return cProductos;
	}

	private Component crearPanelMaquinas() {		
		JPanel pCombo = new JPanel();
		cMaquinas = new JComboBox<>(cargarMaquinas());
		cMaquinas.setFont(new Font("Arial", Font.BOLD, 25));
		cMaquinas.setRenderer(new ComboRenderer());
		cMaquinas.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	cProductos.removeAllItems();
		    	Producto[] productos = cargarProductos();
		    	for(int i = 0; i < productos.length; i++){
		    		cProductos.addItem(productos[i]);
		    	}
		    }
		});
		pCombo.add(cMaquinas);
		cMaquinas.setBackground(Color.WHITE);
		return cMaquinas;
	}
	
	private Component crearPanelClientes() {
		JPanel pCombo = new JPanel();
		cClientes = new JComboBox<>(cargarClientes());
		cClientes.setFont(new Font("Arial", Font.BOLD, 25));
		cClientes.setRenderer(new ComboRenderer());
		pCombo.add(cClientes);
		cClientes.setBackground(Color.WHITE);
		return cClientes;
	}

	private Maquina[] cargarMaquinas() {
		List<Maquina> listaMaquinas = Maquinaria.cargarDatos(conexion);
		Maquina[] maquinaria = new Maquina [listaMaquinas.size()];
		for(int i = 0; i < listaMaquinas.size(); i++){
			maquinaria[i] = listaMaquinas.get(i);
		}
		return maquinaria;
	}
	
	private Producto[] cargarProductos() {
		Maquina maquina = (Maquina) cMaquinas.getSelectedItem();
		int maquinaSeleccionadaID = maquina.getId();
		List<Producto> listaProductos = Inventario.cargarDatos(conexion, maquinaSeleccionadaID);
		Producto[] inventario = new Producto [listaProductos.size()];
		for(int i = 0; i < listaProductos.size(); i++){
			inventario[i] = listaProductos.get(i);
		}
		return inventario;
	}
	
	private Cliente[] cargarClientes() {
		List<Cliente> listaClientes = Clientela.cargarDatos(conexion);
		Cliente[] clientela = new Cliente [listaClientes.size()];
		for(int i = 0; i < listaClientes.size(); i++){
			clientela[i] = listaClientes.get(i);
		}
		return clientela;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("cancel")){
			principal.volver();
		}
		else if(e.getActionCommand().equals("ok")){
			String nombreError = null;
			
			try {
				if(comprobarFecha()){
					crearVenta();
					JOptionPane.showMessageDialog(null, "Se ha insertado el dato",
							"Dato insertado",JOptionPane.INFORMATION_MESSAGE, new ImageIcon(IM_COMPLETADO));
					return;
				}
				
				} catch (SQLException e1) {
					try {
						conexion.setQuery("rollback");
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					nombreError = String.valueOf(e1);
				}catch(ArrayIndexOutOfBoundsException e1){
					nombreError = "Selecciona todos los campos";
				}catch(NumberFormatException e1){
					nombreError = "Datos incorrectos";
				} catch (OutOfStockException e1) {
					nombreError = "No queda stock de este producto";
				} catch (FechaIncorrecta e1) {
					nombreError = "Introduce una fecha valida";
				} catch (NullPointerException e1) {
					nombreError = "No hay stock";
				}
			
			toolkit.beep();
			JOptionPane.showMessageDialog(null, nombreError,
					"Error",JOptionPane.ERROR_MESSAGE, new ImageIcon(IM_ERROR));
			}
	}
	
    private void crearVenta() throws ArrayIndexOutOfBoundsException, NumberFormatException, SQLException, OutOfStockException, NullPointerException{
    	String[] datos = new String[Venta.getNombreColumnas().length];
    	
    	Cliente cliente =(Cliente) cClientes.getSelectedItem();
    	datos[0] = String.valueOf(cliente.getId());
    	Maquina maquina =(Maquina) cMaquinas.getSelectedItem();
    	datos[1] = String.valueOf(maquina.getId());
    	Producto producto =(Producto) cProductos.getSelectedItem();
    	datos[2] = String.valueOf(producto.getId());
    	
    	if(!textPrecio.getText().equals("")){
    		datos[3] = textPrecio.getText();
    	}
    	else{
    		datos[3] = String.valueOf(producto.getPrecio());
    	}
    	
    	datos[4] = ano + "-" + mes + "-" + dia + "  " + horas + ":" + mins + ":" + segs;
    	
    	conexion.setQuery("begin");
    	
    	actualizarStock(datos[1], datos[2], datos[4]);
    	
    	comandos.insertar(Venta.getFormatoColumnas(), datos, Principal.getTablaventa());
    	
    	conexion.setQuery("commit");
	}
    
	private void actualizarStock(String maquinaID, String productoID, String fecha) throws SQLException, OutOfStockException{
		ResultSet cantidadActual;
		int total = -1;
		String[] nombreColumnas = new String[1];
		String primaryKey;
		String datos[] = new String[Stock.getNombreColumnas().length - 1]; // -1 porque la posicion no se actualiza
		datos[0] = maquinaID;
		datos[1] = productoID;
		datos[2] = fecha;
		nombreColumnas[0] = Stock.getNombreColumnas()[3];
		primaryKey = Maquina.getNombreColumnas()[0] + " = " + datos[0] + " and " + Producto.getNombreColumnas()[0] + " = " + datos[1];
		
		cantidadActual = comandos.select(nombreColumnas, Principal.getTablastock(), primaryKey, null, null, false, 0);
		
		if(cantidadActual.next()){
			total += cantidadActual.getInt(1);
			datos[3] = String.valueOf(total);
		}
		if(total == -1){
			throw new OutOfStockException();
		}
		
		comandos.update(Stock.getFormatoColumnas(), datos, Stock.getNombreColumnas(), Principal.getTablastock(), primaryKey);
	}

	private boolean comprobarFecha() throws FechaIncorrecta, NumberFormatException{
        if(!textFecha.getText().toString().equals("") && !textHora.getText().toString().equals("")){

            String fecha = textFecha.getText().toString();
            String [] anoMesDia = fecha.split("[-]");
            String hora = textHora.getText().toString();
            String [] horaMin = hora.split("[:]");

            if(anoMesDia.length == 3) {
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
            }
            throw new FechaIncorrecta();
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
    }

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
