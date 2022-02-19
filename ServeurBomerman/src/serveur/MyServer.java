package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import controleur.AbstractController;
import controleur.ControllerBombermanGame;
import modele.BombermanGame;




public class MyServer extends Thread {
	private ArrayList<Echange> clients = new ArrayList<>();
	private static int ID_CLIENT=0;
	private static AbstractController controller;
	private static String requetteServeur="DEPLACEMENT;STOP";
	



	public MyServer() {
		
	    
	}

	public void run() {
		try {
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(5000);
			System.out.println("DEMARAGE DU SERVEUR");
			//=========================================BOUCLE INFINI POUR CONNECTER PLUSIEUR CLIENT
			while(true) {
				Socket client =server.accept();
				++ID_CLIENT;
				Echange ech= new Echange(client,ID_CLIENT);
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
		public void broadcast(String message , Socket client) {


			for(Echange echange:clients) {
				try {
					PrintWriter sortie= new PrintWriter (echange.getClient().getOutputStream(),true);
                   if(!message.equals("null") || !message.equals(null)) {
                	   String [] infomessage= message.split(";");
                	   if(infomessage[0].equals("CONNEXION")) {
                		   if(echange.getClient().equals(client)) {
                			   sortie.println("DEMARAGE");
                		   }
                	   }else {
                		   MyServer.gestionRequetteClient(message);
                		   sortie.println(requetteServeur);
                		   
                	   }

                   }
					
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
				
				while(true) {
					//=================SORTIE
					String reponse =entree.readLine();
					broadcast(reponse,client);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NullPointerException e) {
				System.out.println("DECONNEXION DU CLIENT :"+this.id_client+" avec IP: "+IPadr);
				clients.remove(this);
				--ID_CLIENT;
				
				if(ID_CLIENT==0) {
					controller.getGame().init();
				}

				
				
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
		case "COMMANDE": 
			if(info.equals("PLAY")) {
				controller.play();
			}
			if(info.equals("Pause")) {
				controller.pause();
			}
			if(info.equals("STEP")) {
				controller.step();
			}
			if(info.equals("RESTART")) {
				controller.restart();
			}
			break;

		case "DEPLACEMENT": 
			if(info.equals("HAUT") || info.equals("BAS") || info.equals("GAUCHE") || info.equals("DROITE")) {
				controller.step();
				;
			}break;
		}
		requetteServeur= "UPDATE_TURN;"+controller.getGame().getTurn();
		
	}



	public static String getRequetteServeur() {
		return requetteServeur;
	}

	public static void setRequetteServeur(String requetteServeur) {
		MyServer.requetteServeur = requetteServeur;
	}



	public static AbstractController getController() {
		return controller;
	}

	public static void setController(AbstractController controller) {
		MyServer.controller = controller;
	}
	
	
	
	
	
	//=========================================================MAIN============================================
	public static void main(String[] args) {
		new MyServer().start();
		
		BombermanGame game = new BombermanGame(1000,"../layouts/niveau3.lay");
		ControllerBombermanGame controlleer = new ControllerBombermanGame(game);
		MyServer.setController(controlleer);

	}


}

