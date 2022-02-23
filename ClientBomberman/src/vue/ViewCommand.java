package vue;


import javax.swing.*;
import java.awt.*;


public class ViewCommand   {
	
	
	
	
	private JFrame fenetreCommand;
	//=============PANEL
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	//=============BUTTON
	private JButton pause;
	private JButton play;
	private JButton restart;
	private JButton step;
	
	private  JLabel label;
	
	JSlider  slider;
	

	public ViewCommand() {
		super();
		this.fenetreCommand= new JFrame();
		this.fenetreCommand.setTitle("Commande");
		this.fenetreCommand.setSize(new Dimension(900,300));
	
		this.fenetreCommand.setLocation(450, 500);
		
		
		//================================AJOUT DES COMPOSANTS 
		
		GridLayout grille_1 = new GridLayout(2,1);
		GridLayout grille_1_1 = new GridLayout(1,4);
		GridLayout grille_1_2 = new GridLayout(1,2);
		GridLayout grille_1_2_1 = new GridLayout(2,1);
		
		
		
		this.panel1 = new JPanel();
		this.panel2 = new JPanel();
		this.panel3 = new JPanel();
		
		this.fenetreCommand.setLayout(grille_1);
		this.panel1.setLayout(grille_1_1);
		this.panel2.setLayout(grille_1_2);
		this.panel3.setLayout(grille_1_2_1);
		 
		 
		 this.fenetreCommand.add(this.panel1);
		 this.fenetreCommand.add(this.panel2);
		 this.panel2.add(this.panel3);
		
		
		
		this.pause = new JButton(new ImageIcon("./icons/icon_pause.png"));
		this.play = new JButton(new ImageIcon("./icons/icon_play.png"));
		this.restart = new JButton(new ImageIcon("./icons/icon_restart.png"));
		this.step = new JButton(new ImageIcon("./icons/icon_step.png"));
		
		this.panel1.add(restart);
		this.panel1.add(play);
		this.panel1.add(step);
		this.panel1.add(pause);
		
		
		
		
		
		this.slider = new JSlider(1,10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1);
		slider.setValue(1);
		this.panel3.add(new JLabel("Nomber of turns per second "));
		this.panel3.add(slider);
		label = new  JLabel("Turn :0",JLabel.CENTER);
		this.panel2.add(label);
		
		

		
		
		
	

		
		//=================================GESTIONS DES ÉTAT DES BOUTON 
		
		
		
	}
	
public void affichier() {
	this.fenetreCommand.setVisible(true);
	this.fenetreCommand.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
	
	public  void UpdateTurn(String trun) {
		label.setText("Turn:"+trun);
	}
	
	public JFrame getFenetreCommand() {
		return fenetreCommand;
	}

	
	public JButton getPause() {
		return pause;
	}


	public JButton getPlay() {
		return play;
	}

	
	public JButton getRestart() {
		return restart;
	}



	public JButton getStep() {
		return step;
	}


	

	public JSlider getSlider() {
		return slider;
	}

    public JLabel getLabel()
    {
    	return this.label;
    }
    public void setLabel(String text) {
    	this.label.setText(text);
    }





	
	public void actualiserlabel() {
		//this.label.setText("Turn :"+this.Game.getTurn());
	}




}
