package controleur;

import modele.Game;

;

public class ControllerBombermanGame implements AbstractController {
	
	private Game game;
	


	public ControllerBombermanGame(Game game) {
		super();
		this.game = game;
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	


	

}
