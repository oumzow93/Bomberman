package client;

import java.io.IOException;
import java.net.Socket;

import vue.Authentification;



public class MyClient {
	private static Authentification authen;
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			Socket client = new Socket("localhost",5000);
			
			Input input   = new Input(client);
			Output output = new Output(client);
			
			
			output.start();
			input.start();
			
			authen =new Authentification();
			
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Authentification getAuthen() {
		return authen;
	}


}
