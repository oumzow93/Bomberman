package agent;

import java.util.Timer;
import java.util.TimerTask;

public class AgentBomberman  extends Agent{
	private int couleur;
	static int compteur=0;
	private boolean estInvincible;
	private boolean peutPoserBomb;
	private boolean estMalade;
	private int rangeBom;
	public  static int nombre_de_vies=5;

	public AgentBomberman(Position poistion) {
		super(poistion);
		// TODO Auto-generated constructor stub
		this.setCouleur(compteur++);
		this.estInvincible=false;
		this.estMalade=false;
		this.peutPoserBomb=true;
		this.rangeBom=1;
	}

	@Override
	public char typeAgent() {
		// TODO Auto-generated method stub
		return 'B';
	}

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}
	
	public static void  setIdCouleur() {
		compteur=0;
	}

	public boolean isEstMalade() {
		return estMalade;
	}

	//=========================RENDRE UN OBJET MALDE PENDANT UN TEMPS 
	public void setEstMalade(long time) {
		Timer t = new Timer();
	    TimerTask task = new TimerTask() {
	        int j=1;
	        public void run() {
	          
	        	estMalade=true;
	        	peutPoserBomb= false;
	        	
	          try {
	            Thread.sleep(time);
	          } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	          }
	        	  
	          if(j==10) {
	        	  t.cancel(); 
		        	estMalade=false;
		        	peutPoserBomb= true;
	          }
	            
	           
	          j=j+1;
	        }
	      };
	      t.schedule(task,time,time);
	}

	public boolean isInvincible() {
		return estInvincible;
	}

	public void setinvincible(long time) {
		
		Timer t = new Timer();
	    TimerTask task = new TimerTask() {
	        int j=1;
	        public void run() {
	          
	        	estInvincible=true;
	          try {
	            Thread.sleep(time);
	          } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	          }
	        	  
	          if(j==10) {
	        	  t.cancel(); 
	        	  estInvincible=false;
	          }
	            
	           
	          j=j+1;
	        }
	      };
	      t.schedule(task,time,time);
		

		
	}

	public boolean isPeutPoserBomb() {
		return peutPoserBomb;
	}

	public void setPeutPoserBomb(boolean peutPoserBomb) {
		this.peutPoserBomb = peutPoserBomb;
	}

	public int getRangeBom() {
		return rangeBom;
	}


	public void AugmentRange() {
		this.rangeBom++;
	}
	public void DimunueRnage() {
		if(this.rangeBom>1) {
			this.rangeBom--;
		}
	}

	@Override
	public String toString() {
		return  super.toString()+"," + couleur ;
	}


}
