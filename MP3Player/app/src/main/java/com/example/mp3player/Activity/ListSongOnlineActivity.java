package com.example.mp3player.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.mp3player.Adapter.OnlineSongListAdapter;
import com.example.mp3player.Model.Host.Playlist;
import com.example.mp3player.Model.Host.Song;
import com.example.mp3player.R;
import com.example.mp3player.Service.APIService;
import com.example.mp3player.Service.DataService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSongOnlineActivity extends AppCompatActivity {

    ImageView imageViewSongList;
    ArrayList<Song> songList;
    List<Playlist> lstPlaylist;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewSongList;
    FloatingActionButton floatingActionButton;
    OnlineSongListAdapter onlineSongListAdapter;
    Playlist playlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song_online);
        map();
        dateintent();
        init();
        if(playlist != null && !playlist.getTen().equals("")){
            setValueInView(playlist.getTen(), playlist.getHinhnen());
            getData(playlist.getId());
        }
    }

    private void setValueInView(String ten, String hinhnen) {
        collapsingToolbarLayout.setTitle(ten);
        try {
            URL url = new URL(hinhnen);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinhnen).into(imageViewSongList);
    }

    private void getData(String playlistId) {
        DataService dataService = APIService.getService();
        Call<List<Song>> callback = dataService.getListSongPlayList(playlistId);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songList = (ArrayList<Song>)response.body();
                onlineSongListAdapter = new OnlineSongListAdapter(ListSongOnlineActivity.this, songList);
                recyclerViewSongList.setLayoutManager(new LinearLayoutManager(ListSongOnlineActivity.this));
                recyclerViewSongList.setAdapter(onlineSongListAdapter);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                int b = 0;
            }
        });

    }

    private void dateintent() {
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("itemplaylist")){
                playlist = (Playlist) intent.getSerializableExtra("itemplaylist");
            }
        }
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);



        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chay nhac
                Intent intentLocalActivity = new Intent(ListSongOnlineActivity.this, PlayMusicActivity.class);
                intentLocalActivity.putExtra("type_play", "play_online_playlist_all");
                intentLocalActivity.putExtra("list_song", songList);
                startActivity(intentLocalActivity);
            }
        });
    }

    private void map(){
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbarlist);
        recyclerViewSongList = findViewById(R.id.recyclerview_song_list);
        floatingActionButton = findViewById(R.id.floatingactionbutton);
        imageViewSongList = findViewById(R.id.imageview_song_list);
    }
}
