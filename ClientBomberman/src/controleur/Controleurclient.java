package controleur;


import client.Output;
import vue.ViewBombermanGame;

public class Controleurclient {
	
	private static ViewBombermanGame vue;
	public static int compteur=0;
	private static String turn;
	
	

	public Controleurclient(ViewBombermanGame v) {
		super();
		vue = v;
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(false);
		vue.getCommande().getPlay().setEnabled(false);
		
	}
	
	public static void play() {
		Output.setRequete("COMMANDE;PLAY;"+turn);
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(false);
		
	}
	public static void step() {
		Output.setRequete("COMMANDE;STEP;"+turn);
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(false);
	}
	public static void pause() {
		Output.setRequete("COMMANDE;PAUSE;"+turn);
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(false);
	}
	
	public static void restart() {
		Output.setRequete("COMMANDE;RESTART;"+turn);
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(false);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(false);
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



}
