package com.toothpicks.AppJam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.toothpicks.AppJam.music.BackgroundSoundService;
import com.toothpicks.AppJam.panels.GamePanel;
import com.toothpicks.AppJam.utility.Panel;

public class Main extends Activity {

	private Panel game;
	private Intent svc;
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Remove title and notification bars
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // Kick off background music
        svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);
               
        // Kick off game
        game = new GamePanel(this);
        setContentView(game);
    }
        
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
      super.onSaveInstanceState(savedInstanceState);
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
      super.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
    public void onPause() {
    	super.onPause();    	
    	stopService(svc);
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	stopService(svc);
    }
    
}