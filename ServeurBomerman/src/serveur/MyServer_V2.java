package serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class MyServer_V2 {
	
	public static  ArrayList<Socket> clientsConnectes = new ArrayList<>(); //Conteneur de client connecté 
	public static PrintWriter sortie;
	
	
	public static void send(Socket client) {
		OutputServer output = new OutputServer(client);
		Thread envoie = new Thread(output);
		envoie.start();
	}
	
	public static void main(String[] args) {
		
		try {
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(5000);
			
			
			while(true) {
				Socket client =server.accept();
				InputServer input = new InputServer(client);
				
				
				clientsConnectes.add(client);
				
				input.start();
				
		

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DEMARAGE DU SERVEUR");
		
	}

}
