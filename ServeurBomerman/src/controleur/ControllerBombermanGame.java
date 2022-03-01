package controleur;

import modele.BombermanGame;
import modele.Game;

;

public class ControllerBombermanGame implements AbstractController {
	
	private BombermanGame game;
	private String niveau;
	


	public ControllerBombermanGame() {
		super();
		this.niveau= "niveau3";
		String chemin = "../layouts/"+this.getNiveau()+".lay";
		this.game =   new BombermanGame (100,chemin);
		this.game.init();
		
	}

	@Override
	public void restart() {
		String chemin = "../layouts/"+this.getNiveau()+".lay";
		this.game =   new BombermanGame (100,chemin);
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

	public Game getGame() {
		return game;
	}

	public void setGame(BombermanGame game) {
		this.game = game;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
	


	

}
