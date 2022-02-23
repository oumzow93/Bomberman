package strategies;

import agent.Agent;

import modele.Game;
import utils.AgentAction;


public class DeplacementBas extends AstracteStrtegie implements IStrategie {
	
	public DeplacementBas(Game game) {
		super(game);
		
	}

	@Override
	public void getAction(Agent agent) {
		// TODO Auto-generated method stub
		this.getGame().moveAgent(agent, AgentAction.MOVE_UP ); 
			

		
	}

}
