package strategies;

import agent.Agent;
import modele.Game;
import serveur.MyServer;

public class DeplacementClavier extends  AstracteStrtegie  implements IStrategie {

	public DeplacementClavier(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getAction(Agent agent) {
		if(MyServer.getRequetteClient().contains("DROITE")) {
			new  DeplacementDroite(this.getGame());
			
		}
		if(MyServer.getRequetteClient().contains("GAUCHE")) {
			new  DeplacementGauche(this.getGame());
		}
		if(MyServer.getRequetteClient().contains("HAUT")) {
			new  DeplacementHaut(this.getGame());
		}
		if(MyServer.getRequetteClient().contains("BAS")) {
			new  DeplacementBas(this.getGame());
			
		}
	}

}
