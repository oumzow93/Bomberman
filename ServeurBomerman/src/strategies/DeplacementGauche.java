package strategies;

import agent.Agent;
import modele.Game;
import utils.AgentAction;


public class DeplacementGauche extends AstracteStrtegie implements IStrategie {

	public DeplacementGauche(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Agent agent) {
		// TODO Auto-generated method stub
		this.getGame().moveAgent(agent, AgentAction.MOVE_LEFT ); 
			

		
	}

}
