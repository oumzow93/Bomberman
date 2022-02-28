package modele;


import java.util.ArrayList;

import agent.Agent;
import agent.AgentBomberman;
import agent.AgentPNJ;
import objets.Bomb;
import objets.Item;
import serveur.MyServer;
import utils.AgentAction;


public abstract class Game implements Runnable {
	private int turn;
	private int maxturn;
	private boolean isRunning;
	private Thread thread;
	private long time;
	
	
	
	
	public Game(int maxturn) {
		this.turn=0;
		this.maxturn=maxturn;
		this.isRunning=false;
		this.time=1000;
		
	}
	//============================= METHODE ABSTRACT
	public  abstract void  initializeGame();
	public abstract void  takeTurn();
	public abstract boolean gameContinue();
	public abstract void gameOver();
	
	
	public abstract ArrayList<AgentBomberman> getListBomberman();
	public abstract ArrayList<AgentPNJ> getListPNJ();
	public abstract InputMap getCarrte();
	public abstract ArrayList<Bomb> getBombs();
	
	
	public abstract boolean isLegalMove(Agent agent, AgentAction  action );
	public abstract void moveAgent(Agent agent, AgentAction  action);
	public abstract void  putBomb(Agent agent);
	public abstract  ArrayList<Item> getItems();
	
	
	public abstract boolean [][] getStart_breakable_walls() ;
	public abstract void setStart_breakable_walls(boolean [][] start_breakable_walls) ;
	
	public abstract boolean [][] getWalls(); 
	public abstract void setWalls(boolean [][] walls);
	
	
	public abstract int getSizeX() ;
	


	public abstract void setSizeX(int sizeX) ;



	public abstract int getSizeY() ;



	public abstract void setSizeY(int sizeY) ;
	

	
	
	public abstract void setFileName(String fileName);
	public abstract  int getScore() ;
	public abstract int getNbVies() ;
	
	public abstract boolean isVictoire() ;
	public abstract boolean isDefaite(); 
		
	
		


   //=================================LES ACCESSEUR
	public int getTurn() {
		return turn;
	}
	public void SetTurn(int turn) {
		this.turn=turn;
	}

	public int getMaxturn() {
		return maxturn;
	}

	public boolean isRunning() {
		return isRunning;
	}
	public Thread getThread() {
		return thread;
	}
	public long getTime() {
		return this.time;
	}
	public void setTime (long time)
	{
		this.time= time;
		MyServer.setRequetteServeur(this.donneMiseAjour());
		
	}
	
	//  LA METHODE INIT PERMET D'INITIALISER LE JEU
	


	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public void init() {
		this.turn=0;
		this.isRunning=true;
		this.initializeGame();
		MyServer.setRequetteServeur(this.donneMiseAjour());
		MyServer.send();
		
		
	}
	public void step() {
		//this.turn++;
		if(this.gameContinue() && this.turn<this.maxturn) {
			this.turn++;
			this.takeTurn();
			
		}else {
			this.isRunning=false;
			
		}
		//System.out.println("pas");
		MyServer.setRequetteServeur(this.donneMiseAjour());
		MyServer.send();
		
		
	}
	public void pause() {
		this.isRunning=false;
		MyServer.setRequetteServeur(this.donneMiseAjour());
	}
	public void run() {
		do {
			this.step();
			try {
				Thread.sleep(this.time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MyServer.setRequetteServeur(this.donneMiseAjour());
		}while(this.isRunning);
		
	}
	public void launch() {
		this.isRunning=true;
		this.thread= new Thread(this);
		this.thread.start();
		MyServer.setRequetteServeur(this.donneMiseAjour());
		
	}
	
	//**********************METHODE POUR REMPLACER  ET RECUPERE TOU LES DONNE MISE EN JOUR DANS LE JEU POUR L'ENVOYER AU CLIENT 
	public  String donneMiseAjour() {
		
		String donnee = "UPDATE:";
		//1: NOMBRE DE TOURS 
		donnee+=getTurn();
		
		//2: TAILLE DE LA CARTE
		donnee+=":TAILLE;"+this.getSizeX()+"&"+this.getSizeY();
		
		//3: LES BOMBERMAN
		donnee+=":BOMBERMAN;";
		String bomberman="";
		for(int i=0; i< this.getListBomberman().size();i++) {
			if(i<this.getListBomberman().size()-1) {
				bomberman+=this.getListBomberman().get(i).toString()+"&";
			}else {
				bomberman+=this.getListBomberman().get(i).toString();
			}
			
			
		}
		donnee+=bomberman;
		
		//4 LES PNJ
		donnee+=":PNJ;";
		String pnj="";
		for(int i=0; i< this.getListPNJ().size();i++) {
			if(i<this.getListPNJ().size()-1) {
				pnj+=this.getListPNJ().get(i).toString()+"&";
			}else {
				pnj+=this.getListPNJ().get(i).toString();
				
			}
		}
		donnee+=pnj;
		
		//=5 LES MUR DESTRUCTUBLE
		donnee+=":BREAKABLE_WALLS;";

		String breakable_wall="";
		for(int i=0; i<this.getSizeX();i++) {
			for(int j=0; j<this.getSizeY();j++) {
				if(this.getStart_breakable_walls()[i][j]) {
					if(i<this.getSizeX() && j<this.getSizeY()) {
						breakable_wall+=i+","+j+"&";
					}else {
						breakable_wall+=i+","+j;
					}
					
				}
				
			}
		}

			

		
		donnee+=breakable_wall;
		
		//=6 LES MUR 
		donnee+=":WALLS;";

		String walls="";
		for (int i=0; i<this.getSizeX();i++ ) {
			for(int j=0; j<this.getSizeY(); j++) {
				if(this.getWalls()[i][j]) {
					if(i<this.getSizeX() && j<this.getSizeY()) {
						walls+= i+","+j+"&";
					}else {
						walls+= i+","+j;
					}
				}
			}
			

		} 
		donnee+=walls;
		
		//7 = LES BOMB
		donnee+=":BOMB;";
		String bomb="";
		for(int i=0;i< this.getBombs().size();i++) {
			if(i<this.getBombs().size()-1) {
				bomb+=this.getBombs().get(i).toString()+"&";
			}else {
				bomb+=this.getBombs().get(i).toString();	
			}
			
		}
		//System.out.println(bomb);
		donnee+=bomb;     
		
		//8 = LES ITEMS
		donnee+=":ITEM;";
		String item="";
		for(int i=0;i<this.getItems().size();i++) {
			if(i<this.getItems().size()-1) {
				item+=this.getItems().get(i).toString()+"&";
				
			}
			else {
				item+=this.getItems().get(i).toString();
						
			}
		}
		
		donnee+=item;
		
        
		return donnee;
	}
		
	

}

