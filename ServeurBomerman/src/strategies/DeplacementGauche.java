package strategies;

import agent.Agent;
import agent.Position;
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
		if(this.getGame().isLegalMove(agent, AgentAction.MOVE_LEFT)) {
			int x= agent.getPosition().getX();
			int y= agent.getPosition().getY();
			agent.setPosition(new Position(x-1,y));
			
		}

		
	}

}
