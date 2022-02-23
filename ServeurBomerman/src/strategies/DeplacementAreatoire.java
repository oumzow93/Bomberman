package strategies;

import java.util.Random;

import agent.Agent;
import modele.Game;
import utils.AgentAction;

//=======================PATTERN STRATEGIE
public class DeplacementAreatoire extends  AstracteStrtegie  implements IStrategie{
	

	public DeplacementAreatoire(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Agent agent ) {
		// TODO Auto-generated method stub
		Random aleatoire = new Random();
		int direction = aleatoire.nextInt(5);
		agent.setDirection(direction);

	    switch(direction) {
	    case 0: 
	    	this.getGame().putBomb(agent);
	    case 1:
	    	this.getGame().moveAgent(agent, AgentAction.MOVE_RIGHT); break;
	    case 2:
	    	this.getGame().moveAgent(agent, AgentAction.MOVE_LEFT); break;
	    case 3:
	    	this.getGame().moveAgent(agent, AgentAction.MOVE_UP); break;
	    case 4:
	    	this.getGame().moveAgent(agent, AgentAction.MOVE_DOWN); break;
	    	default:
	    		 break;
	    }
		
	}
	
}
