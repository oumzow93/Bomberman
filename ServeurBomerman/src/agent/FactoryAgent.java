package agent;

public class FactoryAgent {
	
	public static Agent creerBombermant(Position position) {
		
		return  new AgentBomberman(position);
		
	}
	
	public static Agent creerPNG(Position position, char type) { 
		if( type=='V')
			return new PNJVolant(position);
		 if(type=='E')
			 return new  PNJ_Ennemi(position);
		 if(type=='R')
			 return new  PNJ_Rajion(position);
		
		return null;
	}
	
	

}
