package controleur;

import client.Output;
import vue.ViewBombermanGame;

public class Controleurclient {
	
	private static ViewBombermanGame vue;
	
	
	

	public Controleurclient(ViewBombermanGame v) {
		super();
		vue = v;
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(false);
		
	}
	
	public static void play() {
		Output.setRequete("COMMANDE;PLAY");
		vue.getCommande().getPause().setEnabled(true);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(false);
		vue.getCommande().getPlay().setEnabled(false);
	}
	public static void step() {
		Output.setRequete("COMMANDE;STEP");
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}
	public static void pause() {
		Output.setRequete("COMMANDE;PAUSE");
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}
	
	public static void restart() {
		Output.setRequete("COMMANDE;RESTART");
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(false);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}




	public static ViewBombermanGame getVue() {
		return vue;
	}

}
