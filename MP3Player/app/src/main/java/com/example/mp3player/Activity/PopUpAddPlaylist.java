package com.example.mp3player.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.example.mp3player.Adapter.SelectPlaylistAdapter;
import com.example.mp3player.Interface.OnFinishPopUp;
import com.example.mp3player.Model.Local.Playlist;
import com.example.mp3player.R;

import java.util.List;

public class PopUpAddPlaylist extends Activity implements OnFinishPopUp {
    private RecyclerView mRecyclerView;
    private String mSongId;
    private List<Playlist> mLstPlaylist;
    private SelectPlaylistAdapter mSelectPlaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_add_playlist);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.4), (int)(height*.3));



        // Intent truyền sang.
        Intent intent = this.getIntent();
        String songId = intent.getStringExtra("song_id");
        this.mSongId = songId;
        map();
        init();
    }
    private  void map(){
        this.mRecyclerView = findViewById(R.id.recyclerview_playlist_list_select);

    }
    private  void init(){
        mLstPlaylist = Playlist.listAll(Playlist.class);
        mSelectPlaylistAdapter = new SelectPlaylistAdapter(this, mLstPlaylist, mSongId);
        mSelectPlaylistAdapter.setOnFinish(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSelectPlaylistAdapter);
    }

    @Override
    public void OnFinish() {
        finish();
    }
}
