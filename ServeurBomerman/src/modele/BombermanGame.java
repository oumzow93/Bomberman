package modele;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;


import agent.Agent;
import agent.AgentBomberman;
import agent.AgentPNJ;
import agent.FactoryAgent;
import agent.Position;
import objets.Bomb;
import objets.Item;
import strategies.DeplacementAreatoire;
import strategies.StrategieBird;
import utils.AgentAction;






public class BombermanGame extends Game {
	String fileName;
	private InputMap  carrte;
	//private int actionAeffectué;
	
	
	
	private ArrayList<AgentBomberman> listBomberman = new ArrayList<>();
	private ArrayList<AgentPNJ>listPNJ = new ArrayList<>();
	private ArrayList<Bomb> bombs = new ArrayList<>();
	private ArrayList<Item> items= new ArrayList<>();
	private boolean [][]start_breakable_walls;
	private boolean [][]walls;
	private int sizeX;
	private int sizeY;
	
	
	private boolean fin_du_jeu;
	
	private int Score;
	private int NbVies;
	
	private boolean victoire;
	private boolean defaite;
	
	

	public BombermanGame(int maxturn,String fileName) {
		super(maxturn);
		this.fileName= fileName;
		
		
	
	}


	@Override
	public void initializeGame() {
		this.listBomberman.clear();
		this.listPNJ.clear();
		this.bombs.clear();
		this.items.clear();
		AgentBomberman.setIdCouleur();
		
		this.setFin_du_jeu(true);
		
		this.victoire= false;
		this.defaite = false;
		
		this.NbVies= AgentBomberman.nombre_de_vies;
		this.Score=0;
		try {
			this.carrte= new InputMap(this.fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i=0;i< this.carrte.getStart_agents().size();i++) {
			int x = this.carrte.getStart_agents().get(i).getX();
			int y= this.carrte.getStart_agents().get(i).getY();
			char type=this.carrte.getStart_agents().get(i).getType();
			if(this.carrte.getStart_agents().get(i).getType()=='B') {
				FactoryAgent.creerBombermant(new Position(x,y));
				this.listBomberman.add((AgentBomberman) FactoryAgent.creerBombermant(new Position(x,y)));
			}else {
				FactoryAgent.creerPNG(new Position(x,y),type);
				this.listPNJ.add((AgentPNJ) FactoryAgent.creerPNG(new Position(x,y),type));
			}				
			
		}
		
		this.setSizeX(this.carrte.getSizeX());
		this.setSizeY(this.carrte.getSizeY());
		this.start_breakable_walls = new boolean[this.sizeX][this.sizeY];
		this.walls= new boolean[this.sizeX][this.sizeY];
		
		for(int i=0;i<this.sizeX;i++) {
			for(int j=0;j<this.sizeY;j++) {
				this.start_breakable_walls[i][j]= this.carrte.getStart_breakable_walls()[i][j];
			}
		}
		
		//this.setStart_breakable_walls(this.carrte.getStart_breakable_walls());
		this.setWalls(this.carrte.get_walls());
		

		//System.out.println(this.donneMiseAjour());
		
		//MyServer.setRequetteServeur(this.donneMiseAjour());
		
		System.out.println("initialisation du jeu\n");		
	}


	@Override
	public boolean gameContinue() {
		
		return this.fin_du_jeu;
	}

	@Override
	public void gameOver() {
		//this.SetTurn(this.getMaxturn());
		this.setFin_du_jeu(false);
		
	}
	
	//=========================================REGLE DE FINFR JEU
	void finDuJeu() {
		if(this.getTurn()== this.getMaxturn() || this.listBomberman.isEmpty()) {
			System.out.println("\n=========== DEFAITE !=======================");
			this.defaite=true;
			this.gameOver();
			
			
		}
		if(this.listPNJ.isEmpty() && this.listBomberman.size()==1 ) {
			System.out.println("\n VICTOIRE !!!!!!!!!!!!");
			this.victoire=true;
			this.gameOver();
			
		}
	}
	
	  //PRENT EN ENTRÉE UN AGENT ET UNE ACTION ET REVOI VRAI SI L'ACTION EST POSSIBLE SUE LE PLATEAU
	public boolean  peuvoler(Agent agent, AgentAction  action) {
		int x= agent.getPosition().getX();
		int y= agent.getPosition().getY();
		
		//boolean [][]breakable_walls =start_breakable_walls;
		
		if(agent.typeAgent()!='V') {
			if( action== AgentAction.MOVE_LEFT && (start_breakable_walls [x-1][y]==true || walls[x-1][y]==true)) {
				return false;
			}
			if( action== AgentAction.MOVE_RIGHT &&( start_breakable_walls [x+1][y]==true || walls[x+1][y]==true)) {
				return false;
			}
			if( action== AgentAction.MOVE_DOWN && (start_breakable_walls [x][y-1]==true || walls[x][y-1]==true)) {
				return false;
			}
			if( action== AgentAction.MOVE_UP && (start_breakable_walls [x][y+1]==true || walls[x][y+1]==true)) {
				return false;
			}
			return true;
		}
		
		return true;
		
		
		
	}
	 
	public boolean deplacementPossible(Position position) {
		
			 
		 int x = position.getX();
		 int y= position.getY();
		 int sizeX = this.carrte.getSizeX();
		 int sizeY = this.carrte.getSizeY();

		 
		 return ((x>=0 && x<sizeX)&&(y>=0 && y<sizeY));
		
		
	}

	public boolean isLegalMove(Agent agent, AgentAction  action ) {
		
		int x=  agent.getPosition().getX();
		int y= agent.getPosition().getY();
		
		
		
		switch(action) {
		case MOVE_RIGHT: 
			x++; break;
		case MOVE_LEFT:
			x--; break;
		case MOVE_UP: 
			y++; break;
		case MOVE_DOWN: 
			y--; break;
		default:
			break;
		}	
		
	
		
		if(this.deplacementPossible(new Position(x-1,y)) &&  action ==AgentAction.MOVE_LEFT && this.peuvoler(agent, action) ){
			return true;
		}
		if(this.deplacementPossible(new Position(x+1,y)) && action ==AgentAction.MOVE_RIGHT && this.peuvoler(agent, action) ){
			return true;
		}
		if(this.deplacementPossible(new Position(x,y-1)) && action ==AgentAction.MOVE_DOWN && this.peuvoler(agent, action) ) {
			return true;
		}
		if(this.deplacementPossible(new Position(x,y+1)) && action ==AgentAction.MOVE_UP && this.peuvoler(agent, action) ){
			return true;
		}

	    return false;
	
	}
	
	
	//================================ CETTE METHODE MET A JOUR LA NOUVELLE POSITION D'UN AGENT EN FOCTION DE SON ACTION 	
	@Override
	public void moveAgent(Agent agent, AgentAction  action) {
		
		int x=  agent.getPosition().getX();
		int y= agent.getPosition().getY();
		
		
		if(this.isLegalMove(agent, action)) {
			switch(action) {
	  		case MOVE_RIGHT: 
	  			agent.setPosition(new Position(x+1,y)); break;
	  		case MOVE_LEFT:  
	  			agent.setPosition(new Position(x-1,y)); break;
	  			
	  		case MOVE_UP: 
	  			agent.setPosition(new Position(x,y+1)); break;
	  		case MOVE_DOWN: 
	  			agent.setPosition(new Position(x,y-1)); break;
	  		default:
	  			break;
			}
		
				
		}
		
			
	}
	
	
	//=======================================ELEMINATION DES AGENT BOMBERMAN  LORQU'IL RENCONTRE EN ENEMI
	public void tuerBombermane(Agent enemi) {
		ListIterator<AgentBomberman> itBom= this.listBomberman.listIterator();
		while(itBom.hasNext()) {
			AgentBomberman bomberman = itBom.next();
			 int x1= bomberman.getPosition().getX();
			 int y1= bomberman.getPosition().getY();
			 
			 int x2= enemi.getPosition().getX();
			 int y2= enemi.getPosition().getY();
			 if((x1==x2 && y1==y2)&& !bomberman.isInvincible() ) {
				 if(this.NbVies>=0) {
					 this.NbVies= this.NbVies-1; 
				 }else {
					 itBom.remove();
					 
				 }
				 
				 
				 		 
			 }
			
		}
	
	}
 
	
	//=================================GESTIONDES BOBS==============================================
	
	//======================================LACHER UN BOOB
	public void  putBomb(Agent agent ) {
		int x = agent.getPosition().getX();
		int y= agent.getPosition().getY();
		
		if(agent.typeAgent()=='B' && agent.getPosition()!=null) {
			AgentBomberman agb = (AgentBomberman) agent;
			Bomb bomb= new Bomb(new Position(x,y));
			bomb.setRange(agb.getRangeBom());
			
			if(! agb.isEstMalade()) {
				bomb.setAgent(agb.getCouleur());
				
				this.bombs.add(bomb);	
			}
			agent.setStrategie(new DeplacementAreatoire(this));

		}else if(agent.typeAgent()!='V') {
			agent.setStrategie(new DeplacementAreatoire(this));
		}
		

		
	}
	

	
	
	public void  mortPNJ(int x,int y) {
		ListIterator<AgentPNJ> itPnj= this.getListPNJ().listIterator();
		while(itPnj.hasNext()) {
			Agent pnj =itPnj.next();
			int xp=pnj.getPosition().getX();
			int yp= pnj.getPosition().getY();
			
			if(x==xp && y==yp) {
				System.out.println("mortPNJ");
				this.setScore(this.getScore()+1);
				itPnj.remove();
				
				
			}
		}
	}
	
	public void effetExplosion(Bomb bom) {
		
		Random aleatoire = new Random();
		int itemType = aleatoire.nextInt(4)+1;
		//int itemGeration =1;
		int itemGeration = aleatoire.nextInt(5);
		
		
		int x= bom.getPosition().getX();
		int y= bom.getPosition().getY();
		int rang = bom.getRange();
		int x1= bom.getPosition().getX()-rang;
		int x2= bom.getPosition().getX()+rang;
		int y1= bom.getPosition().getY()-rang;
		int y2= bom.getPosition().getY()+rang;
		//===================destruction des mures
		for(int i =x1; i<=x2;i++) {
			if( (i>0 && i<this.getCarrte().getSizeX()-1)  ) {
				if(start_breakable_walls[i][y]==true) {
					if(itemGeration==1) {
						this.items.add(new Item(new Position(i,y),itemType));
					}
					this.getCarrte().getStart_breakable_walls()[i][y]=false;

				}

				
				this.mortPNJ(i, y);
			}
			
			
		}
		
		for(int i =y1; i<=y2;i++) {
			if( (i>0 && i<this.getCarrte().getSizeY()-1)  ) {
				if(start_breakable_walls[x][i]==true) {
					if(itemGeration==1) {
						this.items.add(new Item(new Position(x,i),itemType));
					}
					this.getCarrte().getStart_breakable_walls()[x][i]=false;
					
				}
				
				this.mortPNJ(x, i);
			}
			
			
		}
			
	}
	
	
	
	public void objectsSpecieux(AgentBomberman bomberman) {

		int x=  bomberman.getPosition().getX();
		int y = bomberman.getPosition().getY();
		
		ListIterator<Item> itemItera = this.items.listIterator();
		
		while(itemItera.hasNext()) {
			Item itm= itemItera.next();
			if(itm.getTypeItem()==1 && (x==itm.getP().getX() && y== itm.getP().getY())) { // ==============AUGENTER LA RANGE DE LA BOMB DE 1
				bomberman.AugmentRange();
				itemItera.remove();
				
			}
			if(itm.getTypeItem()==2 && (x==itm.getP().getX() && y== itm.getP().getY())) {  // ==============DUMINIER LA RANGE DE LA BOMB DE 1
				bomberman.DimunueRnage();

				itemItera.remove();
				
			}
			if(itm.getTypeItem()==3 && (x==itm.getP().getX() && y== itm.getP().getY())) {
				for(AgentBomberman b: this.listBomberman) {
					if(b.getPosition().getX()==x && b.getPosition().getY()==y) {
						b.setEstMalade(this.getTime());
						
					}
				}
				itemItera.remove();
			}
			if(itm.getTypeItem()==4 && (x==itm.getP().getX() && y== itm.getP().getY())) {
				for(AgentBomberman b: this.listBomberman) {
					if(b.getPosition().getX()==x && b.getPosition().getY()==y) {
						b.setinvincible(this.getTime());
						
					}
				}
				itemItera.remove();
				
			}
		}



	}
 

	
	@Override
	public void takeTurn() {		

		
       //=============================DEPALCEMENT DES BOMBERMAN==========		
		for(int i=0; i< this.listBomberman.size();i++) {
			AgentBomberman agent  = this.listBomberman.get(i);
			
			agent.setStrategie(new DeplacementAreatoire(this));
			this.objectsSpecieux(agent);
			
		}
		//==============================DEPACLEMENT DES MECHANT
		for(int i=0; i<this.listPNJ.size();i++) {			
			Agent agent  = this.listPNJ.get(i);
			this.tuerBombermane(agent);
			if(agent.typeAgent()=='V') {
				agent.setStrategie(new StrategieBird(this));
			}
			else {
				agent.setStrategie(new DeplacementAreatoire(this));
			}
			
			
		}
		for( Bomb bom : this.bombs) {
			bom.exploision(this.getTime());
			if(bom.getStatue()==4) {
				this.effetExplosion(bom);
			}
		}
		
		ListIterator<Bomb> itbomb = this.bombs.listIterator();
		while(itbomb.hasNext()) {
			Bomb b=itbomb.next();
			if(b.Aexploser()) {
				itbomb.remove();
				
			}
				
		}
		//MyServer.setRequetteServeur(this.donneMiseAjour());*/
		finDuJeu();
		
	}
	
	@Override
	public ArrayList<AgentBomberman> getListBomberman() {
		return listBomberman;
	}
	@Override
	
	public ArrayList<AgentPNJ> getListPNJ() {
		return listPNJ;
	}
	
	@Override
	public InputMap getCarrte() {
		return carrte;
	}
	@Override
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}


	public ArrayList<Item> getItems() {
		return items;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public int getScore() {
		return Score;
	}


	public void setScore(int score) {
		Score = score;
	}


	public int getNbVies() {
		return NbVies;
	}


	public void setNbVies(int nbVies) {
		NbVies = nbVies;
	}


	public boolean isVictoire() {
		return victoire;
	}


	public boolean isDefaite() {
		return defaite;
	}


	public boolean isFin_du_jeu() {
		return fin_du_jeu;
	}


	public void setFin_du_jeu(boolean fin_du_jeu) {
		this.fin_du_jeu = fin_du_jeu;
	}


	public boolean [][] getStart_breakable_walls() {
		return start_breakable_walls;
	}


	public void setStart_breakable_walls(boolean [][] start_breakable_walls) {
		this.start_breakable_walls = start_breakable_walls;
	}


	public boolean [][] getWalls() {
		return walls;
	}


	public void setWalls(boolean [][] walls) {
		this.walls = walls;
	}


	public int getSizeX() {
		return sizeX;
	}


	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}


	public int getSizeY() {
		return sizeY;
	}


	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}








}
