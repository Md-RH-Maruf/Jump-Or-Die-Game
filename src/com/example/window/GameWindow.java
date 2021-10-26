package com.example.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.example.main.GameStateManager;
import com.example.state.ObjectName;

public class GameWindow extends JPanel implements Runnable,KeyListener{
	
	public static int WIDTH = 500 , HEIGHT = 600;
	
	private int posX=0,posY=0;
	
	private Thread thread;
	
	private GameStateManager gameSManeger;
	
	private boolean running;
	public static boolean isPrint=true;
	private int FPS=60;
	private long targetTime=1000/FPS;
	
	
	private BufferedImage image;
	private Graphics2D g;
	
	
	int i;
	public GameWindow() {
	
		super();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		//setFocusable(true);
		setFocusable(true);
		requestFocus();
		
	} 
	
	public void addNotify(){
		super.addNotify();
		
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	public void init(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		g = (Graphics2D) image.getGraphics();
		
		gameSManeger=new GameStateManager();
		
		//gameSManeger.addObject(new Player(10, 20, ObjectName.player));
		
	}

	public void run() {
	
		init();
		
		this.requestFocus();
		if(!running)
			running = true;
		
		long start;
		long elapsed;
		long wait;
		
		while(running){
			System.out.println(i++);
			start=System.nanoTime();

			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime()-start;
			wait = targetTime - elapsed / 1000000;

			if(wait<0) wait=5;
			try {
				thread.sleep(wait);
				
			} catch (Exception  e) {

				e.printStackTrace();
			}
			isPrint=false;
			
		}
	}
	
	private void update(){
	
		gameSManeger.update();
	}
	
	
	private void draw(){
		gameSManeger.draw(g);
	
	}
	private void drawToScreen(){
		
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH ,HEIGHT,null);
		g2.dispose();
	}
	
	public void keyPressed(KeyEvent k) {
		gameSManeger.keyPressed(k.getKeyCode());
	}
	public void keyReleased(KeyEvent k) {
		gameSManeger.keyReleased(k.getKeyCode());
	}
	public void keyTyped(KeyEvent arg0) {
	}

}
