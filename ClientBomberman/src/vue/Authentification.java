package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Output;

public class Authentification {

	private  JFrame fenetre;
	private JButton valider;



	private JTextField login;
	private JTextField password;




	public Authentification() {
		super();

		this.fenetre = new JFrame("AUTHENTIFIACATION");
		this.fenetre.setSize(300,100);
		
		this.valider = new JButton("Valider");
		
		JLabel labLogin = new JLabel("Login: ");
		JLabel labPassword= new JLabel("password: ");
		
		this.login = new JTextField();
		this.password=new  JTextField();
		
		JPanel p = new JPanel (new GridLayout(2,2));
		
		p.add(labLogin); p.add(this.login);
		p.add(labPassword); p.add(this.password);
		
		this.fenetre.add(p,BorderLayout.CENTER);
        
		this.fenetre.add(this.valider, BorderLayout.PAGE_END);
        


		this.fenetre.setLocationRelativeTo(null);
		this.fenetre.setVisible(true);
		
		
		//==============================================EVENEMENT SUR LE BOUTON VALIDER
		this.valider.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String usname= login.getText();
				String pass = password.getText();
				
				if(!usname.equals("") && !pass.equals("")) {
					String requete = "connexion;"+usname+";"+pass;
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
