package controleur;


import java.util.ArrayList;


import agent.AgentBomberman;
import agent.AgentPNJ;
import agent.PNJVolant;
import agent.PNJ_Ennemi;
import agent.PNJ_Rajion;
import agent.Position;
import client.Output;
import vue.ViewBombermanGame;

public class Controleurclient {
	
	private static ViewBombermanGame vue;
	private static String turn="";
	private static ArrayList<AgentBomberman> listBomberman = new ArrayList<>();
	private static ArrayList<AgentPNJ>listPNJ = new ArrayList<>();
	private static boolean [][]start_breakable_walls;
	private static boolean [][]walls;
	private static int sizeX;
	private static int sizeY;
	
	

	public Controleurclient(ViewBombermanGame v) {
		super();
		vue = v;
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(false);
		
		
	}
	
	public static void play() {
		Output.setRequete("COMMANDE:PLAY:"+turn);
		vue.getCommande().getPause().setEnabled(true);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(false);
		vue.getCommande().getStep().setEnabled(false);
		
	}
	public static void step() {
		Output.setRequete("COMMANDE:STEP:"+turn);
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}
	public static void pause() {
		Output.setRequete("COMMANDE:PAUSE:"+turn);
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}
	
	public static void restart() {
		Output.setRequete("COMMANDE:RESTART:"+turn);
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(false);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}




	public static ViewBombermanGame getVue() {
		return vue;
	}

	public static String getTurn() {
		return turn;
	}

	public static void setTurn(String turn) {
		Controleurclient.turn = turn;
	}

	public static ArrayList<AgentBomberman> getListBomberman() {
		return listBomberman;
	}

	public static void setListBomberman(int x,int y, int direction,  int couleur) {
		
		AgentBomberman bomberman = new AgentBomberman(new Position(x,y));
		bomberman.setCouleur(couleur);
		bomberman.setDirection(direction);
		listBomberman.add(bomberman);
	}

	public static ArrayList<AgentPNJ> getListPNJ() {
		return listPNJ;
	}

	public static void setListPNJ(int x, int y, int direction, char type) {
		
	   switch(type) {
	   case 'V':
		   PNJVolant volant=  new PNJVolant(new Position(x,y));
		   listPNJ.add(volant);
		   break;
	   case 'R':
		   PNJ_Rajion rajion = new PNJ_Rajion(new Position(x,y));
		   listPNJ.add(rajion);
		   break;
	   case 'E':
		   PNJ_Ennemi enemi = new  PNJ_Ennemi(new Position(x,y));
		   listPNJ.add(enemi);
		   break;
	   }
	}

	public static boolean [][] getStart_breakable_walls() {
		return start_breakable_walls;
	}

	public static void setStart_breakable_walls(int x, int y) {
		start_breakable_walls[x][y]=true;
	}

	public boolean [][] getWalls() {
		return walls;
	}

	public static void setWalls(int x, int y) {
		walls[x][y]= true;
	}

	public static int getSizeX() {
		return sizeX;
	}

	public static void setSizeX(int X) {
		sizeX = X;
	}

	public static int getSizeY() {
		return sizeY;
	}

	public static void setSizeY(int Y) {
		sizeY = Y;
	}



}
