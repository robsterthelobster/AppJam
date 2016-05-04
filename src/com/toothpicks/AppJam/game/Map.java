package com.toothpicks.AppJam.game;

import com.toothpicks.AppJam.panels.GamePanel;

import android.graphics.PointF;

public class Map {
	
	public int background;
	
	PointF start, end;
	
	char letter;
	
	public Map(char letter){
		this.letter = letter;
		
		init();
	}
	
	public void init(){
		start = new PointF(0,0);
		end   = new PointF(0,0);
		//background = mapSelection();
		
		//temp start/end locations
		setPoints();
	}
	
	public String getLetter(){
		return "" + letter;
	}
	
	public void setPoints(){
		float width = GamePanel.width;
		float height = GamePanel.height;
		
		switch(letter){
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
			start.set(width-Tile.tileSize, height/8);
			end.set(width-Tile.tileSize, height-Tile.tileSize);
			break;
		case 'f':
		case 'g':
		case 'h':
			start.set(0, height/8);
			end.set(width-Tile.tileSize, height/8);
			break;
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
			start.set(Tile.tileSize/2, 2*height/8);
			end.set(0, height/8);
			break;
		case 'p':
		case 'q':
		case 'r':
			start.set(0, height-Tile.tileSize);
			end.set(width-Tile.tileSize, height-Tile.tileSize);
			break;
		case 's':
			start.set(5*width/6, height/8);
			end.set(0, 9*height/10);
			break;
		case 't':
		case 'u':
		case 'v':
		case 'w':
			start.set(0, height/8);
			end.set(width-Tile.tileSize, height/8);
			break;
		case 'x':
		case 'y':
		case 'z':
		}
	}
	
}
