package com.toothpicks.AppJam.utility;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class Panel extends SurfaceView implements SurfaceHolder.Callback {
	
	private GameThread thread;
	
	public Panel(Context context) {
		super(context);

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		thread = new GameThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
	    thread.start();
		init();
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}
	
	protected abstract void init();
	
	protected abstract void render(Canvas canvas);
	
	protected abstract void update();
	
	public abstract void setAvgFps(String avgFps);
	
}