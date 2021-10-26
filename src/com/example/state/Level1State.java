package com.example.state;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.text.html.MinimalHTMLWriter;

import com.example.main.GameStateManager;
import com.example.object.LifePoint;
import com.example.object.Player;
import com.example.object.PointTable;
import com.example.object.Saw;
import com.example.object.Shelf;
import com.example.object.Wall;
import com.example.process.AudioPlayer;
import com.example.tileMap.Background;
import com.example.window.GameWindow;

public class Level1State extends GameState{

	public static boolean isRunning;
	public boolean isPuese;
	private Background bg;
	public static Wall wall;
	public static Saw saw;
	
	public static double temIncrimentY=-1.3;
	
	public static LinkedList<GameState> object =new LinkedList<GameState>();

	public static  Shelf shelf;

	public static Player player;
	public static PointTable pointTable;
	
	public static LifePoint life;
	public static LifePoint point;

	private int newShelfId=1;
	private int afterLifeShow, afterPointShow;
	private int miniNormalshelf, maxNormalShelf;
	
	
	
	public Level1State(GameStateManager gameSManeger){
		
		this.gameSManager=gameSManeger;
		bg = new Background("/Background/mainBackground -2.png",0);
	
		init();
	}


	public void init() {
		try{
			object =new LinkedList<GameState>();
			afterLifeShow = 200;
			afterPointShow = 50;
			miniNormalshelf = 3;
			maxNormalShelf= 5;
			bg.setVector(0,0,0, 0);

			player = new Player();
			life=new LifePoint(2);
			point =new LifePoint(1);
			pointTable= new PointTable(player);
			player.setPosition(320, 50);
			
			addObject(saw=new Saw(gameSManager));
			
			addObject(wall=new Wall(gameSManager));
			addObject(new Shelf(1));
			
			
			
			isRunning = true;
			
			if(MenuState.isBgMusicOn){
				gameSManager.bgLevel1Music.play();
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void update() {
		
		if(isRunning && !isPuese){

			bg.update();
			wall.update();
			saw.update();

			shelfUpdate();
			player.collisionDetection();
			player.update();
			
			
			life.update(temIncrimentY);
			point.update(temIncrimentY);
			
		}
	}
	

	private void shelfUpdate() {
		
		for(int i=2;i<object.size();i++){
			object.get(i).update();
		}
		
		if(object.get(2).y<0){
				player.points += player.addPoint;
				object.remove(2);
				//System.out.println("Incrementy="+temIncrimentY);
				if(player.points % 100 == 0 && player.points / 100 > 0 ){
					Shelf.ySpace += 1;
					for(int i=1;i<object.size();i++){
						object.get(i).incrementY -= 0.1;
						
					}
					temIncrimentY = object.get(1).incrementY;
					
					if(temIncrimentY<-3.3){
						temIncrimentY =-3.3;
					}
				}
				
				if(point.y < 0 ){
					point.isLifePointDraw = 0;
					//System.out.println("Point y="+point.y);
				}
				if(life.y < 0){
					life.isLifePointDraw = 0;
					//System.out.println("life y="+life.y);
				}
				
		}
		
				
		if(object.get(object.size()-1).y<(GameWindow.HEIGHT- Shelf.ySpace)){
			if((newShelfId >= miniNormalshelf && player.y > 200 ) || maxNormalShelf == newShelfId){
				object.add(new Shelf(0));
				newShelfId = 1;
			}else{
				object.add(new Shelf(1));
				newShelfId++;
			}
			
			if(player.points % afterLifeShow ==0 && player.points / afterLifeShow > 0){
				life.isLifePointDraw=1;
				life.setPostion(object.get(object.size()-1).x+25, object.get(object.size()-1).y-25);
				//System.out.println("new life Create");
			}
			else if(player.points % afterPointShow ==0 && player.points / afterPointShow > 0){
				point.isLifePointDraw= 1;
				point.setPostion(object.get(object.size()-1).x+25, object.get(object.size()-1).y-25);
				//System.out.println("new Point Create");
			}
			
			
		}
		
		
	}
	
	


	public void draw(Graphics2D g) {
		bg.draw(g);
	

	}
	
	public static  void shelfDraw(Graphics2D g){
		for(int i=2;i<object.size();i++){
			
			object.get(i).draw(g);
		}
	}
	
	private void addObject(GameState obj){
		this.object.add(obj);
	}
	
	private void removeObject(GameState obj){
		this.object.remove(obj);
	}
	public void keyPressed(int k) {

		if(k == KeyEvent.VK_LEFT){
			player.setLeftMove(true);
			player.setFacingRight(false);
		}
		if(k == KeyEvent.VK_RIGHT){
			player.setRightMove(true);
			player.setFacingRight(true);
		}
		if(k == KeyEvent.VK_UP) player.setJumping(true);
		
		if(k == KeyEvent.VK_SPACE){ 
			
			isPuese = !isPuese;}
		
		if(k == KeyEvent.VK_ENTER) {
			if(!isRunning){
				
				//gameSManager.gameState[GameStateManager.LEVEL1STATE]=null;
				temIncrimentY= -1.3;
				gameSManager.setState(GameStateManager.LEVEL1STATE);
				//System.out.println("Ok");
			}
			
		}
		if(k == KeyEvent.VK_BACK_SPACE){
			gameSManager.bgLevel1Music.stop();
			gameSManager.setState(GameStateManager.MENUSTATE);
		}
	}
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeftMove(false);
		if(k == KeyEvent.VK_RIGHT) player.setRightMove(false);
		if(k == KeyEvent.VK_UP) player.setJumping(false);

	}


	public Rectangle getBounds() {
	
		return null;
	}
}
