package strategies;

import agent.Agent;

import modele.Game;
import utils.AgentAction;

public class  DeplacementDroite extends AstracteStrtegie  implements IStrategie{
	public DeplacementDroite(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Agent agent) {
		// TODO Auto-generated method stub
		this.getGame().moveAgent(agent, AgentAction.MOVE_RIGHT ); 
			

		
	}

}
