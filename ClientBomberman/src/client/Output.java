package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class Output extends Thread {
	private Socket client;
	private static String requete= "";
	private static String requetePrecedent="";
	private int compteur=0;
	private int id_client;
	
	public Output(Socket client) {
		super();
		this.client = client;
		this.setId_client(++compteur);

	}

	@Override
	public void run() {
		try {
			PrintWriter output = new PrintWriter(this.client.getOutputStream(),true);

			while(true) {
                 
				if(!requete.isEmpty() && !requete.equals(requetePrecedent) && !requete.equals("null")) {
					
					output.println(Output.getRequete());
					Output.setRequetePrecedent(getRequete());
				}
				output.flush();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	public static String getRequete() {
		return requete;
	}
	public static void setRequete(String requete) {
		Output.requete = requete;
	}


	public Socket getCleint() {
		return client;
	}



	public static String getRequetePrecedent() {
		return requetePrecedent;
	}

	public static void setRequetePrecedent(String requtePrecedent) {
		Output.requetePrecedent = requtePrecedent;
	}

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}





}
