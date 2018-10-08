package vistas;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import comunicacion.ModuloXBee;
import conexionSQL.MyDataAccess;

public class PanelTrabajador extends JPanel implements ActionListener {

	Principal principal;
	MyDataAccess conexion;
	ModuloXBee xBee;
	JPanel panelCrear, panelEditar;
	
	private static final String PANEL_OPCIONES = "panel opciones";
	private static final String PANEL_EDITAR = "panel clientes";
	private static final String PANEL_CREAR = "panel maquinas";
	
	static final String FONDO_PANEL_TRABAJADOR = "img/fondo_principal.jpg";
	static final String IMAGEN_CREAR = "img/crear.png";
	static final String IMAGEN_EDITAR = "img/editar.png";
	static final String IMAGEN_ATRAS = "img/volver.png";
	
	public PanelTrabajador(JFrame v, MyDataAccess conexion, ModuloXBee xBee) {
		super(new CardLayout());
		principal = (Principal) v;
		this.conexion = conexion;
		this.xBee = xBee;
		this.add(crearPanelPrincipal(), PANEL_OPCIONES);
	}

	private Component crearPanelPrincipal() {
		ImagenFondo panel = new ImagenFondo(FONDO_PANEL_TRABAJADOR, new GridBagLayout());
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.add(botonTransparente(IMAGEN_CREAR, "crear", "CREAR"));
		panel.add(botonTransparente(IMAGEN_EDITAR, "editar", "EDITAR"));
		panel.add(botonTransparente(IMAGEN_ATRAS, "atras", "VOLVER"));
		return panel;
	}

	private Component botonTransparente(String i, String c, String titulo){
		JButton b = new JButton(new ImageIcon(i));
		b.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		b.setActionCommand(c);
		b.setToolTipText(titulo);
		b.addActionListener(this);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainted(false);
		return b;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CardLayout panel = (CardLayout) this.getLayout();
		if(e.getActionCommand().equals("crear")){
			panelCrear = new PanelCrear(this, conexion, principal, xBee);
			this.add(panelCrear, PANEL_CREAR);
			panel.show(this, PANEL_CREAR);
		}else if(e.getActionCommand().equals("editar")){
			panelEditar = new PanelEditar(this, conexion, principal, xBee);
			this.add(panelEditar, PANEL_EDITAR);
			panel.show(this, PANEL_EDITAR);
		}else if(e.getActionCommand().equals("atras")){
			principal.volver();
		}
	}
	
	public void volver(){
		CardLayout panel = (CardLayout) this.getLayout();
		panel.show(this, PANEL_OPCIONES);
	}
}