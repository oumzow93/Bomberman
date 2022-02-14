package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import vue.Clavier;
import vue.ViewCommand;

public class Input  extends Thread{

	private Socket client;
	private  ViewCommand  commande;

	public Input(Socket client) {
		super();
		this.client = client;
		commande  = new ViewCommand ();
	}


	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

			while (true) {
				String reponse = input.readLine();
				System.out.println(reponse);
				gestionRequetteServeur(reponse);


			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}

	}
	
	
	public  void gestionRequetteServeur(String requette) {
		if(requette.equals("DEMARAGE")) {
		    commande.affichier();
			Clavier clavier = new Clavier();
			
			commande.getFenetreCommand().addKeyListener(clavier);
			commande.getPause().addKeyListener(clavier);
			commande.getPlay().addKeyListener(clavier);
			commande.getRestart().addKeyListener(clavier);
			commande.getStep().addKeyListener(clavier);
			commande.getSlider().addKeyListener(clavier);
			commande.getFenetreCommand().setFocusable(true);


			
		}else {
			String []infoRequette = requette.split(";");
			String entete =infoRequette[0];
			String info = infoRequette[1];
			switch(entete) {
			case "UPDATE_TURN": 
				commande.UpdateTurn(info);	break;	
			}
		}

	}

	public Socket getClient() {
		return client;
	}



}
