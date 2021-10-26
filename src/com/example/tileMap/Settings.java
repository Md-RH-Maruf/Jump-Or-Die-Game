package com.example.tileMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import com.example.main.GameStateManager;
import com.example.state.GameState;
import com.example.state.MenuState;

public class Settings extends GameState{

	private static BufferedImage image;
	
	private boolean isMusicOn;
	private boolean isSfxOn;
	
	private static String music;
	private static String sfx;
	
	private File file;
	private Scanner sc;
	private StringTokenizer tkn;
	
	
	private PrintWriter pw;
	private static int currentSelection;
	
	
	private Background bg;
	public Settings(GameStateManager gsm){
		this.gameSManager = gsm;
		init();
	}
	
	public void init() {
		try {
			file= new File("Resources/TextFile/Settings.txt");
			sc = new Scanner(file);
			
			
			for(int i=0;sc.hasNextLine();i++){
				tkn = new StringTokenizer(sc.nextLine());
				tkn.nextToken();
				if(i==0){
					music= tkn.nextToken();
					
				}
					
				else if(i== 1){
					sfx = tkn.nextToken();
				}
					
			}
			image= ImageIO.read(getClass().getResourceAsStream("/Background/settings1.png"));
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


	public static void SettingsDraw(Graphics2D g){
		g.setFont(new Font("Traditional Arabic",Font.BOLD,20));
		g.setColor(Color.black);
		g.drawImage(image, 135, 173+(50*currentSelection), null);
		
		g.drawString("Music            "+music, 170, 200);
		g.drawString("SFX              "+sfx, 170, 250);
	}
	public void keyPressed(int k) {
		if(k== KeyEvent.VK_UP || k == KeyEvent.VK_DOWN){
			MenuState.makeSfxSound();
			currentSelection = (currentSelection==0 ? 1 : 0);
		}
		else if( k == KeyEvent.VK_RIGHT || k== KeyEvent.VK_LEFT){
			if(currentSelection == 0){
				music = (music.equals("On")? "Off" :"On");
				if(music.equals("On")){
					MenuState.isBgMusicOn = true;
					gameSManager.bgMenuMusic.play();
				}else{
					gameSManager.bgMenuMusic.stop();
					MenuState.isBgMusicOn = false;
				}
					
			}
			else{
				sfx = (sfx.equals("On")? "Off" :"On");
				if(sfx.equals("On")){
					MenuState.isSfxSoundOn = true;
					MenuState.makeSfxSound();
				}else
					MenuState.isSfxSoundOn = false;
			}
				
		}
		else if( k== KeyEvent.VK_BACK_SPACE){
			try {
				pw =new PrintWriter("Resources/TextFile/Settings.txt");
				pw.write("music: "+music+"\n"+"sfx: "+sfx);
				pw.close();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
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
