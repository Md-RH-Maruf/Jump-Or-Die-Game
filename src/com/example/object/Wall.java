package com.example.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.example.main.GameStateManager;
import com.example.state.GameState;
import com.example.tileMap.Background;

public class Wall extends GameState {

	Background bg;
	public Wall(GameStateManager gameSManager){
		
		bg=new Background("/Objects/wall.png", 1);
		incrementY = -1.3;
		bg.setVector(0,0,0, incrementY);
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
		if(Player.isFacingRight()){
			return new Rectangle(477,0,23,600);
		}else
			return new Rectangle(0,0,25,600);
		
	}

}
