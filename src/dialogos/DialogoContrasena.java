/**
 * @file DialogoContraseña.java
 * @author Imanol Badiola
 * @brief This file manages the password dialog of the login
 */


package dialogos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class DialogoContrasena extends JDialog implements ActionListener {

	transient Toolkit toolkit;
	
	JLabel labelPass;
	JPasswordField password;
	
	/**
	  * Constructor of the class which sets size, closing operation and specifies the panel that will be put inside
	  * @param ventana Parent windows
	  * @param titulo Title of the dialog
      * @param modo Parameter that blocks any interaction with the parent window
	  */
	public DialogoContrasena(JFrame ventana, String titulo, boolean modo){
		super(ventana, titulo, modo);
		toolkit = Toolkit.getDefaultToolkit();
		this.setLocation((int)toolkit.getScreenSize().getWidth()*3/8, (int)toolkit.getScreenSize().getHeight()/5);
		this.setSize((int)toolkit.getScreenSize().getWidth()/4, (int)toolkit.getScreenSize().getHeight()/7);
		this.setContentPane(crearPanelVentana());
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}

	/**
	  * Creates the panel inside the dialog 
	  * @return The panel object to put inside dialog
	  */
	private Container crearPanelVentana() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearCampoPass(), BorderLayout.CENTER);
		panel.add(crearBoton("OK"),BorderLayout.SOUTH);
		panel.setBackground(Color.WHITE);
		return panel;
	}

	/**
	  * Creates the password input field
	  * @return The password field object
	  */
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
	
	/**
	  * Creates a button 
	  * @param titulo Title of the button
	  * @return Created button object
	  */
	private Component crearBoton(String titulo) {
		JButton boton;
		boton = new JButton (titulo);
		boton.setActionCommand(titulo);
		boton.addActionListener(this);
		return boton;
	}
	
	/**
	  * Getter of the password written
	  * @return The password as a string
	  */
	public String getPassword() {
		return new String(password.getPassword());
	}

	/**
	  * Method called by the Button class when pressed, overridden to close dialog
	  * @param e Event occurred
	  */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("OK")){
			this.dispose();
		}
	}
}
