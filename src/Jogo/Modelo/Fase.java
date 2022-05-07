package Jogo.Modelo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {
	private Image fundo;
	private Player player;
	private Timer timer;
	private List<Enemy1> enemy1;
    private boolean emJogo;
	
	
	public Fase() {
		setFocusable(true);
		setDoubleBuffered(true);
		
		Random gerador = new Random();
		for (int i = 0; i <= 7; i++) {
			ImageIcon referencia = new ImageIcon("res\\fundo"+gerador.nextInt(5)+".png");
			System.out.println("teste aq" + gerador);
			fundo = referencia.getImage();
         }
		
		player = new Player();
		player.load();
		
		addKeyListener(new TecladoAdapter());
		
		timer = new Timer(5, this);
		timer.start();
		
		inicializaInimigos();
		emJogo=true;
	}
	
	public void inicializaInimigos() {
		int condernadas[] = new int[20];
		enemy1= new ArrayList<Enemy1>();
		
		for (int i = 0; i < condernadas.length; i++) {
			int x = (int)(Math.random()*700+30);
			int y = (int)(Math.random()*-600+100);
			enemy1.add(new Enemy1(x,y));
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D graficos = (Graphics2D) g;
		
		if(emJogo==true) {
			graficos.drawImage(fundo, 0, 0, null);
			graficos.drawImage(player.getImagem(), player.getX(), player.getY(), this);
			
			
			for( int o = 0; o <enemy1.size(); o++) {
				Enemy1 in = enemy1.get(o);
				in.load();
				graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);
			}
		} else {
			ImageIcon fimJogo = new ImageIcon("res\\gameover.png");
			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
		}

		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		player.update();
/////erro esta abaixo		
		for (int o = 0; o < enemy1.size(); o++) {
			Enemy1 in = enemy1.get(o);
			if(in.isVisivel()) {
				in.update();
			}else {
				enemy1.remove(o);
			}
		}
		checarColisoes();
		repaint();		
	}
	
	public void checarColisoes() {
		Rectangle formaNave = player.getBounds();
		Rectangle formaEnemy1;
		
		for(int i =0; i< enemy1.size(); i++) {
			Enemy1 tempEnemy1 = enemy1.get(i);
			formaEnemy1 = tempEnemy1.getBounds();
			if(formaNave.intersects(formaEnemy1)) {
				player.setVisivel(true);
				tempEnemy1.setVisivel(false);
				emJogo = true;
			}
		}
	}
	
	private class TecladoAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			player.keyRelease(e);
		}
	}
	

}
