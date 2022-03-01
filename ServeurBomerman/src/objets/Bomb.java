package objets;

import java.util.Timer;
import java.util.TimerTask;

import agent.Position;

public class Bomb {


	private Position poistion;
	private int statue;
	private int range;
	private int agent;
	
	
	public Bomb(Position poistion ) {
		super();
		this.setPoistion(poistion);
		this.statue=0;
		this.setRange(5);
		this.setAgent(0);
	}

	public int getStatue() {
		return statue;
	}

	public void setStatue(int statue) {
		this.statue = statue;
	}


	
	public void explosion() {
		switch(this.statue) {
		case 0: this.setStatue(1); break;
		case 1: this.setStatue(2); break;
		case 2: this.setStatue(3); break;
		case 3: this.setStatue(4); break;
		case 4: this.setStatue(5); break;
		}
	}
	
	//===========================================EXPLOSION
	public void exploision(long time) {
		
		
		if(this.statue==0) {
			
			
			
			Timer t = new Timer();
		    TimerTask task = new TimerTask() {
		        int j=1;
		        public void run() {
		        	try {
		        		setStatue(j);
		        		
		        	}catch(java.lang.IndexOutOfBoundsException e) {} 
		          
		         // for
		          try {
		            Thread.sleep(time);
		          } catch (InterruptedException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		          }
		        	  
		          if(j==5) {
		        	  t.cancel(); 
		          }
		            
		           
		          j=j+1;
		        }
		      };
		      t.schedule(task,time,time);
			
		}
		
	}
	
	public boolean Aexploser() {
		return this.statue==5;
		
	}
	
	

	public Position getPosition() {
		return poistion;
	}

	public void setPoistion(Position poistion) {
		this.poistion = poistion;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getAgent() {
		return agent;
	}

	public void setAgent(int agent) {
		this.agent = agent;
	}
	
	@Override
	public String toString() {
		return  poistion + ","+this.statue+","+this.range ;
	}
	

}
