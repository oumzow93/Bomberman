package strategies;

import modele.Game;

public abstract class AstracteStrtegie {
	private Game game;
	
	

	public AstracteStrtegie(Game game) {
		super();
		this.game = game;
	}



	public Game getGame() {
		return game;
	}

}
