package com.example.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Mplayer extends Activity implements OnClickListener ,OnCompletionListener
{
	ProgressBar songProgress;
	TextView currentTime,totalTime,songTitle,album;
	ImageButton prevoius,rewind,play,pause,forward,next;
	Button playlist;
	ImageView albumImage;
	
	static MediaPlayer mplayer;
	SongList songlist;
	
	ArrayList<HashMap<String, String>> songs=new ArrayList<HashMap<String,String>>();
	
	Handler progressHandler=new Handler();
	
	boolean pauseOver=false;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mplayer);
	
		songProgress=(ProgressBar) findViewById(R.id.song_progress);
		currentTime=(TextView) findViewById(R.id.current_time);
		totalTime=(TextView) findViewById(R.id.total_time);
		songTitle=(TextView) findViewById(R.id.songtitle);
		album=(TextView) findViewById(R.id.album);
		albumImage=(ImageView) findViewById(R.id.album_image);
		
		prevoius=(ImageButton) findViewById(R.id.previous);
		rewind=(ImageButton) findViewById(R.id.rewind);
		play=(ImageButton) findViewById(R.id.play);
		pause=(ImageButton) findViewById(R.id.pause);
		forward=(ImageButton) findViewById(R.id.forward);
		next=(ImageButton) findViewById(R.id.next);
		
		playlist=(Button) findViewById(R.id.playlist);
		
		prevoius.setOnClickListener(this);
		rewind.setOnClickListener(this);
		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		forward.setOnClickListener(this);
		next.setOnClickListener(this);
		
		playlist.setOnClickListener(this);
		
		mplayer=new MediaPlayer();
		//songlist=new SongList();
		
		//songs=songlist.playlist();
		
		//playSong(0);
	}

	void playSong(final String path)
	{
		try
		{
			Log.d("PATH","PATh OF SONG :"+path);
			mplayer.reset();
			mplayer.setDataSource(path);
			mplayer.prepare();
			mplayer.start();
			
			
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() 
				{
					//songTitle.setText(songs.get(index).get("song_title"));
					songProgress.setProgress(0);
					songProgress.setMax(mplayer.getDuration());
				}
			});
			
			updateSongProgress();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	void updateSongProgress()
	{
		progressHandler.postDelayed(progressThread, 100);
	}
	
	public Runnable progressThread=new Runnable() {
		
		@Override
		public void run() {
			
			int totalDuration=mplayer.getDuration();
			int cTime=mplayer.getCurrentPosition();
			
			String finalTime=milliSecondsToTimer(totalDuration);
			String presentTime=milliSecondsToTimer(cTime);
			
			currentTime.setText(presentTime);
			totalTime.setText(finalTime);
			songProgress.setProgress(cTime);
			
			progressHandler.postDelayed(this, 100);
		}
	}; 
	public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";
 
        // Convert total duration into time
           int hours = (int)( milliseconds / (1000*60*60));
           int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
           int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
           // Add hours if there
           if(hours > 0){
               finalTimerString = hours + ":";
           }
 
           // Prepending 0 to seconds if it is one digit
           if(seconds < 10){
               secondsString = "0" + seconds;
           }else{
               secondsString = "" + seconds;}
 
           finalTimerString = finalTimerString + minutes + ":" + secondsString;
 
        // return timer string
        return finalTimerString;
    }
 
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==1)
		{
			String song_name=data.getStringExtra("song_title");
			String song_path=data.getStringExtra("song_path");
			String song_album=data.getStringExtra("song_album");
			
			songTitle.setText(song_name);
			album.setText(song_album);
			
			playSong(song_path);
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		progressHandler.removeCallbacks(progressThread);
		pauseOver=true;
	}
	
	@Override
	protected void onStart() 
	{
		super.onStart();
		if(pauseOver)
		{
			progressHandler.postDelayed(progressThread, 100);
			pauseOver=false;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mplayer.stop();
		mplayer.release();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.mplayer, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.play :
				
				break;

			case R.id.pause :
				mplayer.pause();
				break;

			case R.id.previous :
				
				break;

			case R.id.next :
				
				break;

			case R.id.forward :
				
				break;

			case R.id.rewind :
				
				break;

			case R.id.playlist :
				Intent it=new Intent(Mplayer.this,PlayList.class);
				startActivityForResult(it, 1);
				break;
		}
		
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mplayer.stop();
		
	}

}
