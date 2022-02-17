package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import controleur.Controleurclient;
import vue.ViewBombermanGame;




public class Input  extends Thread{

	private Socket client;
	private  ViewBombermanGame  viewGame;
	private  static String getTrun= "";

	public Input(Socket client) {
		super();
		this.client = client;
		this.viewGame= new ViewBombermanGame();
		new Controleurclient(this.viewGame);
	}


	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

			while (true) {
				String reponse = input.readLine();
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
			this.viewGame.afficher();
			
		}else {
			String []infoRequette = requette.split(";");
			String entete =infoRequette[0];
			String info = infoRequette[1];
			switch(entete) {
			case "UPDATE_TURN": 
				Input.setGetTrun(info);
				this.viewGame.actualiser();
				break;
					
			}
		}

	}

	public Socket getClient() {
		return client;
	}


	public ViewBombermanGame getViewGame() {
		return viewGame;
	}


	public static String getGetTrun() {
		return getTrun;
	}


	public static void setGetTrun(String getTrun) {
		Input.getTrun = getTrun;
	}



}
