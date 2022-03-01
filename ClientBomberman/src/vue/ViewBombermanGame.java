package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import agent.AgentBomberman;
import agent.AgentPNJ;
import controleur.Controleurclient;
import objets.Bomb;
import objets.Item;
import utils.AgentAction;
import utils.ColorAgent;
import utils.InfoAgent;
import utils.InfoBomb;
import utils.InfoItem;
import utils.ItemType;
import utils.StateBomb;

public class ViewBombermanGame {
	private PanelBomberman panelBonberman;
	private  ViewCommand  commande;
	private JFrame frame;
	private InfoBomb bomb;
	private JPanel menuJPanel;



	public ViewBombermanGame() {


		this.panelBonberman=  this.InitPanelBonberman();
		this.frame = new JFrame("Bomberman");
		this.frame.setSize(900, 450);
		this.frame.setLayout(new BorderLayout(0,2));



		//AJOUT MENU POUR CHANGER LES NIVEAUX DU JEU
		menuJPanel = new JPanel();
		String s1[] = { "alone", "arene","exemple", "jeu_symetrique", "jeu1","niveau1","niveau2","niveau3" };

		final JComboBox<String>  typeChaine = new JComboBox<>(s1);
		menuJPanel.add(typeChaine);
		JButton changerLayoutButton = new JButton("Changer de niveau");
		menuJPanel.add(changerLayoutButton);

		Dimension windowSize = frame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x = windowSize.width / 2 ;
		int dy = centerPoint.y = windowSize.height / 2 - 350;
		frame.setLocation(dx, dy);
		frame.add(this.panelBonberman,BorderLayout.CENTER);
		frame.add(menuJPanel, BorderLayout.NORTH);





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





		//==================================ACTION SUR LES BOUTON DE COMMANDES

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



	//=============================INIT PANELE BOMBERMAN (RECUPERATION DES DONNÉES INITIAL ENVOYER PAR LE SERVEUR AU TOURS 0)

	public PanelBomberman InitPanelBonberman() {



		boolean [][]wall =  Controleurclient.getWalls();
		boolean [][]breakable_walls = Controleurclient.getStart_breakable_walls();
		ArrayList<InfoAgent> infoAgent = new ArrayList<>();
		int sizeX = Controleurclient.getSizeX();
		int sizeY = Controleurclient.getSizeY();

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
		return new PanelBomberman(sizeX,sizeY,wall,breakable_walls,infoAgent);

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

	public  ItemType getItem(int item) {



        if(item==1) {
            return ItemType.FIRE_UP;
        }
        if(item==2) {
            return ItemType.FIRE_DOWN;
        }
        if(item==3) {
            return ItemType.SKULL;
        }
        if(item==4) {
            return ItemType.FIRE_SUIT;
        }
        return null;
    }




	public void afficher() {
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		commande.affichier();
	}




	//===================================ACTILIATION DU PANAU A CHAQUE TOURS DU JEAU

	public  void actualiser() {
		this.commande.UpdateTurn(Controleurclient.getTurn());





		boolean [][]breakable_walls = Controleurclient.getStart_breakable_walls();
		ArrayList<InfoAgent> infoAgent = new ArrayList<>();
		ArrayList<InfoBomb> bombs  = new ArrayList<>();
		ArrayList<InfoItem> items = new ArrayList<>();

		//=================================================== MISE A JOURS DES AJENTS
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


		//============================================ MISE A JOUR DES BOMBS
		for (Bomb b: Controleurclient.getBombs()) {
			int x= b.getPosition().getX();
			int y= b.getPosition().getY();
			int range = b.getRange();


			switch(b.getStatue()) {
			case 0:  bomb = new InfoBomb(x,y,range,StateBomb.Step0); break;
			case 1:  bomb = new InfoBomb(x,y,range,StateBomb.Step1); break;
			case 2:  bomb = new InfoBomb(x,y,range,StateBomb.Step2); break;
			case 3: bomb = new InfoBomb(x,y,range,StateBomb.Step3); break;
			case 4: bomb = new InfoBomb(x,y,range,StateBomb.Boom); break;

			}
			bombs.add(bomb);
		}
		
		for(Item i: Controleurclient.getItems()) {
			int x=i.getP().getX();
			int y = i.getP().getY();
			int type=i.getTypeItem();
			InfoItem item=new InfoItem(x, y, this.getItem(type));
			items.add(item);
			
			
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