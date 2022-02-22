package vue;
/*package vue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

//import java.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


//import com.sun.tools.sjavac.comp.dependencies.PublicApiCollector;

//import com.sun.org.apache.xpath.internal.operations.And;

import client.Output;

public class Authentification {

	private  JFrame fenetre;
	private JButton valider;
	private JButton cancel;



	private JTextField login;
	private JTextField password;
	static ResultSet rs;




	public Authentification() {
		super();

		this.fenetre = new JFrame("AUTHENTIFIACATION");
		this.fenetre.setSize(700,170);

		this.valider = new JButton("Connexion");
		this.cancel = new JButton("Cancel");

		JLabel labLogin = new JLabel("Login: ");
		JLabel labPassword= new JLabel("password: ");

		this.login = new JTextField();
		this.password=new  JTextField();

		JPanel p = new JPanel (new GridLayout(2,2));

		p.add(labLogin); p.add(this.login);
		p.add(labPassword); p.add(this.password);

		this.fenetre.add(p,BorderLayout.CENTER);

		this.fenetre.add(this.valider, BorderLayout.PAGE_END);
		//this.fenetre.add(this.can)



		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setVisible(true);


		//==============================================EVENEMENT SUR LE BOUTON VALIDER
		this.valider.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {
				String usname= login.getText();
				String pass = password.getText();




				if(!usname.equals("") && !pass.equals("")) {
					String requete = "CONNEXION;"+usname+";"+pass;
					Output.setRequete(requete);
					fenetre.setVisible(false);




				}


			}
		});

	}
		 
	
	public JFrame getFenetre() {
		return fenetre;
	}
	public JButton getValider() {
		return valider;
	}
	public JTextField getLogin() {
		return login;
	}
	public JTextField getPassword() {
		return password;
	}

}
*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import client.Output;

//import com.sun.tools.sjavac.comp.dependencies.PublicApiCollector;
public class Authentification extends JFrame {
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
							//this.setVisible(false);




						}


					}

					
				});
				
				this.cancel.addActionListener(new ActionListener() {


					@Override
					public void actionPerformed(ActionEvent e) {
						String usname= login.getText();
						String pass = password.getText();

							//String requete = "CONNEXION;"+usname+";"+pass;
							//Output.setRequete(requete);
							//this.setVisible(false);
							dispose();




						


					}

					
				});

			}
				 
		
		
	}
	
	














