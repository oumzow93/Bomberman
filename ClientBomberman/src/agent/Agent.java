package agent;



public abstract class  Agent {
	Position position;
	
	int direction;
	

	public Agent(Position poistion) {
		super();
		this.position = poistion;
		
		this.direction=0;
	}
	
	 public abstract char  typeAgent();

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position.setX(position.getX()); 
		this.position.setY(position.getY());
	}

	

	

 
	
	//===================== USE STRATEGY
	
	//=========================================ACCESSEUR ET SUCCESSEUR

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	
	
	

}
