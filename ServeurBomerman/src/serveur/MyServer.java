package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ListIterator;



public class MyServer extends Thread {
	private ArrayList<Echange> clients = new ArrayList<>();
	private int ID_CLIENT;



	public MyServer() {
		this.ID_CLIENT=0;
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


			ListIterator<Echange>iter = clients.listIterator();

			while(iter.hasNext()) {
				Echange echange= iter.next();
				try {
					PrintWriter sortie= new PrintWriter (echange.getClient().getOutputStream(),true);
					if(!message.equals("null") || !message.equals(null) ) {
						System.out.println(message);
						sortie.println(message);
					}
					else {
						System.out.println("DECONNEXION DE CLIENT: "+this.id_client);
						iter.remove();
						
					}


					sortie.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}catch (NullPointerException e) {
					//System.out.println("Problème : " + e.getMessage());
				}
			}



		}
		@Override
		public void run() {
			try {
				BufferedReader  entree = new BufferedReader(new InputStreamReader(client.getInputStream()));


				String IPadr= client.getRemoteSocketAddress().toString();
				System.out.println("CONNEXION DU CLIENT : "+this.id_client+" avec IP: "+IPadr);


				while(true) {
					//=================SORTIE
					String reponse =  entree.readLine();
					broadcast(reponse);



				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	//=========================================================MAIN============================================
	public static void main(String[] args) {
		new MyServer().start();

	}

}
