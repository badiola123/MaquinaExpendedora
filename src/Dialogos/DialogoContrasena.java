package Dialogos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class DialogoContrasena extends JDialog implements ActionListener {

	Toolkit toolkit;
	
	JLabel labelPass;
	JPasswordField password;
	
	public DialogoContrasena(JFrame ventana, String titulo, boolean modo){
		super(ventana, titulo, modo);
		toolkit = Toolkit.getDefaultToolkit();
		this.setLocation((int)toolkit.getScreenSize().getWidth()*3/8, (int)toolkit.getScreenSize().getHeight()/5);
		this.setSize((int)toolkit.getScreenSize().getWidth()/4, (int)toolkit.getScreenSize().getHeight()/7);
		this.setContentPane(crearPanelVentana());
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}

	private Container crearPanelVentana() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearCampoPass(), BorderLayout.CENTER);
		panel.add(crearBoton("OK"),BorderLayout.SOUTH);
		panel.setBackground(Color.WHITE);
		return panel;
	}

	private Component crearCampoPass() {
		JPanel panel = new JPanel (new BorderLayout(10, 10));
		
		password = new JPasswordField (20);
		password.setBorder(BorderFactory.createLoweredBevelBorder());
		labelPass = new JLabel ("Contraseña:");
		labelPass.setFont(new Font ("Arial", Font.ITALIC, 16));
		labelPass.setHorizontalAlignment(JLabel.CENTER);
		labelPass.setBackground(Color.WHITE);
		
		panel.setBackground(Color.WHITE);
		panel.add(labelPass, BorderLayout.WEST);
		panel.add(password, BorderLayout.CENTER);
		return panel;
	}

	private Component crearBoton(String titulo) {
		JButton boton;
		boton = new JButton (titulo);
		boton.setActionCommand(titulo);
		boton.addActionListener(this);
		return boton;
	}
	
	public String getPassword() {
		return new String(password.getPassword());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("OK")){
			this.dispose();
		}
	}
}
