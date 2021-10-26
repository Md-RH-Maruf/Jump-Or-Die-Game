package com.example.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.TileObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.example.main.GameStateManager;
import com.example.process.AudioPlayer;
import com.example.tileMap.Background;

public class MenuState extends GameState{

	private Background bg;

	private int currentChoice=0;
	private String[] options={"Start","Help","Settings","High Score","Quit"};
	private Color titleColor;
	private Font titleFont;
	private Font font;

	
	public static AudioPlayer sfxSound;
	public static boolean isBgMusicOn;
	public static boolean isSfxSoundOn;

	private File file;
	private Scanner sc;
	private StringTokenizer tkn;

	public MenuState(GameStateManager gameSManager){

		this.gameSManager= gameSManager;

		try{
			bg = new Background("/Background/mainBackground.jpg", 0);
			bg.setVector(0,0,0, 0);

			titleColor = new Color(128,0,0);
			titleFont = new Font("Century Gthic",Font.PLAIN, 48);
			font = new Font("Arial" ,Font.PLAIN,22);

			soundSelection();
			if(MenuState.isBgMusicOn){
				gameSManager.bgMenuMusic.play();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void soundSelection() {
		try {
			
			sfxSound = new AudioPlayer("/audio/Click.mp3");
			file = new File("Resources/TextFile/Settings.txt");

			sc = new Scanner(file);
			
			for(int i=0;sc.hasNextLine();i++){
				tkn=new StringTokenizer(sc.nextLine());
				tkn.nextToken();
				if(i== 0){
					if(tkn.nextToken().equals("On"))
						isBgMusicOn =true;
					else 
						isBgMusicOn = false;
					
					
				}else if(i == 1){
					if(tkn.nextToken().equals("On"))
						isSfxSoundOn =true;
					else 
						isSfxSoundOn = false;
				}
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void makeSfxSound(){
		if(isSfxSoundOn){
			sfxSound.play();
		}
	}

	public void init() {

	}
	public void update() {
		bg.update();
	}
	public void draw(Graphics2D g) {
		bg.draw(g);

		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Jump Or Die", 135, 190);

		g.setFont(font);

		for(int i=0 ; i < options.length;i++){
			if(i == currentChoice){
				g.setColor(Color.black);
			}else{
				g.setColor(Color.red);
			}
			g.drawString(options[i], 220, 230+i*25);
		}
	}

	private void select(){
		if(currentChoice ==0){
			gameSManager.setState(GameStateManager.LEVEL1STATE);
		}else if(currentChoice == 1){
			gameSManager.setState(GameStateManager.HELP);
		}else if(currentChoice == 2){
			gameSManager.setState(GameStateManager.SETTINGS);
		}
		else if(currentChoice == 3){
			gameSManager.setState(GameStateManager.HIGHSCORE);
		}else if(currentChoice == 4){
			System.exit(0);
		}
	}
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			makeSfxSound();
			select();
		}

		if(k == KeyEvent.VK_UP){
			makeSfxSound();
			currentChoice --;
			if(currentChoice == -1){
				currentChoice = options.length - 1;
			}
		}

		if(k == KeyEvent.VK_DOWN){
			currentChoice++;
			makeSfxSound();
			if(currentChoice == options.length){
				currentChoice = 0;
			}
		}

	}
	public void keyReleased(int k) {		
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
