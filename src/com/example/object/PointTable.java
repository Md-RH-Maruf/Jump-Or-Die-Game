package com.example.object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PointTable {

	private BufferedImage[] image=new BufferedImage[4];
	private Font font=new Font("Serif",Font.BOLD,20);
	private Player p;
	public PointTable(Player p){
		this.p = p;
		init();
	}
	
	private void init(){
		try {
			image[0] = ImageIO.read(getClass().getResourceAsStream("/Objects/pointTable.png"));
			
			image[1]= image[0].getSubimage(0, 0, 500, 20);
			image[2]= image[0].getSubimage(0, 20, 23, 22);
			image[3]= image[0].getSubimage(22, 20, 24, 22);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g){
		
		g.drawImage(image[1], 0, 1, null);
		
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(String.valueOf(p.points)	, 27, 17);
		for(int i=0;i< p.health; i++){
			g.drawImage(image[3], 400+(i*32), 0,null);
		}
	}
	
	
}
