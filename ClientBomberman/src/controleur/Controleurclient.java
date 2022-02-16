package controleur;

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
		vue.getCommande().getPause().setEnabled(true);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(false);
		vue.getCommande().getPlay().setEnabled(false);
	}
	public static void step() {
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}
	public static void pause() {
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(true);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}
	
	public static void restart() {
		vue.getCommande().getPause().setEnabled(false);
		vue.getCommande().getRestart().setEnabled(false);
		vue.getCommande().getStep().setEnabled(true);
		vue.getCommande().getPlay().setEnabled(true);
	}




	public static ViewBombermanGame getVue() {
		return vue;
	}

}
