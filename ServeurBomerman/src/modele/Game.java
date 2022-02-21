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
		
		
	}
	public void step() {
		//this.turn++;
		if(this.gameContinue() && this.turn<this.maxturn) {
			this.turn++;
			this.takeTurn();
		}else {
			this.isRunning=false;
			
		}
		MyServer.setRequetteServeur(this.donneMiseAjour());
		
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
		//this.notifierObservateurs();
		
	}
	
	//**********************METHODE POUR REMPLACER  ET RECUPERE TOU LES DONNE MISE EN JOUR DANS LE JEU POUR L'ENVOYER AU CLIENT 
	public  String donneMiseAjour() {
		
		String donnee = "UPDATE:";
		//1: NOMBRE DE TOURS 
		donnee+=getTurn();		
		return donnee;
	}
		
	

}

