package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
	
	
	private JLabel NbVies;
	private JLabel Score;



	public ViewBombermanGame() {


		this.panelBonberman=  this.InitPanelBonberman();
		this.frame = new JFrame("Bomberman");
		this.frame.setSize(900, 450);
		this.frame.setLayout(new BorderLayout(0,2));



		//AJOUT MENU POUR CHANGER LES NIVEAUX DU JEU
		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new GridLayout(1,3));
		frame.add(panelMenu,BorderLayout.NORTH);
		JButton layout=new JButton("Niveua1");
		//layout.setBackground(Color.lightGray);
		panelMenu.add(layout);
		
		JButton modeInteractif = new JButton("Aléatoire");
		//modeInteractif.setBackground(Color.lightGray);
		panelMenu.add(modeInteractif);
		
		//==============PANEL POUR LES SCORE ET NOMBRE DE VIES 
		JPanel NbV_et_Score = new JPanel();
		Border border = BorderFactory.createRaisedBevelBorder();
		NbV_et_Score.setBorder(border);
		NbV_et_Score.setLayout(new GridLayout(1,2));
		panelMenu.add(NbV_et_Score);
		this.NbVies = new JLabel();
		this.Score= new JLabel();
		
		  this.NbVies.setText("VIES RESTENT :"+Controleurclient.getNombreVie());
		  this.Score.setText("  Score: "+Controleurclient.getScore());
		
		NbV_et_Score.add(this.Score);
		NbV_et_Score.add(this.NbVies);

		Dimension windowSize = frame.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x = windowSize.width / 2 ;
		int dy = centerPoint.y = windowSize.height / 2 - 350;
		frame.setLocation(dx, dy);
		frame.add(this.panelBonberman,BorderLayout.CENTER);
		frame.add(panelMenu, BorderLayout.NORTH);
		
		





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
		
		
		
		
		
		
		
		
		//=================================================AJOUTE DES ACTION LISTNER SUR LES BOUTON DES INTERFACES

		layout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {

               

				String[] optionsToChoose = {"niveau1", "niveau2", "niveau3"};

				String niveau = (String) JOptionPane.showInputDialog(
						null,
						"Selectionné votre niveau",
						"Changement de niveau",
						JOptionPane.QUESTION_MESSAGE,
					    new ImageIcon("./images/B42.png"),
						optionsToChoose,
						optionsToChoose[2]);

				System.out.println("nivau " + niveau+".lay");
				Controleurclient.setNiveau(niveau);
				Controleurclient.changeNiveau();
				


				frame.remove(panelBonberman);
				frame.validate();



		
				panelBonberman=  InitPanelBonberman();
				

				frame.add(panelBonberman,BorderLayout.CENTER);
				frame.revalidate();

				panelBonberman.repaint();

			}






		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		





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