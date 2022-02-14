package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import modele.BombermanGame;
import modele.Game;



public class MyServer extends Thread {
	private ArrayList<Echange> clients = new ArrayList<>();
	private int ID_CLIENT;
	private static Game game;
	private static String requetteServeur="DEMARAGE";



	public MyServer() {
		this.ID_CLIENT=0;
	    game= new BombermanGame(100, "test");
	}

	public void run() {
		try {
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(5000);
			System.out.println("DEMARAGE DU SERVEUR");
			//=========================================BOUCLE INFINI POUR CONNECTER PLUSIEUR CLIENT
			while(true) {
				Socket client =server.accept();
				++this.ID_CLIENT;
				Echange ech= new Echange(client,this.ID_CLIENT);
				this.clients.add(ech);
				ech.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//=======================================CLASSE POUR LA GESTION DES ENTRÉES ET SORTIES===============
	public class Echange extends Thread{
		private int id_client;
		private Socket client;
		public Echange(Socket client, int id_client) {
			super();
			this.setId_client(id_client);
			this.client = client;
		}

		//============================================BRODCASTING====================================
		public void broadcast(String message) {


			for(Echange echange:clients) {
				try {
					PrintWriter sortie= new PrintWriter (echange.getClient().getOutputStream(),true);

					MyServer.gestionRequetteClient(message);
					sortie.println(requetteServeur);
					sortie.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
		@Override
		public void run() {
			String IPadr= client.getRemoteSocketAddress().toString();
			System.out.println("CONNEXION DU CLIENT : "+this.id_client+" avec IP: "+IPadr);
			try {
				BufferedReader  entree = new BufferedReader(new InputStreamReader(client.getInputStream()));




				String reponse;
				while(!(reponse =  entree.readLine()).equals(null)) {
					//=================SORTIE
					broadcast(reponse);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NullPointerException e) {
				System.out.println("DECONNEXION DU CLIENT :"+this.id_client+" avec IP: "+IPadr);
			}

		}
		

		public Socket getClient() {
			return client;
		}



		public int getId_client() {
			return id_client;
		}

		public void setId_client(int id_client) {
			this.id_client = id_client;
		}


	}
	//=====================================GESTION DES REQUETTE VENANT DU CLIENT 
	public  static void gestionRequetteClient(String requette) {
		String []infoRequette = requette.split(";");
		String entete =infoRequette[0];
		String info = infoRequette[1];
		switch(entete) {
		case "DEPLACEMENT": 
			if(info.equals("HAUT") || info.equals("BAS") || info.equals("GAUCHE") || info.equals("DROITE")) {
				game.SetTurn(game.getTurn()+1);
				requetteServeur= "UPDATE_TURN;"+game.getTurn();
			}break;
		}
	}

	public Game getGame() {
		return game;
	}

	public static String getRequetteServeur() {
		return requetteServeur;
	}

	public static void setRequetteServeur(String requetteServeur) {
		MyServer.requetteServeur = requetteServeur;
	}

	//=========================================================MAIN============================================
	public static void main(String[] args) {
		new MyServer().start();

	}

}
