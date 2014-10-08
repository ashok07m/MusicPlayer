package com.example.musicplayer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class SongList extends Activity {

	final String song_path = new String("/sdcard/");
	private ArrayList<HashMap<String, String>> songs_list = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		
	}
	public ArrayList<HashMap<String, String>> playlist() {
		File FilePath = new File(song_path);

		if (FilePath.listFiles(new Mp3filter()).length > 0) {
			HashMap<String, String> songs = new HashMap<String, String>();

			for (File file : FilePath.listFiles(new Mp3filter())) {
				songs.put("song_title",
						file.getName()
								.substring(0, file.getName().length() - 4));
				songs.put("song_path", file.getPath());

				songs_list.add(songs);
			}
		}

		return songs_list;
	}

	class Mp3filter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String filename) {

			return (filename.endsWith(".mp3")) || (filename.endsWith(".MP3"));
		}
	}

}
