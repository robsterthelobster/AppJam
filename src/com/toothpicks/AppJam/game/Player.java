package com.toothpicks.AppJam.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.SurfaceView;

public class Player {
	
	private SurfaceView view;
	private Paint paint;
	private PointF start, finish;
	private RectF finishBox, hitBox;
	private Bitmap imagePlayer, imageGoal;
	
	private int size = 50;
	private int playerVariable, goalVariable;
	
	private int fatFinger = 20;
	
	public boolean finished;
	
	public Player(PointF start, PointF finish, SurfaceView view){
		this.start  = start;
		this.finish = finish;
		this.view   = view;
		init();
	}
	
	public void init(){
		//paint sets the color
		paint = new Paint();
		paint.setColor(Color.RED);
		
		finished = false;
		
		hitBox    = new RectF( start.x,  start.y,  start.x + size,  start.y + size);
		finishBox = new RectF(finish.x, finish.y, finish.x + 80  , finish.y + 80  );
		
		//setting player image
		playerVariable = view.getResources().getIdentifier( "player", "drawable", view.getContext().getPackageName() );
		imagePlayer = BitmapFactory.decodeResource( view.getResources(), playerVariable );
		imagePlayer = Bitmap.createScaledBitmap( imagePlayer, size, size, true );
		
		//goal image
		goalVariable = view.getResources().getIdentifier( "player_goal", "drawable", view.getContext().getPackageName() );
		imageGoal = BitmapFactory.decodeResource( view.getResources(), goalVariable );
		imageGoal = Bitmap.createScaledBitmap( imageGoal, size, size, true );
	}

	public void draw(Canvas canvas){
		//draws player
		canvas.drawBitmap(imagePlayer, hitBox.left, hitBox.top, null);
		
		//draws goal
		canvas.drawBitmap(imageGoal, finishBox.left, finishBox.top, null);
	}
	
	public void update(PointF mouse){
		setLocation(mouse);
		if(intersects(finishBox)){
			finished = true;
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
	
	private void setLocation(PointF mouse){
		float minx = max( 0F, mouse.x - size / 2F );
		float miny = max( 0F, mouse.y - size / 2F );
		float maxx = min( (float)view.getWidth()  - 1, mouse.x + size / 2F );
		float maxy = min( (float)view.getHeight() - 1, mouse.y + size / 2F );
		
		hitBox.set(minx, miny, maxx, maxy);
	}
	
	public boolean intersects(PointF mouse){
		return(		mouse.x >= hitBox.left - fatFinger && mouse.x < hitBox.right  + fatFinger &&
					mouse.y >= hitBox.top  - fatFinger && mouse.y < hitBox.bottom + fatFinger   );	
	}
	
	public boolean intersects(RectF rect){
		return hitBox.intersect(rect);
	}
	
	public void reset(){
		init();
	}
	
	public boolean isFinished(PointF mouse){
		return (mouse.x > finishBox.left && mouse.x < finishBox.right && mouse.y > finishBox.top && mouse.y < finishBox.bottom);
	}
}
