package com.example.musicplayer;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PlayList extends Activity 
{
	ListView songPlayList;
	Uri uri;
	ArrayList<HashMap<String, String>> songlist=new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String, String>> songlistData=new ArrayList<HashMap<String,String>>();
	private ArrayList<HashMap<String, String>> songs_list = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> songpath=new ArrayList<HashMap<String,String>>();
	ListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.songlist);
		
		songPlayList=(ListView) findViewById(R.id.songlist);
		/*
		Intent it=new Intent(this,SongList.class);
		startActivity(it);
		finish();
		
		*/
		//SongList list=new SongList();
		this.songlist=playList1();
		
		for(int i=0;i<this.songlist.size();i++)
		{
			HashMap<String , String> song=this.songlist.get(i);
			songlistData.add(song);			
		}
		
		adapter=new SimpleAdapter(this, songlistData, R.layout.listlayout,new String[]{"song_title"} ,new int[]{R.id.songtitle});
		
		songPlayList.setAdapter(adapter);
		
		
		songPlayList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,long id) 
			{
				int song_index=position;
				String title=songlist.get(song_index).get("song_title");
				String path=songlist.get(song_index).get("song_path");
				String album=songlist.get(song_index).get("album");
				Log.d("TAG","Song Path is : "+path);
				
				Intent it=new Intent(PlayList.this,Mplayer.class);
				it.putExtra("title", title);
				it.putExtra("song_path", path);
				it.putExtra("album", album);
				setResult(1, it);
				finish();
			}
		});
		
	}
	

	public ArrayList<HashMap<String, String>> playList1() {
		
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				ContentResolver cr = getApplicationContext().getContentResolver();

				
				uri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
			
				String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
				
				String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
				
				String[] projection = { MediaStore.Audio.Media._ID,
						MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE,
						MediaStore.Audio.Media.DATA,
						MediaStore.Audio.Media.DISPLAY_NAME,
						MediaStore.Audio.Media.DURATION 
							
				};

				Cursor cursor = cr.query(
						uri, projection,
						selection, null, sortOrder);

				String path,name,album,artist;

				if (cursor.getCount() != 0) {
					cursor.moveToFirst();
					do{
						path = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DATA));
						name = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
						album= cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.ALBUM));
						
						
						
						HashMap<String, String> songs = new HashMap<String, String>();

						songs.put("song_title",name);
						songs.put("song_path", path);
						songs.put("album", album);
						Log.d("names","title :"+name+" path :"+path);
						/*
						 * + "||" + cursor.getString(1) + "||" + cursor.getString(2) +
						 * "||" + cursor.getString(3) + "||" + cursor.getString(4) +
						 * "||" + cursor.getString(5));
						 */
						songs_list.add(songs);
					}while (cursor.moveToNext()) ;
					Log.d("TAG", "length of file:" + songs_list.size());
					cursor.close();
				} else {
					Log.d("TAG", "length of file:" + songs_list.size());
				}
				

				
			}
		});
		
		return songs_list;
	}


}
