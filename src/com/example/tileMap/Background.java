package com.example.tileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream.GetField;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.example.window.GameWindow;

public class Background {

	private BufferedImage image;

	private double x,y,dx,dy;

	private double moveScale;



	public Background(String s, double ms){

		try{
			
			image=ImageIO.read(getClass().getResourceAsStream(s));
			
			moveScale = ms;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();

		}
	}

	public void setPosition(double x, double y){
		this.x=(x * moveScale) % GameWindow.WIDTH;
		this.y=(y * moveScale) % GameWindow.HEIGHT;
	}

	public void setVector(int x,int y,double dx, double dy){

		this.x = x;
		this.y = y;

		this.dx = dx;
		this.dy = dy;
	}

	public void update(){

		x += dx;
		y += dy;
		if(x<-500){
			x=0;
		} 

		if(y<-600){
			y=0;
		}
	}

	public void draw(Graphics2D g){

		g.drawImage(image, (int)x, (int )y , null);

		if(x < 0){
			g.drawImage(image, (int) x+GameWindow.WIDTH	, (int)y, null);
		}

		if( x >0){
			g.drawImage(image, (int) x-GameWindow.WIDTH, (int )y	, null);
		}

		if( y >0){
			g.drawImage(image, (int) x, (int )y-GameWindow.HEIGHT	, null);
		}
		if( y <0){
			g.drawImage(image, (int) x, (int )y+GameWindow.HEIGHT	, null);
		}
	}
}
