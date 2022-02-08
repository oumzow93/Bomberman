package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Output extends Thread {
	private Socket client;
	private static String requete= "";
	private static String requetePrecedent="";
	public Output(Socket client) {
		super();
		this.client = client;
		//this.setRequete("");
	}
	
	@Override
	public void run() {
		try {
			PrintWriter output = new PrintWriter(this.client.getOutputStream(),true);
		
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			while(true) {

				while(!requete.equals(null) && !requete.equals(requetePrecedent) && !requete.equals("null")) {
					output.println(Output.getRequete());
					scanner.nextLine();
					Output.setRequete(requete);
					//Output.setRequetePrecedent(requete);
					
				}
				
	
				output.flush();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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



	

}
