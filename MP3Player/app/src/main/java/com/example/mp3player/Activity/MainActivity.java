package com.example.mp3player.Activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mp3player.Adapter.LocalSongListAdapter;
import com.example.mp3player.Adapter.MainViewPaggerAdapter;
import com.example.mp3player.Fragment.Fragment_Home;
import com.example.mp3player.Fragment.Fragment_Local;
import com.example.mp3player.Fragment.Fragment_Search;
import com.example.mp3player.Model.Local.Artist;
import com.example.mp3player.Model.Local.ScanLocalMusic;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    Fragment_Local fragment_local = new Fragment_Local();
    Fragment_Search fragment_search = new Fragment_Search();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map();
        init();
        initPermission();
    }

    private  void init(){
        MainViewPaggerAdapter mainViewPaggerAdapter = new MainViewPaggerAdapter(getSupportFragmentManager());

        mainViewPaggerAdapter.addFragment(fragment_local, "");
        //mainViewPaggerAdapter.addFragment(new Fragment_Home(),"Trang chủ");
        mainViewPaggerAdapter.addFragment(fragment_search, "");

        mViewPager.setAdapter(mainViewPaggerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.iconhome);

        //mTabLayout.getTabAt(0).setIcon(R.drawable.iconlocal);
        //mTabLayout.getTabAt(1).setIcon(R.drawable.iconhome);
        mTabLayout.getTabAt(1).setIcon(R.drawable.iconsearch);

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
                //scanMusicExternal();
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
                //scanMusicExternal();
            } else {
                Toast.makeText(MainActivity.this, "Permision Write File is Denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
