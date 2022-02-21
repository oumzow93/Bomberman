package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import client.Input;
import controleur.Controleurclient;
import models.InputMap;
import utils.InfoAgent;

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
			ArrayList<InfoAgent> infoAgent = carte.getStart_agents();
			
			
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
	
	public void afficher() {
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		commande.affichier();
	}
	
	
	public  void actualiser() {
		this.commande.UpdateTurn(Input.getGetTrun());
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
