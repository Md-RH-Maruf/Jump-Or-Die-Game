package com.example.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LifePoint {

	private int id;
	public double x;
	public double y;
	private double dx;
	
	public int isLifePointDraw;
	private BufferedImage[] image=new BufferedImage[3];
	public LifePoint(int id){
		try {
			this.id = id;
			image[0]= ImageIO.read(getClass().getResourceAsStream("/Objects/pointTable.png"));
			image[1]= image[0].getSubimage(0, 20, 23, 22);
			image[2]= image[0].getSubimage(22, 20, 24, 22);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPostion(double x,double y){
		if(x>480 || x<20){
			x = (x>500? 460: 20);
		}
		
		this.x = x;
		this.y = y;
	}
	public void update(double dy){
		
		
		if(y>0){

			y += dy;
			//System.out.println("y"+id+"="+y);
		}
			
	}
	public void draw(Graphics2D g){
		
		
		if(isLifePointDraw==1){
			if(id==1){
				g.drawImage(image[1],(int)	 x, (int)y, null);
			}else
				g.drawImage(image[2],(int)	 x, (int)y, null);
		}
		
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x+2,(int)y+2,18,18);
	}
}
