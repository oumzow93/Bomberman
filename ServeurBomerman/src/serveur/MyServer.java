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
	private static ArrayList<Echange> clients = new ArrayList<>();
	private static int ID_CLIENT=0;
	private static AbstractController controller;
	private static String requetteServeur="";
	private static String requetteClient="";
	private static String requettPrecedent="";




	public MyServer() {


	}
	public static void send() {
		for (Echange echange:clients) {
			try {
				PrintWriter sortie= new PrintWriter (echange.getClient().getOutputStream(),true);
				sortie.println(MyServer.requetteServeur);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
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
				clients.add(ech);
				ech.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//=======================================CLASSE POUR LA GESTION DES ENTRÉES ET SORTIES===============
	public static class Echange extends Thread{
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
					if(!message.equals("null")   &&  !message.equals(null)  ) {

						if(message.startsWith("CONNEXION") && echange.getClient().equals(client) ) {


							MyServer.setRequetteServeur(MyServer.getRequetteServeur().replaceAll("UPDATE:", "DEMARAGE:")); 
							sortie.println(MyServer.getRequetteServeur() );


						}else if(message.startsWith("COMMANDE")) {
							String[]commande = message.split(":");

							switch(commande[1]) {
							case "PLAY":controller.play();	break;
							case "PAUSE":controller.pause(); break;
							case "STEP":controller.step(); break;
							case "RESTART": controller.restart(); break;

							}

							//MyServer.setRequettPrecedent(requetteServeur);
						}else if(message.startsWith("DEPLACEMENT")) {
							MyServer.setRequetteServeur("DEPLACEMENT"); 
							MyServer.setRequetteClient(message);
						}




						sortie.flush();

					}


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

					System.out.println(reponse);
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

