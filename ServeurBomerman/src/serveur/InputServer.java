package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import controleur.ControllerBombermanGame;
import modele.BombermanGame;

public class InputServer extends Thread {
	private static Socket client;
	private static BombermanGame game;
	private static ControllerBombermanGame controller;
	
	
	
	public InputServer(Socket client) {
		InputServer.client=client;
	}
	
	
	public void run() {

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

			while (true) {
				String reponse;
				while((reponse=input.readLine())!=null) {
					System.out.println(reponse);
					gestionRequetteClient(reponse);

				}

			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}

	}
	
	
	public void gestionRequetteClient(String donnee) {
		if(donnee.startsWith("CONNEXION") ) {
			String [] user = donnee.split(":");
			System.out.println("User: PSEUDO= "+user[1]+ "PASSWORD= "+user[2]);
			
			
			OutputServer.setRequete(OutputServer.getRequete().replaceAll("UPDATE:", "DEMARAGE:"));
			InputServer.game= new   BombermanGame(100,"../layouts/niveau1.lay");
			InputServer.controller= new  ControllerBombermanGame(InputServer.game);
			
			MyServer_V2.send(getClient());
		}else if(donnee.startsWith("COMMANDE") ) {
			String [] commande = donnee.split(":");
			switch(commande[1]) {
			case "PLAY":InputServer.controller.play(); break;
			case "PAUSE":InputServer.controller.pause(); break;
			case "STEP":InputServer.controller.step();; break;
			case "RESTART":InputServer.controller.restart(); break;
			default: break;
			}
		}
		
	}
	
	
	
	public static Socket getClient() {
		return client;
	}
	public static void setClient(Socket client) {
		InputServer.client = client;
	}
	public static BombermanGame getGame() {
		return game;
	}
	public static void setGame(BombermanGame game) {
		InputServer.game = game;
	}
	public static ControllerBombermanGame getController() {
		return controller;
	}
	public static void setController(ControllerBombermanGame controller) {
		InputServer.controller = controller;
	}
	
	
	
	

}
