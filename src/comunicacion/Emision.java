package comunicacion;

public class Emision {
	final static int DATOS_ACEPTADOS = 97; // 1 trama
	final static int DATOS_RECHAZADOS = 98; // 1 trama
	final static int NUEVO_USUARIO = 99; // 1 tramas
	final static int NUEVO_USUARIO_CANCELADO = 100; // 1 tramas
	final static int VENTA_ACEPTADA = 103; // 1 trama
	
	public Emision() {
		
	}
	
	public int crearTramaDatosAceptados(){
		return DATOS_ACEPTADOS;
	}
	
	public int crearTramaDatosRechazados(){
		return DATOS_RECHAZADOS;
	}
	
	public int crearTramaNuevoUsuario(){
		return NUEVO_USUARIO;
	}
	
	public int crearTramaNuevoUsuarioCancelado(){
		return NUEVO_USUARIO_CANCELADO;
	}
	
	public int crearTramaVentaAceptada(){
		return VENTA_ACEPTADA;
	}
}
