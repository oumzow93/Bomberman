package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import controleur.AbstractController;
import controleur.ControllerBombermanGame;






public class MyServer extends Thread {
	private static ArrayList<Echange> clients = new ArrayList<>();
	private static int ID_CLIENT=0;
	private static AbstractController controller;
	private static String requetteServeur="";
	private static String requetteClient="";
	private static String requettPrecedent="";
	 private static ArrayList<String> utlisateur = new ArrayList<>();




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

	public static boolean  authentification(String pseudo, String password) {
	 
        try {
        	
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/WebBomberman/APIConnexion?pseudo="+pseudo+"&password="+password ))
                    .GET()
                    .header("Accept", "application.json")
                    .build();
           

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            
            if(!response.body().isEmpty()) {
            	return true;
            }

            
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
	}
	public static void sendPartie(String dateDebut ,String dateFin, String mode,String score,String resultat) {
		String url = "http://localhost:8080/WebBomberman/SaveParite?debut="+dateDebut+"&fin="+dateFin+"&mode="+mode+"&score="+score+"&resultat="+resultat; 
		
        try {
        	
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url ))
                    .GET()
                    .header("Accept", "application.json")
                    .build();
           

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
                       
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
		
	}
	
	public static void sendHistorique(String pseudo, String debut) {
		String url = "http://localhost:8080/WebBomberman/SaveHistorique?pseudo="+pseudo+"&debut="+debut;
	       try {
	        	
	            HttpClient client = HttpClient.newHttpClient();

	            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url ))
	                    .GET()
	                    .header("Accept", "application.json")
	                    .build();
	           

	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
	            System.out.println(response);
	                       
	        }catch (IOException | InterruptedException e) {
	            e.printStackTrace();
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
							String[] connexion= message.split(":");
							String pseudo= connexion[1];
							String password= connexion[2];
							
							if(MyServer.authentification(pseudo, password)) {
								
								MyServer.setRequetteServeur(MyServer.getRequetteServeur().replaceAll("UPDATE:", "DEMARAGE:")); 
								sortie.println(MyServer.getRequetteServeur());
								MyServer.utlisateur.add(pseudo);
							}else {
															
								sortie.println("ECHEC_AUTHENTIFICATION");
							}
							

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
							MyServer.setRequetteClient(message+":"+this.id_client);
							
						}else if(message.startsWith("NIVEAU")) {
							String []niveau = message.split(":");
							controller.setNiveau(niveau[1]);
							controller.changeNivau(); 
							
							MyServer.setRequetteServeur(MyServer.getRequetteServeur().replaceAll("UPDATE:", "CHANGE_NIVEAU:")); 
							sortie.println(MyServer.getRequetteServeur());
							
							
						}
						else if (message.startsWith("SPEED")) {
							String [] speed = message.split(":");
							controller.setSpeed(Double.parseDouble(speed[1]));
							
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

					//System.out.println(reponse);
					broadcast(reponse,client);

					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NullPointerException e) {
				System.out.println("DECONNEXION DU CLIENT :"+this.id_client+" avec IP: "+IPadr);
				clients.remove(this);
				--ID_CLIENT;
				if(!MyServer.utlisateur.isEmpty()) {
					MyServer.utlisateur.remove(ID_CLIENT);

				}
				
				if(ID_CLIENT==0) {
					controller.restart();
					MyServer.utlisateur.clear();
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


		
		ControllerBombermanGame controlleer = new ControllerBombermanGame();


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
	public static ArrayList<String> getUtlisateur() {
		return utlisateur;
	}
	public static void setUtlisateur(ArrayList<String> utlisateur) {
		MyServer.utlisateur = utlisateur;
	}


}

