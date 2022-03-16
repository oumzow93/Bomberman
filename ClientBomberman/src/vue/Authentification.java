package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import client.Output;

//import com.sun.tools.sjavac.comp.dependencies.PublicApiCollector;
public class Authentification  {
	/**
	 * 
	 */
	public static JFrame fenetre;
	private static JLabel  messageConnexion;
	JPanel panHaut,panBas;
	private JButton valider;
	private JButton cancel;

	private JTextField login;
	private JTextField password;
	public Authentification() {
	
		fenetre = new JFrame();
		fenetre.setSize(new Dimension(500,250));
		fenetre.setResizable(false); 
		Container contenu = fenetre.getContentPane();
		panHaut=new JPanel(new GridLayout(3,2));
		panBas=new JPanel();
		JPanel panCentre = new JPanel();
		
		contenu.add(panHaut,BorderLayout.NORTH);
		contenu.add(panBas,BorderLayout.SOUTH);
		contenu.add(panCentre,BorderLayout.CENTER);
		messageConnexion= new JLabel();
		panCentre.add(messageConnexion);
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
		fenetre.setVisible(true);
		fenetre.setLocationRelativeTo(null);
		panBas.setBorder(b);
		
		
		//==============================================EVENEMENT SUR LE BOUTON VALIDER
				this.valider.addActionListener(new ActionListener() {


					@Override
					public void actionPerformed(ActionEvent e) {
						String usname= login.getText();
						String pass = password.getText();




						if(!usname.equals("") && !pass.equals("")) {
							String requete = "CONNEXION:"+usname+":"+pass;
							Output.setRequete(requete);
							System.out.println(requete);

						}


					}

					
				});
				
				this.cancel.addActionListener(new ActionListener() {


					@Override
					public void actionPerformed(ActionEvent e) {
						fenetre.dispose();




						


					}

					
				});

			}
	
	
	public static void fermer() {
		fenetre.setVisible(false);
		
	}


	public static JLabel getMessageConnexion() {
		return messageConnexion;
	}


	public static void setMessageConnexion(String message ) {
		Authentification.messageConnexion.setText(message);
		Authentification.messageConnexion.setForeground(Color.red);
	}



				 
		
		
	}
	
	













