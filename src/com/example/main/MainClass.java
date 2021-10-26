package com.example.main;

import java.awt.BufferCapabilities;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.window.GameWindow;

public class MainClass extends JFrame {

	GameWindow ob_window;
	
	public static void main(String args[]){

		MainClass main=new MainClass();
		main.ob_window=new GameWindow();
		main.setContentPane(main.ob_window);
		
		
		main.setResizable(false);
		main.setVisible(true);
		
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.pack();
		main.setLocationRelativeTo(null);
	}

	
}
