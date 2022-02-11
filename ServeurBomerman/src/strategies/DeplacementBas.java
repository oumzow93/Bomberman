package strategies;

import agent.Agent;
import agent.Position;
import modele.Game;
import utils.AgentAction;


public class DeplacementBas extends AstracteStrtegie implements IStrategie {
	
	public DeplacementBas(Game game) {
		super(game);
		
	}

	@Override
	public void getAction(Agent agent) {
		if(this.getGame().isLegalMove(agent, AgentAction.MOVE_UP)) {
			int x= agent.getPosition().getX();
			int y= agent.getPosition().getY();
			agent.setPosition(new Position(x,y+1));
			
		}
	
	}

}
