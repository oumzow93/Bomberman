package strategies;

import agent.Agent;
import modele.Game;
import serveur.MyServer;
import utils.AgentAction;

public class DeplacementClavier extends  AstracteStrtegie  implements IStrategie {

	public DeplacementClavier(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Agent agent) {
		if(MyServer.getRequetteClient().startsWith("DEPLACEMENT")) {
			String [] clavier = MyServer.getRequetteClient().split(":");
			switch(clavier[1]) {
			case "HAUT": 
				this.getGame().moveAgent(agent, AgentAction.MOVE_DOWN); break;
			case "BAS":
				this.getGame().moveAgent(agent, AgentAction.MOVE_UP); break;
			case "GAUCHE":
				this.getGame().moveAgent(agent, AgentAction.MOVE_LEFT); break;
			case "DROITE":
				this.getGame().moveAgent(agent, AgentAction.MOVE_RIGHT); break;
			case "PUT_BOMB":
				this.getGame().putBomb(agent);
				default:break;
			}
			MyServer.setRequetteClient("stop");
			
		}
			

	
	}

}
