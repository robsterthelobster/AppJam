package com.toothpicks.AppJam.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.SurfaceView;

import com.toothpicks.AppJam.panels.GamePanel;

public class Tile {
	
	public static int tileSize;
	
	private SurfaceView view;
	private char letter;
	private int tileNum;	
	private boolean isFlipped;
	
	// Front is what's seen when flipped
	// The back is common to all tiles, so is made static
	private Bitmap imageFront;
	private static Bitmap imageBack;
	
	public Tile(char letter, SurfaceView view){
		this.letter = letter;
		this.view   = view;
		init();
	}
	
	private void init() {
		tileSize  = (int)GamePanel.width / 6;
		isFlipped = false;

		int imageConstant = view.getResources().getIdentifier( "tile_" + letter, "drawable", view.getContext().getPackageName() );
		imageFront = BitmapFactory.decodeResource( view.getResources(), imageConstant );

		imageConstant = view.getResources().getIdentifier( "tile_blank", "drawable", view.getContext().getPackageName() );
		imageBack = BitmapFactory.decodeResource( view.getResources(), imageConstant );
	}
	
	public int getTile(){
		return tileNum;
	}
	
	public void flip(){
		isFlipped = true;
	}
	
	public void draw(Canvas canvas, PointF location){

		// Only scale bitmaps when needed
		if ( imageFront.getWidth() != tileSize ) {
			imageFront = Bitmap.createScaledBitmap( imageFront, tileSize, tileSize, true );
			imageBack  = Bitmap.createScaledBitmap(  imageBack, tileSize, tileSize, true );		
		}
		
		if ( isFlipped )
			canvas.drawBitmap( imageFront, location.x, location.y, null );
		else 
			canvas.drawBitmap(  imageBack, location.x, location.y, null );
	}
}
