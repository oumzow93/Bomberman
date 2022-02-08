package vue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import client.Output;

public class Clavier extends JFrame implements KeyListener {


	private static final long serialVersionUID = 1L;

	@Override
	public void keyPressed(KeyEvent event) {
		
		switch(event.getKeyCode()) {
		case KeyEvent.VK_NUMPAD8: Output.setRequete("DEPLACEMENT;HAUT"); break;
		case KeyEvent.VK_NUMPAD2: Output.setRequete("DEPLACEMANT;BAS"); break;
		case KeyEvent.VK_NUMPAD4: Output.setRequete("DEPLACEMENT;GAUCHE"); break;
		case KeyEvent.VK_NUMPAD6: Output.setRequete("DEPLACEMENT;DROITE"); break;
		
		default : Output.setRequete("DEPLACEMENT;STOP"); break;
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
