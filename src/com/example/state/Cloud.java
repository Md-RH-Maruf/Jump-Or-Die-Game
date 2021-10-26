package com.example.state;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.example.main.GameStateManager;
import com.example.tileMap.Background;

public class Cloud extends GameState{
	
	private Background bg;

	public Cloud(GameStateManager gameSManager){
		this.gameSManager= gameSManager;
		
		bg= new Background("/Background/forGame.png", 1);
		bg.setVector(0,0,-0.1, 0);
	}
	public void init() {
	}

	public void update() {
		bg.update();
	}
	public void draw(Graphics2D g) {
		bg.draw(g);
	}
	public void keyPressed(int k) {
	}
	public void keyReleased(int k) {
		
	}
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
