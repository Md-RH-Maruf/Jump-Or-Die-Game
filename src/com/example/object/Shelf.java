package com.example.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.example.main.GameStateManager;
import com.example.state.GameState;
import com.example.state.Level1State;
import com.example.tileMap.Background;
import com.example.window.GameWindow;

public class Shelf extends GameState{

	private int width=GameWindow.WIDTH;
	private int height=GameWindow.HEIGHT;
	

	private double incrementX ;


	private double	xSpace=75;
	public static double  ySpace=70;

	//private int id;
	private int normalShelf=1;
	private int sawShelf=0;



	private BufferedImage[] shelfImage=new BufferedImage[3];

	private ArrayList<BufferedImage> shelfArray;

	public Shelf(int id){
		if(id==0){
			this.id=id;
		}else
			this.id=1;
		try {

			shelfArray=new ArrayList<BufferedImage>();
			shelfImage[0]=ImageIO.read(getClass().getResourceAsStream("/Objects/shelves.png"));
			//shelfImages = new ArrayList<BufferedImage[]>();
		} catch (IOException e) {
			e.printStackTrace();
		}
		incrementY = Level1State.temIncrimentY;
		init();	
		//setIncrementy(-1);
	}
	private void loadShelfImage(){

		if(id==normalShelf){
			shelfImage[id]= shelfImage[0].getSubimage(0, 0, 75, 10);
		}else
			shelfImage[id]= shelfImage[0].getSubimage(0, 10, 75, 10);

	}
	public void init() {
		loadShelfImage();
		initialYPosition();
		loadNewXposition();
	}
	private void initialYPosition() {

		y = height;
	}

	public void update() {

		y += incrementY;

	}
	public void draw(Graphics2D g) {

		g.drawImage(shelfImage[id],(int)x, (int) y, null);

	}
	public void loadNewXposition(){
		int preXPos=(int)(Level1State.object.get(Level1State.object.size()-1).x);
		if( preXPos> 250){
			x = preXPos - (xSpace+(300 - Player.y));
		}else
			x = preXPos + (xSpace+(300 - Player.y));

		if(x > 500)
			x -= 500;
		else if( x < -50)
			x += 500;

		
		if((preXPos-x)< 50 && (preXPos-x)>-50){

			if((preXPos-x)>0)
				x -=60;
			else
				x +=60;
		}

		}
	public void keyPressed(int k) {
	}
	public void keyReleased(int k) {
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setIncrementX(double incrementX) {
		this.incrementX = incrementX;
	}
	public void setIncrementy(double incrementy) {
		this.incrementY = incrementy;
	}
	@Override
	public Rectangle getBounds() {
		if(id==0){
			return new Rectangle((int)x,(int)y+2,75,10);
		}else
			return new Rectangle((int)x,(int)y,75,10);
	}
}
