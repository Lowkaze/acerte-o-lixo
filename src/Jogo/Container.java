package Jogo;

import javax.swing.JFrame;
import Jogo.Modelo.Fase;

public class Container extends JFrame {
	public Container() {
		add(new Fase());
		setTitle("Salve o Mundo");
		setSize(750, 680);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main (String[] args) {
		new Container();
	}
}