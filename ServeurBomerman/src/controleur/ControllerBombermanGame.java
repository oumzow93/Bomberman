package controleur;

import modele.BombermanGame;
import modele.Game;
import serveur.MyServer;

;

public class ControllerBombermanGame implements AbstractController {
	
	private Game game;
	private String niveau;
	


	public ControllerBombermanGame() {
		super();
		this.niveau= "niveau2";
		String chemin = "../layouts/"+this.getNiveau()+".lay";
		this.game =   new BombermanGame (50,chemin);
		this.game.init();
		
	}

	@Override
	public void restart() {

		this.game.init();
		this.game.pause();
		
		
	}

	@Override
	public void step() {
		this.game.step();
	
	}

	@Override
	public void play() {
		
		this.game.launch();
		
		
		
	}
	@Override
	public void pause() {
		this.game.pause();
		
	}



	@Override
	public void setSpeed(double speed){
		// TODO Auto-generated method stub
		this.game.setTime((long)speed);
		
	}
	
	@Override
	
	
	
	
	public void changeNivau() {
		// TODO Auto-generated method stub
		String chemin = "../layouts/"+this.getNiveau()+".lay";
		this.game =   new BombermanGame (100,chemin);
		this.game.SetTurn(0);
		this.game.pause();
		this.game.initializeGame();
		MyServer.setRequetteServeur(this.game.donneMiseAjour());
		
		
	}
    
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}


	


	

}
