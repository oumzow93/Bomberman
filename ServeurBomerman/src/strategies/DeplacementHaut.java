package strategies;

import agent.Agent;
import agent.Position;
import modele.Game;
import utils.AgentAction;

public class DeplacementHaut extends AstracteStrtegie implements IStrategie {
	
	public DeplacementHaut(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Agent agent) {
		// TODO Auto-generated method stub
		if(this.getGame().isLegalMove(agent, AgentAction.MOVE_DOWN)) {
			int x= agent.getPosition().getX();
			int y= agent.getPosition().getY();
			agent.setPosition(new Position(x,y-1));
			
		}

		
	}

}
