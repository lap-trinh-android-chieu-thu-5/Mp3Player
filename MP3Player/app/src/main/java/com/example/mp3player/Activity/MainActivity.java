package com.example.mp3player.Activity;

import android.Manifest;
import android.app.Notification;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mp3player.Adapter.LocalSongListAdapter;
import com.example.mp3player.Adapter.MainViewPaggerAdapter;
import com.example.mp3player.Fragment.Fragment_Controller_Service;
import com.example.mp3player.Fragment.Fragment_Home;
import com.example.mp3player.Fragment.Fragment_Local;
import com.example.mp3player.Fragment.Fragment_Search;
import com.example.mp3player.Model.Host.Playlist;
import com.example.mp3player.Model.Local.Artist;
import com.example.mp3player.Model.Local.ScanLocalMusic;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.MusicPlayer.ServiceMusicPlayer;
import com.example.mp3player.R;
import com.example.mp3player.Service.APIService;
import com.example.mp3player.Service.DataService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private FrameLayout mFrameLayout;
    private Fragment_Controller_Service mFragmentControllerService;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static ServiceMusicPlayer serviceMusicPlayer;
    public static Notification notification;

    Fragment_Local fragment_local = new Fragment_Local();
    Fragment_Search fragment_search = new Fragment_Search();
    Fragment_Home fragment_home = new Fragment_Home();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map();
        //initPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private  void init(){
        if(MainActivity.serviceMusicPlayer != null){
            mTabLayout.setVisibility(View.GONE);

            mFrameLayout.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mFragmentControllerService = new Fragment_Controller_Service();
            fragmentTransaction.add(R.id.frame_controller_service_main, mFragmentControllerService);
            fragmentTransaction.commit();

            MainActivity.serviceMusicPlayer.context = this;
        }else{
            mFrameLayout.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.VISIBLE);
        }

        MainViewPaggerAdapter mainViewPaggerAdapter = new MainViewPaggerAdapter(getSupportFragmentManager());

        mainViewPaggerAdapter.addFragment(fragment_local, "");
        mainViewPaggerAdapter.addFragment(fragment_home,"");
        mainViewPaggerAdapter.addFragment(fragment_search, "");

        mViewPager.setAdapter(mainViewPaggerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(R.drawable.iconlocal);
        mTabLayout.getTabAt(1).setIcon(R.drawable.iconhome);
        mTabLayout.getTabAt(2).setIcon(R.drawable.iconsearch);

        mTabLayout.setBackgroundColor(Color.parseColor("#280D4D"));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int a= 0;
                switch (position){
                    case 0:
                        getSupportActionBar().show();
                        break;
                    case 1:
                        getSupportActionBar().show();
                        break;
                    case 2:
                        getSupportActionBar().hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void map(){
        mTabLayout = findViewById(R.id.myTabLayout);
        mViewPager = findViewById(R.id.myViewPager);

        mFrameLayout = findViewById(R.id.frame_controller_service);

        mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLocalActivity = new Intent(MainActivity.this, PlayMusicActivity.class);
                intentLocalActivity.putExtra("type_play", "continue");

                MainActivity.this.startActivity(intentLocalActivity);
            }
        });
    }

    private void scanMusicExternal(){
        ContentResolver musicResolver = getContentResolver();
        ScanLocalMusic scanLocalMusic = new ScanLocalMusic();
        scanLocalMusic.scan(musicResolver);
    }

    public void initPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {

                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(Manifest.permission.WAKE_LOCK)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.WAKE_LOCK}, 1);

            }else{
                int a= 0;
            }



            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }else{
                //quet nhac neu duoc su cho phep
                scanMusicExternal();
            }
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                //Permisson don't granted
                if (shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)) {
                    Toast.makeText(MainActivity.this, "Permission isn't granted ", Toast.LENGTH_SHORT).show();
                }
                // Permisson don't granted and dont show dialog again.
                else {
                    Toast.makeText(MainActivity.this, "Permisson don't granted and dont show dialog again ", Toast.LENGTH_SHORT).show();
                }
                //Register permission
                requestPermissions(new String[]{Manifest.permission.INTERNET}, 1);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permision Write File is Granted", Toast.LENGTH_SHORT).show();

                //quet nhac neu duoc su cho phep
                scanMusicExternal();
            } else {
                Toast.makeText(MainActivity.this, "Permision Write File is Denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
