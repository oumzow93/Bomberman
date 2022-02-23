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
	private static String requetteServeur="";
	private static String requetteClient;
	private static String requettPrecedent="";




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
					if(!message.equals("null")   &&  !message.equals(null) ) {

						if(message.contains("CONNEXION") && echange.getClient().equals(client) ) {
							
							
							MyServer.setRequetteServeur(MyServer.getRequetteServeur().replaceAll("UPDATE:", "DEMARAGE:")); 
							//sortie.println(MyServer.getRequetteServeur() );
							

						}else {
							if(message.contains("PLAY")) {
								controller.play();								
							}
							if(message.contains("PAUSE")) {
								controller.pause();
								
							}
							if(message.contains("STEP")) {
								controller.step();
								
							}
							if(message.contains("RESTART")) {
								controller.restart();
								
							}

							
						}
						while(!requetteServeur.equals(null) && !requetteServeur.equals(getRequettPrecedent()) ) {
							
							sortie.println(requetteServeur);
							System.out.println(requetteServeur);
							MyServer.setRequettPrecedent(requetteServeur);
							
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
					MyServer.setRequetteClient(reponse);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NullPointerException e) {
				System.out.println("DECONNEXION DU CLIENT :"+this.id_client+" avec IP: "+IPadr);
				clients.remove(this);
				--ID_CLIENT;

				if(ID_CLIENT==0) {
					controller.restart();
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
		}



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
		

		BombermanGame game = new BombermanGame(100,"../layouts/niveau1.lay");
		ControllerBombermanGame controlleer = new ControllerBombermanGame(game);
		
		
		new MyServer().start();
		MyServer.setController(controlleer);

	}

	public static String getRequetteClient() {
		return requetteClient;
	}

	public static void setRequetteClient(String requetteClient) {
		MyServer.requetteClient = requetteClient;
	}

	public static String getRequettPrecedent() {
		return requettPrecedent;
	}

	public static void setRequettPrecedent(String requettPrecedent) {
		MyServer.requettPrecedent = requettPrecedent;
	}


}

