package com.example.mp3player.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mp3player.Adapter.MainViewPaggerAdapter;
import com.example.mp3player.Fragment.Fragment_Home;
import com.example.mp3player.Fragment.Fragment_Local;
import com.example.mp3player.Fragment.Fragment_Local_Album;
import com.example.mp3player.Fragment.Fragment_Local_Artist;
import com.example.mp3player.Fragment.Fragment_Local_Favorite;
import com.example.mp3player.Fragment.Fragment_Local_Playlist;
import com.example.mp3player.Fragment.Fragment_Local_Song_List;
import com.example.mp3player.Fragment.Fragment_Search;
import com.example.mp3player.R;

public class LocalActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_local);

        // Intent truyền sang.
        Intent intent = this.getIntent();
        String fragmentOpen= intent.getStringExtra("fragment_open");

        map();
        init(fragmentOpen);

    }

    private  void init(String fragmentOpen){
        MainViewPaggerAdapter mainViewPaggerAdapter = new MainViewPaggerAdapter(getSupportFragmentManager());

        mainViewPaggerAdapter.addFragment(new Fragment_Local_Song_List(), "");
        mainViewPaggerAdapter.addFragment(new Fragment_Local_Playlist(),"");
        mainViewPaggerAdapter.addFragment(new Fragment_Local_Favorite(), "");
        mainViewPaggerAdapter.addFragment(new Fragment_Local_Artist(),"");
        mainViewPaggerAdapter.addFragment(new Fragment_Local_Album(), "");


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
    }

    private void setShowPagger(String fragmentOpen){
        switch (fragmentOpen){
            case "song_list":{
                this.mViewPager.setCurrentItem(0);
                break;
            }
            case "playlist":{
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

            case "album":{
                this.mViewPager.setCurrentItem(4);
                break;
            }
        }
    }

}
