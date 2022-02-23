package strategies;

import agent.Agent;
import agent.AgentBomberman;
import agent.PNJVolant;
import modele.Game;

public class StrategieBird implements IStrategie {
	Game game;


	public  StrategieBird (Game game) {
		super();
		this.game = game;
	}

	@Override
	public void getAction(Agent agent) {
		// TODO Auto-generated method stub

		
		for(AgentBomberman agb: this.game.getListBomberman()) {
			if(this.estDansleRayon(agb, agent, PNJVolant.RayonAction)) {
				agent.setStrategie(new DeplacementAreatoire(this.game));
			}else {
				agent.setDirection(0);
			}
		}
		
		
		
		
	}
	
	public boolean estDansleRayon(Agent a1, Agent a2,  int rayon) {
		int x1= a1.getPosition().getX()-rayon;
		int x2= a1.getPosition().getX()+rayon;

		int y1= a1.getPosition().getY()-rayon;
		int y2= a1.getPosition().getY()+rayon;

		boolean result=false;

		for(int i=x1;i<x2;i++) {
			for(int j=y1;j<y2;j++) {
				if( (i>=0 && i<this.game.getCarrte().getSizeX() )&& (j>0 && j<this.game.getCarrte().getSizeY() &&
						a2.getPosition().getX()==i && a2.getPosition().getY()==j)	) {
					result=true;


				}
			}

		}

		return result;

	}

}
