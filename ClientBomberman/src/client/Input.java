package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Input  extends Thread{
	
	private Socket client;

	public Input(Socket client) {
		super();
		this.client = client;
	}
	
	
	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			
			while (true) {
				String reponse = input.readLine();
				if(!reponse.equals("exkit")) {
					System.out.println(reponse);
				}
				else {
					System.out.println("decconexion du serveur");
				}
				
			}
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		
	}

	public Socket getClient() {
		return client;
	}
	
	

}
