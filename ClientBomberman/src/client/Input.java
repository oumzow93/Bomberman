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
	private static String reponseprecedent="";

	public Input(Socket client) {
		super();
		this.client = client;

	}


	@Override
	public void run() {

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

			while (true) {
				String reponse ;
				
				while((reponse=input.readLine())!=null) {
					gestionRequetteServeur(reponse);

				}

			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}

	}

	public  void gestionRequetteServeur(String requette) {
		if(requette.startsWith("DEMARAGE")) {
			String []infoRequette = requette.split(":");
			Controleurclient.setTurn(infoRequette[1]);
			this.recupTilleCarte(infoRequette[2]);
			this.recupBomberman(infoRequette[3]);
			this.recupPnj(infoRequette[4]);

			this.viewGame= new ViewBombermanGame();
			new Controleurclient(this.viewGame);
			this.viewGame.afficher();

		}else {
			if(requette.startsWith("UPDATE")) {
				String []infoRequette = requette.split(":");
				Controleurclient.setTurn(infoRequette[1]);
				this.recupTilleCarte(infoRequette[2]);
				this.recupBomberman(infoRequette[3]);
				this.recupPnj(infoRequette[4]);

				this.viewGame.actualiser();
			}

		}

	}

	public void recupTilleCarte(String taille) {

		if(!taille.isEmpty() && taille.length()>=2) {
			String []tailleRecup = taille.split(";");
			String []mataille  = tailleRecup[1].split("&");
			if(mataille.length==2) {
				Controleurclient.setSizeX(Integer.parseInt(mataille[0]) );
				Controleurclient.setSizeY(Integer.parseInt(mataille[1]) );
			}

		}else{
			System.out.println("taille : donnees manquante");
		}
	}
	public void recupBomberman(String donnee) {
		Controleurclient.getListBomberman().clear();
		String bomberman[] = donnee.split(";");

		if(bomberman[1].contains("&") ) {
			String bomber[] = bomberman[1].split("&");
			for( int i=0;i <bomber.length;i++) {
				String attribut[] = bomber[i].split(",");
				int x= Integer.parseInt(attribut[0]);
				int y= Integer.parseInt(attribut[1]);
				int d= Integer.parseInt(attribut[2]);
				int c= Integer.parseInt(attribut[4]);
				Controleurclient.setListBomberman(x, y, d, c);

			}
		}else {

			String attribut[] = bomberman[1].split(",");
			int x= Integer.parseInt(attribut[0]);
			int y= Integer.parseInt(attribut[1]);
			int d= Integer.parseInt(attribut[2]);
			int c= Integer.parseInt(attribut[4]);
			Controleurclient.setListBomberman(x, y, d, c);
		}



	}
	public void recupPnj(String donnee) {
		Controleurclient.getListPNJ().clear();
		String pnj[] = donnee.split(";");

		if(pnj[1].contains("&") ) {
			String bomber[] = pnj[1].split("&");
			for( int i=0;i <bomber.length;i++) {
				String attribut[] = bomber[i].split(",");
				int x= Integer.parseInt(attribut[0]);
				int y= Integer.parseInt(attribut[1]);
				int d= Integer.parseInt(attribut[2]);
				char t=  attribut[3].charAt(0);
				Controleurclient.setListPNJ(x,y, d, t);

			}
		}else {

			String attribut[] = pnj[1].split(",");
			int x= Integer.parseInt(attribut[0]);
			int y= Integer.parseInt(attribut[1]);
			int d= Integer.parseInt(attribut[2]);
			char t=  attribut[3].charAt(0);
			Controleurclient.setListPNJ(x,y, d, t);
		}
		
	}

	public Socket getClient() {
		return client;
	}


	public ViewBombermanGame getViewGame() {
		return viewGame;
	}


	public static String getReponseprecedent() {
		return reponseprecedent;
	}


	public static void setReponseprecedent(String reponseprecedent) {
		Input.reponseprecedent = reponseprecedent;
	}






}
