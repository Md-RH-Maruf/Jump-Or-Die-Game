package com.example.process;

import java.awt.image.BufferedImage;

import com.example.object.Player;
import com.example.state.Level1State;

public class Animation {

	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	public Animation(){
		playedOnce= false;
	}
	
	public void update(){
		
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) /1000000;
		
		if(elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(Player.isFalling()||Player.isJumping()){
			if(Player.isFacingRight()){
				currentFrame=0;
			}else
				currentFrame=1;
		}
		
		if(currentFrame == frames.length){
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public int getCurrentFrame(){ return currentFrame;	}
	
	public BufferedImage getImage(){return frames[currentFrame];}
	
	public boolean hasPlayedOnce(){ return playedOnce; }
	
	
	
}
