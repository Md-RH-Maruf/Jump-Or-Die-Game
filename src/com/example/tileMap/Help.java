package com.example.tileMap;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.main.GameStateManager;
import com.example.state.GameState;
import com.example.state.MenuState;

public class Help extends GameState{
	
	Background bg;

	static BufferedImage image;
	
	public Help(GameStateManager gsm){
		this.gameSManager = gsm;
		init();
	}
	public void init() {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Background/Help.png"));
			bg = new Background("/Background/mainBackground -2.png",0);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public void update() {
	}
	public void draw(Graphics2D g) {
		bg.draw(g);
		
	}
	public static void drawHelp(Graphics2D g){
		g.drawImage(image, 10, 10, null);
	}
	public void keyPressed(int k) {
		if(k ==  KeyEvent.VK_BACK_SPACE){
			MenuState.makeSfxSound();
			gameSManager.setState(GameStateManager.MENUSTATE);
		}
	}
	public void keyReleased(int k) {
	}
	public Rectangle getBounds() {
		return null;
	}
}
