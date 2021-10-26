package com.example.state;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.example.main.GameStateManager;

public abstract class GameState {

	protected GameStateManager gameSManager;
	
	public double x;
	public double incrementY;

	public double y;
	public int id;
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract Rectangle getBounds();
}
