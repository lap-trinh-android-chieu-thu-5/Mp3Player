package com.example.mp3player.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.mp3player.Adapter.MainViewPaggerAdapter;
import com.example.mp3player.Fragment.Fragment_Controller_Service;
import com.example.mp3player.Fragment.Fragment_Home;
import com.example.mp3player.Fragment.Fragment_Local;
import com.example.mp3player.Fragment.Fragment_Local_Album;
import com.example.mp3player.Fragment.Fragment_Local_Album_Main;
import com.example.mp3player.Fragment.Fragment_Local_Artist;
import com.example.mp3player.Fragment.Fragment_Local_Artist_Main;
import com.example.mp3player.Fragment.Fragment_Local_Favorite;
import com.example.mp3player.Fragment.Fragment_Local_Playlist;
import com.example.mp3player.Fragment.Fragment_Local_Playlist_Main;
import com.example.mp3player.Fragment.Fragment_Local_Song_List;
import com.example.mp3player.Fragment.Fragment_Search;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.List;

public class LocalActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Fragment_Controller_Service mFragmentControllerService;

    private Fragment_Local_Playlist_Main fragment_local_playlist_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_local);


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Intent truyền sang.
        Intent intent = this.getIntent();
        String fragmentOpen= intent.getStringExtra("fragment_open");

        map();
        init(fragmentOpen);
    }

    private  void init(String fragmentOpen){
        if(MainActivity.serviceMusicPlayer != null){
            mFrameLayout.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mFragmentControllerService = new Fragment_Controller_Service();
            fragmentTransaction.add(R.id.frame_controller_service_main, mFragmentControllerService);
            fragmentTransaction.commit();

            MainActivity.serviceMusicPlayer.context = this;
        }else{
            mFrameLayout.setVisibility(View.GONE);
        }


        MainViewPaggerAdapter mainViewPaggerAdapter = new MainViewPaggerAdapter(getSupportFragmentManager());

        fragment_local_playlist_main = new Fragment_Local_Playlist_Main();
        mainViewPaggerAdapter.addFragment(new Fragment_Local_Song_List(), "");
        mainViewPaggerAdapter.addFragment(fragment_local_playlist_main,"");
        mainViewPaggerAdapter.addFragment(new Fragment_Local_Favorite(), "");
        mainViewPaggerAdapter.addFragment(new Fragment_Local_Artist_Main(),"");
        mainViewPaggerAdapter.addFragment(new Fragment_Local_Album_Main(), "");


        mViewPager.setAdapter(mainViewPaggerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.iconlocalmusic);
        mTabLayout.getTabAt(1).setIcon(R.drawable.iconlocalplaylist);
        mTabLayout.getTabAt(2).setIcon(R.drawable.iconlocallove);
        mTabLayout.getTabAt(3).setIcon(R.drawable.iconlocalsinger);
        mTabLayout.getTabAt(4).setIcon(R.drawable.iconlocalalbum);

        setShowPagger(fragmentOpen);

    }

    private void map(){
        mTabLayout = findViewById(R.id.myTabLayout);
        mViewPager = findViewById(R.id.myViewPager);
        mFrameLayout = findViewById(R.id.frame_controller_service);

        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLocalActivity = new Intent(LocalActivity.this, PlayMusicActivity.class);
                intentLocalActivity.putExtra("type_play", "continue");

                LocalActivity.this.startActivity(intentLocalActivity);
            }
        });
    }

    private void setShowPagger(String fragmentOpen){
        switch (fragmentOpen){
            case "song_list":{
                this.mViewPager.setCurrentItem(0);
                break;
            }
            case "playlist_list":{
                this.mViewPager.setCurrentItem(1);
                break;
            }
            case "favorite_list":{
                this.mViewPager.setCurrentItem(2);
                break;
            }
            case "artist_list":{
                this.mViewPager.setCurrentItem(3);
                break;
            }

            case "album_list":{
                this.mViewPager.setCurrentItem(4);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            this.fragment_local_playlist_main.onLoadData();
        }
    }
}
