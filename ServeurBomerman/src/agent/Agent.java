package agent;

import strategies.IStrategie;

public abstract class  Agent {
	Position position;
	
	int direction;
	IStrategie strategie;

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

	

	
	//======================================== STRETEGIES

	public IStrategie getStrategie() {
		return strategie;
	}
 
	
	//===================== USE STRATEGY
	public void setStrategie(IStrategie strategie) {
		this.strategie = strategie;
		this.strategie.getAction(this);
	}
	
	//=========================================ACCESSEUR ET SUCCESSEUR

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	
	
	
	

}
