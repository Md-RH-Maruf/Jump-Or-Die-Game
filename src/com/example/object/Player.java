package com.example.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BufferCapabilities.FlipContents;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import com.example.main.GameStateManager;
import com.example.process.Animation;
import com.example.process.AudioPlayer;
import com.example.state.Level1State;
import com.example.state.MenuState;
import com.example.tileMap.HighScore;

public class Player {

	Animation animation;



	public int health;
	private int maxHealth;
	public static int points;
	public int addPoint;
	private int extraPoint;

	private int width;
	private int height;
	private double moveSpeed ;


	private double stopSpeed ;
	private double maxSpeed;
	private double fallSpeed;
	private double maxFallSpeed;
	private double jumpStart;
	private double stopJumpSpeed;
	


	private long flinchingTimer;

	private boolean flinching;
	private boolean isDownIntersect;

	private static boolean facingRight;


	private boolean leftMove;
	private boolean rightMove;
	private boolean up;
	private boolean down;

	private boolean dead;
	private boolean stand;
	private static boolean falling;
	private static boolean jumping;

	public static double x;
	public static double y;
	private double dx;
	private double dy;

	private static final int LEFTIDLE=1;
	private static final int RIGHTIDLE=0;
	private static final int LEFTWALKING=3;
	private static final int RIGHTWALKING=2;
	private static final int JUMPING=4;
	private static final int FALLING=5;
	private static final int SCRATCHING=0;
	private static int currentAction;

	private HashMap<String	, AudioPlayer> sfx;
	
	private ArrayList<BufferedImage[]> frames;
	
	private final int[] framesNum={10,10,12,12,2,2,2};
	
	private Font gameOvaerFont=new Font("serif",Font.BOLD,40);
	private Font pointFont=new Font("serif",Font.BOLD,20);
	private Font insFont=new Font("serif",Font.PLAIN,18);
	
	private String gemaOver="Game Over!";
	private String point;
	private String instruction="Press Enter for Restart or Back_Space for exit...";

	public Player(){
		width = 25;
		height = 50;

		moveSpeed = 0.4;
		maxSpeed = 5.5;
		stopSpeed = 0.8;
		fallSpeed = 0.15;
		maxFallSpeed = 5.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		falling = true;
		facingRight = true;
		stand = false;
		points=0;
		addPoint = 5;
		health = 3;
		maxHealth = 3;
		extraPoint=30;

		try{
			BufferedImage sheet= ImageIO.read(getClass().getResourceAsStream("/Objects/player.png"));

			frames = new ArrayList<BufferedImage[]>();

			for(int i=0;i<7;i++){
				BufferedImage[] bi=new BufferedImage[framesNum[i]];
				
				if(i%2 == 0){
					for(int j=0; j<framesNum[i] ; j++){
						bi[j]= sheet.getSubimage(j*width, i*height, width, height);
					}
				}
				else if(i == 7){
					for(int j=0; j<framesNum[i] ; j++){
						bi[j]= sheet.getSubimage(j*width, i*height, width, height);
					}
				}else{
			
					for(int j=framesNum[i]-1, k=0; j>=0 ; j-- ,k++){
						bi[k]= sheet.getSubimage(j*width, i*height, width, height);
					}
				}

				frames.add(bi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

		animation= new Animation();
		currentAction = LEFTIDLE;

		animation.setFrames(frames.get(LEFTIDLE));
		animation.setDelay(400);
		
		sfx= new HashMap<String, AudioPlayer>();
		sfx.put("jump", new AudioPlayer("/audio/jump.mp3"));
		sfx.put("crush", new AudioPlayer("/audio/crush.mp3"));
		sfx.put("plusPoint", new AudioPlayer("/audio/extraPoint.mp3"));
		sfx.put("GameOver", new AudioPlayer("/audio/GameOver.mp3"));
	}

	public void update(){
		getNextPosition();
		animation.update();

		if(leftMove || rightMove){
			if(facingRight){
				if(currentAction != RIGHTWALKING){
					currentAction = RIGHTWALKING;

					animation.setFrames(frames.get(RIGHTWALKING));
					animation.setDelay(50);

				}
			}else{
				if(currentAction != LEFTWALKING){
					currentAction = LEFTWALKING;

					animation.setFrames(frames.get(LEFTWALKING));
					animation.setDelay(50);

				}
			}

		}
		else if(jumping){

			if(currentAction != JUMPING){
				makeSfxJumpSound();
				currentAction = JUMPING;

				animation.setFrames(frames.get(JUMPING));
				animation.setDelay(1000);
			}


		}else if(falling){
			if(currentAction != FALLING){
				makeSfxJumpSound();
				currentAction = FALLING;

				animation.setFrames(frames.get(FALLING));
				animation.setDelay(1000);
			}
		}else{
			if(facingRight){
				if(currentAction != RIGHTIDLE){
					currentAction = RIGHTIDLE ;

					animation.setFrames(frames.get(RIGHTIDLE));
					animation.setDelay(400);

				}
			}else{
				if(currentAction != LEFTIDLE){
					currentAction = LEFTIDLE;

					animation.setFrames(frames.get(LEFTIDLE));
					animation.setDelay(400);

				}
			}
		}
	}

	private void makeSfxJumpSound(){
		if(MenuState.isSfxSoundOn){
			sfx.get("jump").play();
		}
	}
	private void makeSfxPlusPointSound(){
		if(MenuState.isSfxSoundOn){
			sfx.get("plusPoint").play();
		}
	}
	
	private void makeSfxCrushSound(){
		if(MenuState.isSfxSoundOn){
			sfx.get("crush").play();
		}
	}
	private void makeSfxGameOverSound(){
		if(MenuState.isSfxSoundOn){
			sfx.get("GameOver").play();
		}
	}

	private void getNextPosition() {


		if(rightMove){

			dx += moveSpeed;

			if(dx > maxSpeed){
				dx = maxSpeed;

			}

		}
		else if(leftMove){
			dx  -= moveSpeed;
			if(dx < - maxSpeed){
				dx = -maxSpeed;
			}
		}
		else{
			if( dx > 0){
				dx -= stopSpeed;
				if(dx < 0){
					dx=0;
				}
			}else if(dx < 0){
				dx += stopSpeed;
				if(dx > 0){
					dx = 0;
				}
			}


		}

		if(jumping && stand){

			dy = jumpStart;
			falling = true;
		}

		if(falling){

			dy += fallSpeed;

			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;

			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}

		x+=dx;
		y+=dy;
	}

	public void draw(Graphics2D g){

		//setMapPosition();

		if(flinching){
			long elapsed = (System.nanoTime() - flinchingTimer) / 1000000;
			
			if( elapsed > 2100){
				flinching = false;
				flinchingTimer= 0;
			}
			if((elapsed> 0 && elapsed< 300) || (elapsed> 600 && elapsed< 900) ||(elapsed> 1200 && elapsed< 1500)||(elapsed> 1800 && elapsed< 2100)){				
				return;
			}
			
		}
		x= (x < 15 ? 20: x> 490? 470: x);
		
		g.drawImage(animation.getImage(),(int) x, (int)y, null);
		if(!Level1State.isRunning){
			if(isDownIntersect)
				g.drawImage(frames.get(6)[1], (int)x, (int) y, null);
			else
				g.drawImage(frames.get(6)[0], (int)x, (int) y, null);
			
			gameOverShow(g);
		}
		
	}

	private void gameOverShow(Graphics2D g) {
		
		point="Your Point = "+String.valueOf(points);
		
		g.setColor(Color.red);
		g.setFont(gameOvaerFont);
		g.drawString(gemaOver, 150, 280);
		
		g.setColor(Color.white);
		g.setFont(pointFont);
		g.drawString(point, 175, 320);
		

		
		g.setColor(Color.yellow);
		g.setFont(insFont);
		g.drawString(instruction, 100, 400);
	}

	public void setPosition(double x,double y){
		this.x = x;
		this.y = y;
	}

	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	public void setLeftMove(boolean leftMove) {
		this.leftMove = leftMove;
	}

	public static boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}
	public void setRightMove(boolean rightMove) {
		this.rightMove = rightMove;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public static boolean isJumping() {
		return jumping;
	}
	public static boolean isFalling() {
		return falling;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public Rectangle getCenterBounds(){
		return new Rectangle((int)x+1,(int)y+3,(int)(width-6),(int)(height-14));
	}

	public Rectangle getUpBounds(){
		return new Rectangle((int)x+2,(int)y+1,(int)(width-8),3);
	}

	public Rectangle getDownBounds(){
		return new Rectangle((int)x+5,(int)y+(height-4),(int)(width-17),4);
	}

	public void collisionDetection() {
		if(jumping|| stand){
			if(jumping){
				for(int j=2;j < Level1State.object.size(); j++){
					if(getUpBounds().intersects(Level1State.object.get(j).getBounds())){
						dy = 0;
						isDownIntersect = false;
					}
				}
			}
			if(getUpBounds().intersects(Level1State.object.get(0).getBounds())){
				//Level1State.isRunning= false;
				isDownIntersect = false;
				isFlinching();
				if(health==0){
					Level1State.isRunning=false;
					HighScore.setHighScore(points);
					flinching = false;
				}else{
					if(Level1State.object.get(5).id== 1)
						setPosition(	Level1State.object.get(5).x+25, Level1State.object.get(5).y-height);
					else
						setPosition(	Level1State.object.get(6).x+25, Level1State.object.get(6).y-height);
				}
					
			}
		}
		if(rightMove ){
			if(getCenterBounds().intersects(Level1State.object.get(1).getBounds())){
				x= 457;
				dx=0;
			}
		}else if(leftMove){
			if(getCenterBounds().intersects(Level1State.object.get(1).getBounds())){
				x= 23;
				dx=0;
			}

		}

		if(falling){
			for(int i=2;i<Level1State.object.size();i++){
				if(getDownBounds().intersects(Level1State.object.get(i).getBounds())){
					if(Level1State.object.get(i).id == 0){
						isFlinching();
						isDownIntersect = true;
						if(health==0){
							Level1State.isRunning=false;
							HighScore.setHighScore(points);
							flinching = false;
						}else
							setPosition(Level1State.object.get(i-1).x+25	, Level1State.object.get(i-1).y-height);
					}else{
						falling = false;
						stand= true;
						dy = Level1State.object.get(i).incrementY;
						y = Level1State.object.get(i).y-height+1;
					}
				}

			}

			if(getDownBounds().intersects(Level1State.object.get(0).getBounds())){
				//Level1State.isRunning= false;
				isFlinching();
				isDownIntersect = true;
				if(health==0){
					Level1State.isRunning=false;
					HighScore.setHighScore(points);
					flinching = false;
				}else{
					if(Level1State.object.get(5).id== 1)
						setPosition(	Level1State.object.get(5).x+25, Level1State.object.get(5).y-height);
					else
						setPosition(	Level1State.object.get(6).x+25, Level1State.object.get(6).y-height);
				}
					
			}
		}
		falling = true;
		stand = false;
		for(int i=2;i<Level1State.object.size();i++){
			if(getDownBounds().intersects(Level1State.object.get(i).getBounds())){
				falling = false;
				stand = true;
			}
			if(getCenterBounds().intersects(Level1State.object.get(i).getBounds())){
				dx = 0;
			}
		}
		if(Level1State.point.getBounds().intersects(getDownBounds())||Level1State.point.getBounds().intersects(getCenterBounds())){
			Level1State.point.isLifePointDraw=0;
			makeSfxPlusPointSound();
			points += extraPoint;
			Level1State.point.y =-1;
		}
		if(Level1State.life.getBounds().intersects(getDownBounds())||Level1State.life.getBounds().intersects(getCenterBounds())){
			Level1State.life.isLifePointDraw=0;
			makeSfxPlusPointSound();
			points += extraPoint;
			Level1State.life.y=-1;
			health++;
			
			if(health>maxHealth){
				health=maxHealth;
			}
		}
	}

	private void isFlinching() {
		if(flinching){
			return;
		}else{
			makeSfxCrushSound();
			health --;
			if(health== 0){
				makeSfxGameOverSound();
				GameStateManager.bgLevel1Music.stop();
			}
				
			flinchingTimer= System.nanoTime();
			flinching = true;
		}
	}
}
