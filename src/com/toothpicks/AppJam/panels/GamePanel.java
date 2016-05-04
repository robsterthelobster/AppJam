package com.toothpicks.AppJam.panels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.toothpicks.AppJam.game.Player;
import com.toothpicks.AppJam.game.Tile;
import com.toothpicks.AppJam.game.Word;
import com.toothpicks.AppJam.utility.Panel;

public class GamePanel extends Panel{

	public static int width, height;
	
	private final static long SPLASH_DELAY = 4700;  // 4.7 seconds
	private static long beginTime;	// the time when the game started
	
	private PointF mouse;
	private RectF button;
	
	private Player player;
	private Word word;
	
	public boolean showSplashScreen;
	private Bitmap splashScreen, tileBG, map, mapBG, letterBG, picture;
	
	//determines if the player should reset
	//determines if the player moved
	private boolean reset, playerMoved;
	private boolean isPressed;
	
	public GamePanel(Context context) {
		super(context);
		init();
	}
		
	protected void init() {
		width  = getWidth();
		height = getHeight();
		button = new RectF( width - 100, height - 100, width, height );		

		word   = new Word("shower", this);
		player = new Player(word.getStart(), word.getFinish(), this);

		showSplashScreen = true;
		beginTime = System.currentTimeMillis();
		
		// Cache static resources up front
		int splashConstant = getResources().getIdentifier( "splashscreen", "drawable", getContext().getPackageName() );
		splashScreen = BitmapFactory.decodeResource( getResources(), splashConstant );
		
		int tileConstant = getResources().getIdentifier( "bg_tile", "drawable", getContext().getPackageName() );
		tileBG = BitmapFactory.decodeResource( getResources(), tileConstant );
		
		int mapConstant = getResources().getIdentifier( "bg_appjam", "drawable", getContext().getPackageName() );
		mapBG = BitmapFactory.decodeResource( getResources(), mapConstant );

		int pictureConstant = getResources().getIdentifier( "shower", "drawable", getContext().getPackageName() );
		picture = BitmapFactory.decodeResource( getResources(), pictureConstant );
		
		updateBitmaps();
	}

	// Update bitmap resources as needed
	private void updateBitmaps() {
			
		// Update maze black and white map
		int mapConstant = getResources().getIdentifier( "map_" + word.getCurrentLetter(), "drawable", getContext().getPackageName() );
		map = BitmapFactory.decodeResource( getResources(), mapConstant );
	
		// Update maze letter outline
		int letterConstant = getResources().getIdentifier( "trans_" + word.getCurrentLetter(), "drawable", getContext().getPackageName() );
		letterBG = BitmapFactory.decodeResource( getResources(), letterConstant );		
	}	
	
	// the fps to be displayed
	private String avgFps;
	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}

	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, getHeight()-20, paint);
		}
	}
	
	public void render(Canvas canvas){		

		// Only scale bitmaps when needed
		if ( letterBG.getWidth() != width || letterBG.getHeight() != height ) {
			map      = Bitmap.createScaledBitmap( map     , width, height       , true );
			mapBG    = Bitmap.createScaledBitmap( mapBG   , width, height       , true );
			letterBG = Bitmap.createScaledBitmap( letterBG, width, height       , true );
			tileBG   = Bitmap.createScaledBitmap( tileBG  , width, Tile.tileSize, true );
		}

		if ( showSplashScreen == true ) {
			if ( System.currentTimeMillis() - beginTime < SPLASH_DELAY ) {
				splashScreen = Bitmap.createScaledBitmap( splashScreen, width, height, true );
				canvas.drawBitmap(splashScreen, 0, 0, null);
				return;
			}
			else
				showSplashScreen = false;
		}

		canvas.drawBitmap(mapBG   , 0, 0, null);
		canvas.drawBitmap(tileBG  , 0, 0, null);
		
		if ( word.isCompleted() == false ) {
			canvas.drawBitmap(letterBG, 0, 0, null);
			player.draw(canvas);		
		}
		else {
			picture = Bitmap.createScaledBitmap( picture, width, height - 100, true );
			canvas.drawBitmap(picture, 0, 100, null);
		}
		word.draw(canvas);
		
		// display fps
		// canvas.drawColor(Color.BLACK);  // uncomment for debugging
		// displayFps(canvas, avgFps);		
	}
	
	public void update(){
		//resets
		if(reset){
			reset = false;
			player.reset();
		}
		
		//updates player
		if(playerMoved){
			player.update(mouse);
			playerMoved = false;
		}
	}
	
	private float max( float a, float b ) {
		if ( a > b )
			return a;
		else
			return b;
	}
	
	private float min( float a, float b ) {
		if ( a < b )
			return a;
		else
			return b;
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		
		mouse = new PointF( event.getX(), event.getY() );
		
		//when clicked
		if( event.getAction() == MotionEvent.ACTION_DOWN ){
			//if the mouse touches the YELLOW button
			//move to next letter
			if(		mouse.x > button.left && mouse.x < button.right &&
					mouse.y > button.top  && mouse.y < button.bottom)
			{
					word.nextLetter();
					player.reset();
					player = new Player(word.getStart(), word.getFinish(), this);
					updateBitmaps();
			}
			//if the mouse touches the player
			//then isPressed = true; for dragging
			if( player.intersects(mouse) ){
				isPressed = true;
			}
		}
		
		//if the mouse is pressed and moving
		if( event.getAction() == MotionEvent.ACTION_MOVE ){
			//if isPressed
			//the set new player location
			//if new location is black, reset player
			if(isPressed) {
				int x = (int) min( max( 0F, mouse.x ), width  - 1 );
				int y = (int) min( max( 0F, mouse.y ), height - 1 );
				if(	map.getPixel(x, y) == Color.BLACK ) {
					reset = true;
					isPressed = false;
				}
				else if( player.isFinished(mouse) ){
					isPressed = false;
					word.nextLetter();
					player.reset();
					player = new Player(word.getStart(), word.getFinish(), this);
					updateBitmaps();					
				}
				else
					playerMoved = true;
			}
		}
		
		//mouse released, reset isPressed
		if( event.getAction() == MotionEvent.ACTION_UP ){
			isPressed = false;
		}
		return true;
	}
}
