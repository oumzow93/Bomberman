package controleur;

import modele.Game;

public interface AbstractController {
	
	public abstract void restart();
	public abstract void step();
	public abstract void play();
	public abstract void pause();
	public abstract void  setSpeed(double speed);
	
	public abstract  String getNiveau();
	public abstract  void setNiveau(String niveau);
	public abstract void changeNivau();
	
	
	public abstract Game getGame() ;

	

	
	
	

}
