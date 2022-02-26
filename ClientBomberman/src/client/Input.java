package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import controleur.Controleurclient;
import vue.ViewBombermanGame;




public class Input  extends Thread{

	private Socket client;
	private  ViewBombermanGame  viewGame;
	private static String reponseprecedent="";

	public Input(Socket client) {
		super();
		this.client = client;

	}


	@Override
	public void run() {

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));

			while (true) {
				String reponse ;

				while((reponse=input.readLine())!=null) {
					gestionRequetteServeur(reponse);

				}

			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			//e.printStackTrace();
		}

	}

	public  void gestionRequetteServeur(String requette) {
		if(requette.startsWith("DEMARAGE")) {
			String []infoRequette = requette.split(":");
			Controleurclient.setTurn(infoRequette[1]);
			this.recupTilleCarte(infoRequette[2]);
			this.recupBomberman(infoRequette[3]);
			this.recupPnj(infoRequette[4]);
			this.recupBreakbleWalls(infoRequette[5]);
			this.recupWalls(infoRequette[6]);

			this.viewGame= new ViewBombermanGame();
			new Controleurclient(this.viewGame);
			this.viewGame.afficher();

		}else {
			if(requette.startsWith("UPDATE")) {
				String []infoRequette = requette.split(":");
				Controleurclient.setTurn(infoRequette[1]);
				this.recupTilleCarte(infoRequette[2]);
				this.recupBomberman(infoRequette[3]);
				this.recupPnj(infoRequette[4]);
				this.recupBreakbleWalls(infoRequette[5]);
				this.recupBomb(infoRequette[7]);
				this.recupItem(infoRequette[8]);


				this.viewGame.actualiser();
			}

		}

	}

	public void recupTilleCarte(String taille) {

		if(!taille.isEmpty() && taille.length()>=2) {
			String []tailleRecup = taille.split(";");
			String []mataille  = tailleRecup[1].split("&");
			if(mataille.length==2) {
				Controleurclient.setSizeX(Integer.parseInt(mataille[0]) );
				Controleurclient.setSizeY(Integer.parseInt(mataille[1]) );
			}

		}else{
			System.out.println("taille : donnees manquante");
		}
	}
	public void recupBomberman(String donnee) {
		Controleurclient.getListBomberman().clear();
		String bomberman[] = donnee.split(";");

		if(bomberman.length>1 ) {
			if(bomberman[1].contains("&") ) {
				String bomber[] = bomberman[1].split("&");
				for( int i=0;i <bomber.length;i++) {
					String attribut[] = bomber[i].split(",");
					int x= Integer.parseInt(attribut[0]);
					int y= Integer.parseInt(attribut[1]);
					int d= Integer.parseInt(attribut[2]);
					int c= Integer.parseInt(attribut[4]);
					Controleurclient.setListBomberman(x, y, d, c);

				}
			}else {

				String attribut[] = bomberman[1].split(",");
				int x= Integer.parseInt(attribut[0]);
				int y= Integer.parseInt(attribut[1]);
				int d= Integer.parseInt(attribut[2]);
				int c= Integer.parseInt(attribut[4]);
				Controleurclient.setListBomberman(x, y, d, c);
			}


		}else {
			System.out.println("PAS DE BOMBERMAN");
		}




	}
	public void recupPnj(String donnee) {
		Controleurclient.getListPNJ().clear();
		String pnj[] = donnee.split(";");

		if(pnj.length>1 ) {
			if(pnj[1].contains("&") ) {
				String mechan[] = pnj[1].split("&");
				for( int i=0;i <mechan.length;i++) {
					String attribut[] = mechan[i].split(",");
					int x= Integer.parseInt(attribut[0]);
					int y= Integer.parseInt(attribut[1]);
					int d= Integer.parseInt(attribut[2]);
					char t=  attribut[3].charAt(0);
					Controleurclient.setListPNJ(x,y, d, t);

				}
			}else {

				String attribut[] = pnj[1].split(",");
				int x= Integer.parseInt(attribut[0]);
				int y= Integer.parseInt(attribut[1]);
				int d= Integer.parseInt(attribut[2]);
				char t=  attribut[3].charAt(0);
				Controleurclient.setListPNJ(x,y, d, t);
			}


		}else {
			System.out.println("PAS D'AGENT PNJ");

		}



	}
	public void recupBreakbleWalls(String donnee) {
		
		boolean [][]start_breakable_walls = new boolean[Controleurclient.getSizeX()][Controleurclient.getSizeY()];
		for(int i=0;i< Controleurclient.getSizeX();i++) {
			for(int j=0; j<Controleurclient.getSizeY();j++) {
				start_breakable_walls[i][j]=false;
				
			}
		}
		
		Controleurclient.setStart_breakable_walls(start_breakable_walls);
		
		String breakablewall[] = donnee.split(";");

		if(breakablewall.length>1) {

			String coordonnees[]=breakablewall[1].split("&");
			for(int i=0; i<coordonnees.length;i++) {
				String []coordonnee =coordonnees[i].split(",");
				int x= Integer.parseInt(coordonnee[0]);
				int y= Integer.parseInt(coordonnee[1]);

				start_breakable_walls[x][y]=true;
			}

			Controleurclient.setStart_breakable_walls(start_breakable_walls);

		}



	}
	public void recupWalls(String donnee) {
		boolean [][]walls = new boolean[Controleurclient.getSizeX()][Controleurclient.getSizeY()];
		for(int i=0;i< Controleurclient.getSizeX();i++) {
			for(int j=0; j<Controleurclient.getSizeY();j++) {
				walls[i][j]=false;
			}
		}
		String wall[] = donnee.split(";");
		if(wall.length>1) {
			String coordonnees[]=wall[1].split("&");
			for(int i=0; i<coordonnees.length;i++) {

				String []coordonnee =coordonnees[i].split(",");
				int x= Integer.parseInt(coordonnee[0]);
				int y= Integer.parseInt(coordonnee[1]);

				walls[x][y]=true;
			}

			Controleurclient.setWalls(walls);

		}



	}

	public void recupBomb(String  donnee) {
		Controleurclient.getBombs().clear();
		String []bomb = donnee.split(";");
		System.out.println(donnee);

		if(bomb.length>1) {
			if(bomb[1].contains("&")) {
				String[] lesbomb = bomb[1].split("&");
				for(int i=0; i<lesbomb.length;i++) {
					String[]attribut= lesbomb[i].split(",");
					int x= Integer.parseInt(attribut[0]);
					int y= Integer.parseInt(attribut[1]);
					int s= Integer.parseInt(attribut[2]);
					int r= Integer.parseInt(attribut[3]);
					Controleurclient.setBombs(x, y,s,r);
				}
			}else {
				String[]attribut= bomb[1].split(",");
				int x= Integer.parseInt(attribut[0]);
				int y= Integer.parseInt(attribut[1]);
				int s= Integer.parseInt(attribut[2]);
				int r= Integer.parseInt(attribut[3]);
<<<<<<< Updated upstream
				Controleurclient.setBombs(x, y,s, r);

=======
				Controleurclient.setBombs(x,y,s,r);
				
>>>>>>> Stashed changes
			}

		}else {
			//System.out.println("PAS DE BOMB");
		}


	}
	public void recupItem(String  donnee) {
		Controleurclient.getItems().clear();
		String []item = donnee.split(";");
		System.out.println(item);

		if(item.length>1) {
			if(item[1].contains("&")) {
				String[] lesitems = item[1].split("&");
				for(int i=0; i<lesitems.length;i++) {
					String[]attribut= lesitems[i].split(",");
					int x= Integer.parseInt(attribut[0]);
					int y= Integer.parseInt(attribut[1]);
					int t= Integer.parseInt(attribut[2]);

					Controleurclient.setItems(x,y,t);
				}
			}else {
				String[]attribut= item[1].split(",");
				int x= Integer.parseInt(attribut[0]);
				int y= Integer.parseInt(attribut[1]);
				int t= Integer.parseInt(attribut[2]);

				Controleurclient.setItems(x,y,t);

			}

		}else {
			//System.out.println("PAS DE BOMB");
		}
	}




	public Socket getClient() {
		return client;
	}


	public ViewBombermanGame getViewGame() {
		return viewGame;
	}


	public static String getReponseprecedent() {
		return reponseprecedent;
	}


	public static void setReponseprecedent(String reponseprecedent) {
		Input.reponseprecedent = reponseprecedent;
	}






}
