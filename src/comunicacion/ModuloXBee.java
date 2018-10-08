package comunicacion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.models.XBee64BitAddress;

import Excepciones.OutOfStockException;
import Productos.Producto;
import Stock.Stock;
import clientes.Cliente;
import conexionSQL.Comandos;
import conexionSQL.MyDataAccess;
import maquinas.Maquina;
import venta.Venta;
import vistas.Principal;

public class ModuloXBee extends XBeeDevice {

	private static final int BAUD_RATE = 9600;
	private static final String MAC_XBEE_REMOTA = "0013A20040084D25";
	RemoteXBeeDevice xBeeRemota;
	Emision emitir;
	Recepcion recibir;
	MyDataAccess conexion;
	Comandos comandos;
	
	public ModuloXBee(String puerto, MyDataAccess conexion) {
		super(puerto, BAUD_RATE);
		this.conexion = conexion;
		comandos = new Comandos(conexion);
		xBeeRemota = new RemoteXBeeDevice(this, new XBee64BitAddress(MAC_XBEE_REMOTA));
		emitir = new Emision();
		abrirModulo();
	}

	public void abrirModulo() {
		try {
			this.open();
		} catch (XBeeException e) {
			e.printStackTrace();
		}
		
		recibir = new Recepcion(this);
		this.addDataListener(recibir);
	}

	public void mandarTramaVentaAceptada(){
		int dato = emitir.crearTramaVentaAceptada();
		mandarTrama(dato);
		System.out.println("Se ha mandado trama venta aceptada");
	}
	
	public void mandarTramaDatosAceptados(){
		int dato = emitir.crearTramaDatosAceptados();
		mandarTrama(dato);
		System.out.println("Se ha mandado la trama datos aceptados");
	}
	
	public void mandarTramaDatosRechazados(){
		int dato = emitir.crearTramaDatosRechazados();
		mandarTrama(dato);
		System.out.println("Se ha mandado la trama datos rechazados");
	}
	
	public void mandarTramaDatosNuevoUsuario(){
		int dato = emitir.crearTramaNuevoUsuario();
		mandarTrama(dato);
		System.out.println("Se ha mandado la trama nuevo usuario");
	}
	
	public void mandarTramaDatosNuevoUsuarioCancelado(){
		int dato = emitir.crearTramaNuevoUsuarioCancelado();
		mandarTrama(dato);
		System.out.println("Se ha mandado la trama nuevo usuario cancelado");
	}

	private void mandarTrama(int dato) {
		byte[] trama = new byte[1];
		trama[0] = (byte) dato;
		
		try {
			this.sendData(xBeeRemota, trama);
		} catch (XBeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Recepcion getRecibir() {
		return recibir;
	}

	public void cerrarModulo() {
		this.close();
	}

	public void confirmarVenta(String maquinaID, String husilloID, String usuarioID) {
		String[] datos = new String[Venta.getNombreColumnas().length];
		datos[0] = usuarioID;
		datos[1] = maquinaID;
		datos[2] = husilloID;
		datos[4] = calcularFecha();
		try {
			datos[3] = calcularPrecio(husilloID, maquinaID); // Precio actual del producto
			actualizarStock(datos[1], datos[2], datos[4]); // Restar una unidad del stock
			comandos.insertar(Venta.getFormatoColumnas(), datos, Principal.getTablaventa()); // Insertar venta
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al registrar una venta",
					"Error",JOptionPane.ERROR_MESSAGE);
		}	
	}

	private void actualizarStock(String maquinaID, String husilloID, String fecha) throws SQLException {
		ResultSet cantidadActual;
		int total = -1;
		String[] nombreColumnas = new String[1];
		String primaryKey;
		String datos[] = new String[Stock.getNombreColumnas().length-1];
		datos[0] = maquinaID;
		datos[1] = husilloID;
		datos[2] = fecha;
		nombreColumnas[0] = Stock.getNombreColumnas()[3];
		primaryKey = Maquina.getNombreColumnas()[0] + " = " + datos[0] + " and " + Stock.getNombreColumnas()[4] + " = " + datos[1];
		
		cantidadActual = comandos.select(nombreColumnas, Principal.getTablastock(), primaryKey, null, null, false, 0);
		
		if(cantidadActual.next()){
			total += cantidadActual.getInt(1);
			datos[3] = String.valueOf(total);
		}
		
		comandos.update(Stock.getFormatoColumnas(), datos, Stock.getNombreColumnas(), Principal.getTablastock(), primaryKey);
	}

	private String calcularPrecio(String husilloID, String maquinaID) throws SQLException {
		String precio = null;
		String[] nombreColumnas = new String[1];
		nombreColumnas[0] = Producto.getNombreColumnas()[2];
		String primaryKey = Stock.getNombreColumnas()[4] + " = " + husilloID + " and " + Maquina.getNombreColumnas()[0] + " = " + maquinaID;
		String seleccion = Principal.getTablaproducto() + " JOIN " + Principal.getTablastock() 
		   + " ON " + Principal.getTablaproducto() + "." + Producto.getNombreColumnas()[0] 
		   + " = " + Principal.getTablastock() + "." + Producto.getNombreColumnas()[0];
		
		ResultSet datos = comandos.select(nombreColumnas, seleccion, primaryKey, null, null, false, 0);
		
		if(datos.next()){
			precio = datos.getString(1);
		}
		
		return precio;
	}

	private String calcularFecha() {
		int ano, mes, dia, horas, mins, segs;
    	Calendar cal;
    	cal = Calendar.getInstance();
    	
    	ano = cal.get(Calendar.YEAR);
    	mes = cal.get(Calendar.MONTH)+1;
    	dia = cal.get(Calendar.DAY_OF_MONTH);
    	horas = cal.get(Calendar.HOUR_OF_DAY);
        mins = cal.get(Calendar.MINUTE);
        segs = cal.get(Calendar.SECOND);
        
        return ano + "-" + mes + "-" + dia + "  " + horas + ":" + mins + ":" + segs;
	}

	public void comprobarDatos(String maquinaID, String husilloID, String usuarioID) {
		int valido = 1;
		String[] nombreColumnas = new String[1];
		String primaryKey;
		
    	nombreColumnas[0] = Stock.getNombreColumnas()[3]; // Total
    	primaryKey = Maquina.getNombreColumnas()[0] + " = " + maquinaID + " and " + Stock.getNombreColumnas()[4] + " = " + husilloID;
    	try {
    		valido = valido * comprobar(nombreColumnas, Principal.getTablastock(), primaryKey); // Comprobar que hay productos disponibles
    		
    		nombreColumnas[0] = "count(*)";
			primaryKey = Cliente.getNombreColumnas()[0] + " = '" + usuarioID + "'";
			valido = valido * comprobar(nombreColumnas, Principal.getTablacliente(), primaryKey); // Comprobar que el cliente existe
		
	    	if(valido == 1) mandarTramaDatosAceptados();
	    	else mandarTramaDatosRechazados();
	    	
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar datos",
					"Error",JOptionPane.ERROR_MESSAGE);
			
			mandarTramaDatosRechazados();
		}
	}

	private int comprobar(String[] nombreColumnas, String tabla, String primaryKey) throws SQLException {
		ResultSet datos = comandos.select(nombreColumnas, tabla, primaryKey, null, null, false, 0);
		
		if(datos.next()){    		
        	if(Integer.valueOf(datos.getString(1)) > 0){
        		return 1;
        	}
        	else return 0;
		}
		
		return 0;
	}

}