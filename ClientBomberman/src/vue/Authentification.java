package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import client.Output;

//import com.sun.tools.sjavac.comp.dependencies.PublicApiCollector;
public class Authentification extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panHaut,panBas;
	private JButton valider;
	private JButton cancel;

	private JTextField login;
	private JTextField password;
	public Authentification() {
		//initialisation du JFrame
		super();
		
		this.setSize(new Dimension(500,250));
		this.setResizable(false); 
		Container contenu = this.getContentPane();
		panHaut=new JPanel(new GridLayout(3,2));
		panBas=new JPanel();
		contenu.add(panHaut,BorderLayout.NORTH);
		contenu.add(panBas,BorderLayout.SOUTH);
		panHaut.add(new JLabel("username"));
		this.login = new JTextField();
		panHaut.add(login);
		
		panHaut.add(new JLabel("password"));
		this.password=new  JTextField();
		panHaut.add(password);
		this.valider=new JButton("connexion");
		panBas.add(this.valider);
		this.cancel=new JButton("cancel");
		panBas.add(cancel);
		panHaut.setBorder(new TitledBorder("authentification"));
		Border b = BorderFactory.createLineBorder(Color.blue.darker(),5);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		panBas.setBorder(b);
		
		
		//==============================================EVENEMENT SUR LE BOUTON VALIDER
				this.valider.addActionListener(new ActionListener() {


					@Override
					public void actionPerformed(ActionEvent e) {
						String usname= login.getText();
						String pass = password.getText();




						if(!usname.equals("") && !pass.equals("")) {
							String requete = "CONNEXION;"+usname+";"+pass;
							Output.setRequete(requete);
							setVisible(false);




						}


					}

					
				});
				
				this.cancel.addActionListener(new ActionListener() {


					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();




						


					}

					
				});

			
	}	 
		
		
	}
	
	















