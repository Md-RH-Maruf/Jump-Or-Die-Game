package com.example.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.example.main.GameStateManager;
import com.example.state.GameState;
import com.example.tileMap.Background;

public class Saw extends GameState {

	Background bg;
	public Saw(GameStateManager gameSManager){
		
		bg=new Background("/Objects/saw.png", 0);
		
		bg.setVector(26,0,0, 0);
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
		if(Player.isFalling()&& !Player.isJumping()){
			return new Rectangle(0,575,600,25);
		}else
			return new Rectangle(0,0,600,25);
	}

}

