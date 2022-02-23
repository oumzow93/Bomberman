package serveur;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;



public class OutputServer extends Thread {
	private static Socket client;
	private static String requete= "";
	private static String requetePrecedent="";

	public OutputServer(Socket client) {
		super();
		setClient(client);
	}
	
	
	@Override
	public void run() {
		try {
			PrintWriter output = new PrintWriter(client.getOutputStream(),true);

			while(true) {
                 
				if(!requete.isEmpty() && !requete.equals(requetePrecedent) && !requete.equals("null")) {
					output.println(OutputServer.getRequete());
					OutputServer.setRequetePrecedent(getRequete());
				}
				output.flush();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	public Socket getClient() {
		return client;
	}

	public static void setClient(Socket clt) {
		client = clt;
	}


	public static String getRequete() {
		return requete;
	}


	public static void setRequete(String requete) {
		OutputServer.requete = requete;
	}


	public static String getRequetePrecedent() {
		return requetePrecedent;
	}


	public static void setRequetePrecedent(String requetePrecedent) {
		OutputServer.requetePrecedent = requetePrecedent;
	}
	
	
	

}
