package com.toothpicks.AppJam.game;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.SurfaceView;

public class Word {

	private String word;
	
	private SurfaceView view;
	private boolean isCompleted;
	private PointF wordLocation;
	private int currentLetter;
	
	private ArrayList<Map> maps;
	private ArrayList<Tile> letters;
	
	public Word(String word, SurfaceView view){
		this.word = word;
		this.view = view;		
		init();
	}
	
	//init or reset
	public void init(){
		wordLocation  = new PointF(0,0);		
		currentLetter = 0;
		
		maps    = new ArrayList<Map>();
		letters = new ArrayList<Tile>();

		for( char c: word.toCharArray() ){
			letters.add( new Tile(c, view) );
		}
		for( char c: word.toCharArray() ){
			maps.add( new Map(c) );
		}
	}

	public void draw(Canvas canvas) {
		PointF location = new PointF( wordLocation.x, wordLocation.y );
		for(Tile tile: letters){
			tile.draw(canvas, location);
			location.set( location.x + Tile.tileSize, location.y );
		}
	}
	
	public int getBackground(){
		return maps.get(0).background;
	}
	
	public void nextLetter(){
		if( maps.size() > 1 ) 
			maps.remove(0);
		else
			isCompleted = true;
		
		if( currentLetter < letters.size() ) {
			letters.get(currentLetter).flip();
			currentLetter++;
		}
	}
	
	public PointF getStart(){
		return maps.get(0).start;
	}
	
	public PointF getFinish(){
		return maps.get(0).end;
	}
	
	public boolean isCompleted(){
		return isCompleted;
	}
	
	public String getCurrentLetter(){
		return maps.get(0).getLetter();
	}
	
}
