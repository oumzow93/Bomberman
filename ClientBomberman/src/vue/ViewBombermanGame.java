package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import agent.AgentBomberman;
import agent.AgentPNJ;
import controleur.Controleurclient;
import models.InputMap;

import utils.AgentAction;
import utils.ColorAgent;
import utils.InfoAgent;
import utils.InfoBomb;
import utils.InfoItem;

public class ViewBombermanGame {
	private PanelBomberman panelBonberman;
	private  ViewCommand  commande;
	private JFrame frame;



	public ViewBombermanGame() {



		try {
			InputMap carte = new InputMap("./layouts/niveau1.lay");

			int sizeX=  carte.getSizeX();
			int sizeY  = carte.getSizeY();
			boolean [][]wall = carte.get_walls();
			boolean [][]breakable_walls = carte.getStart_breakable_walls();
			ArrayList<InfoAgent> infoAgent = new ArrayList<>();
			System.out.println(Controleurclient.getListBomberman().size());

			for( AgentBomberman Bomberman: Controleurclient.getListBomberman()) {
				int x= Bomberman.getPosition().getX();
				int y= Bomberman.getPosition().getY();
				char type= Bomberman.typeAgent();
				int  action= Bomberman.getDirection();

				InfoAgent inf_a= new InfoAgent(x,y,this.getAction(action),type,this.getCouleur(Bomberman.getCouleur()),Bomberman.isInvincible(),Bomberman.isEstMalade());
				infoAgent.add(inf_a);

			}
			
		    for(AgentPNJ pnj: Controleurclient.getListPNJ()) {
		    	int x= pnj.getPosition().getX();
		    	int y= pnj.getPosition().getY();
		    	char type= pnj.typeAgent();
		    	int action = pnj.getDirection();
		    
		    	InfoAgent inf_a= new InfoAgent(x,y,this.getAction(action),type,ColorAgent.DEFAULT,false,false);
		    	infoAgent.add(inf_a);
		    	
		    }


			this.panelBonberman= new PanelBomberman(sizeX,sizeY,wall,breakable_walls,infoAgent);
			this.frame = new JFrame("Bomberman");
			this.frame.setSize(900, 450);
			this.frame.setLayout(new BorderLayout(0,2));



			Dimension windowSize = frame.getSize();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Point centerPoint = ge.getCenterPoint();
			int dx = centerPoint.x = windowSize.width / 2 ;
			int dy = centerPoint.y = windowSize.height / 2 - 350;
			frame.setLocation(dx, dy);
			frame.add(this.panelBonberman,BorderLayout.CENTER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		commande  = new ViewCommand ();
		Clavier clavier = new Clavier();
		//====================================================ACTION DU CLAVIER
		this.frame.addKeyListener(clavier);

		commande.getFenetreCommand().addKeyListener(clavier);
		commande.getPause().addKeyListener(clavier);
		commande.getPlay().addKeyListener(clavier);
		commande.getRestart().addKeyListener(clavier);
		commande.getStep().addKeyListener(clavier);
		commande.getSlider().addKeyListener(clavier);
		commande.getFenetreCommand().setFocusable(true);
		this.frame.setFocusable(true);







		//*******************************BUTTON PLAY
		this.commande.getPlay().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {

				Controleurclient.play();

			}
		});







		//*****************************BOUTON PAUSE
		this.commande.getPause().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				Controleurclient.pause();

			}
		});

		//=============================BOUTON ACTIALISER
		this.commande.getRestart().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				Controleurclient .restart() ;

			}
		});
		//============================BOUTTON AVANCER
		this.commande.getStep().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				Controleurclient .step();

			}
		});




	}

	//================================ ATTRIBUTION DE DIFFERENTES POUR LES AGENT
	public ColorAgent getCouleur(int indice) {
		ColorAgent resultat = ColorAgent.DEFAULT;

		switch(indice) {
		case 0: resultat= ColorAgent.BLEU; break;
		case 1:resultat = ColorAgent.ROUGE; break;
		case 2:resultat = ColorAgent.VERT; break;
		case 3:resultat = ColorAgent.JAUNE; break;
		case 4:resultat = ColorAgent.BLANC; break;
		default: break;
		}
		return resultat;
	}

	//=====================================RECUPERATION DES DIRECTION DES AGENTS 
	public AgentAction getAction( int direction) {
		AgentAction result = AgentAction.STOP;
		switch(direction) {
		case 0: result= AgentAction.STOP; break;
		case 1:result= AgentAction.MOVE_RIGHT; break;
		case 2:result= AgentAction.MOVE_LEFT; break;
		case 3:result= AgentAction.MOVE_UP; break;
		case 4:result= AgentAction.MOVE_DOWN; break;


		}
		return result;
	}










	public void afficher() {
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		commande.affichier();
	}


	public  void actualiser() {
		this.commande.UpdateTurn(Controleurclient.getTurn());
		//System.out.println(Controleurclient.getSizeX()+","+Controleurclient.getSizeY());




		boolean [][]breakable_walls = new boolean [Controleurclient.getSizeX()][Controleurclient.getSizeX()];
		ArrayList<InfoAgent> infoAgent = new ArrayList<>();
		ArrayList<InfoBomb> bombs  = new ArrayList<>();
		ArrayList<InfoItem> items = new ArrayList<>();
		
		
		for( AgentBomberman Bomberman: Controleurclient.getListBomberman()) {
			int x= Bomberman.getPosition().getX();
			int y= Bomberman.getPosition().getY();
			char type= Bomberman.typeAgent();
			int  action= Bomberman.getDirection();

			InfoAgent inf_a= new InfoAgent(x,y,this.getAction(action),type,this.getCouleur(Bomberman.getCouleur()),false,false);
			infoAgent.add(inf_a);

		}
	    for(AgentPNJ pnj: Controleurclient.getListPNJ()) {
	    	int x= pnj.getPosition().getX();
	    	int y= pnj.getPosition().getY();
	    	char type= pnj.typeAgent();
	    	int action = pnj.getDirection();
	    
	    	InfoAgent inf_a= new InfoAgent(x,y,this.getAction(action),type,ColorAgent.DEFAULT,false,false);
	    	infoAgent.add(inf_a);
	    	
	    }



		this.panelBonberman.updateInfoGame(breakable_walls, infoAgent, items,bombs);
		this.panelBonberman.repaint();
	}

	public JFrame getFrame() {
		return frame;
	}

	public PanelBomberman getPanelBonberman() {
		return panelBonberman;
	}

	public ViewCommand getCommande() {
		return commande;
	}

	public void setPanelBonberman(PanelBomberman panelBonberman) {
		this.panelBonberman = panelBonberman;
	}



}
