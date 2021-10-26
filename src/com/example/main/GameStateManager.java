package com.example.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.logging.Level;


import com.example.object.Shelf;
import com.example.process.AudioPlayer;
import com.example.state.Cloud;
import com.example.state.GameState;
import com.example.state.Level1State;
import com.example.state.MenuState;
import com.example.tileMap.Help;
import com.example.tileMap.HighScore;
import com.example.tileMap.Settings;
import com.example.window.GameWindow;

public class GameStateManager {


	public GameState[] gameState;
	private int currentState;

	public static final int MENUSTATE=0;
	public static final int CLOUDSTATE=1;
	public static final int HELP=2;
	public static final int LEVEL1STATE=3;
	public static final int SETTINGS= 4;
	public static final int HIGHSCORE=5;

	public static AudioPlayer bgMenuMusic;
	public static AudioPlayer bgLevel1Music;
	public GameStateManager(){

		gameState = new GameState[6];

		currentState = MENUSTATE;

		loadSound();
		loadState(currentState);
		
	}

	private void loadSound(){
		bgMenuMusic= new AudioPlayer("/audio/GameMenu.mp3");
		bgLevel1Music=new AudioPlayer("/audio/BackgroundMusic.mp3");
	
	}

	public void update(){
		try{

			gameState[currentState].update();
			gameState[CLOUDSTATE].update();



		}catch(Exception e){
			//e.printStackTrace();
		}
	}

	public void draw(Graphics2D g){

		try{

			gameState[currentState].draw(g);
			gameState[CLOUDSTATE].draw(g);
			if(currentState==LEVEL1STATE){
				Level1State.shelfDraw(g);
				Level1State.wall.draw(g);
				Level1State.saw.draw(g);
				Level1State.player.draw(g);
				Level1State.pointTable.draw(g);
				Level1State.point.draw(g);
				Level1State.life.draw(g);
			}
			else if(currentState == HELP){
				Help.drawHelp(g);
			}else if(currentState == SETTINGS){
				Settings.SettingsDraw(g);
			}else if(currentState == HIGHSCORE){
				HighScore.drawHighScore(g);
			}

		}catch(Exception e){
			//e.printStackTrace();
		}

	}
	private void loadState(int state) {
		
		if(state == MENUSTATE){
			gameState[state] =new MenuState(this);
			gameState[CLOUDSTATE]=new Cloud(this);
		}
		else if( state == LEVEL1STATE){
			bgMenuMusic.stop();
			Level1State.temIncrimentY =-1.3;
			gameState[state] = new Level1State(this);
		}else if( state == HELP){
			gameState[state] = new Help(this);
		}else if( state == SETTINGS){
			gameState[state] =new Settings(this);
		}else if( state == HIGHSCORE){
			gameState[state] = new HighScore(this);
		}
	}

	private void unloadState(int state){
		gameState[state]=null;
		//gameState[2]=null;
	}

	public void setState(int state){
		unloadState(currentState);
		currentState = state;
		//System.out.println("state===="+state);
		loadState(currentState);
	}

	public void keyPressed(int k){
		gameState[currentState].keyPressed(k);
	}
	public void keyReleased(int k){
		gameState[currentState].keyReleased(k);
	}
}
