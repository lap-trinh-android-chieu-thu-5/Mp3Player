package com.example.mp3player.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mp3player.Adapter.LocalArtistViewPaggerAdapter;
import com.example.mp3player.Interface.ItemClickAlbum;
import com.example.mp3player.Interface.ItemClickArtist;
import com.example.mp3player.R;

public class Fragment_Local_Album_Main extends Fragment implements ItemClickAlbum {
    private boolean isExit = false;
    private ViewPager mViewPager;
    View view;
    private Fragment_Local_Album mFragment_local_album;
    private Fragment_Local_Album_Detail mFragment_local_album_detail;
    @Override
    public void onStart() {
        super.onStart();
        map();
        init();
        this.isExit = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_artist_main, container, false);
        return view;
    }

    private  void init(){
        LocalArtistViewPaggerAdapter localArtistViewPaggerAdapter = new LocalArtistViewPaggerAdapter(getChildFragmentManager());

        if(isExit == false){
            this.mFragment_local_album = new Fragment_Local_Album();
            this.mFragment_local_album.setOnClick(this);
            this.mFragment_local_album_detail = new Fragment_Local_Album_Detail();
        }


        localArtistViewPaggerAdapter.addFragment(mFragment_local_album);
        localArtistViewPaggerAdapter.addFragment(mFragment_local_album_detail);

        mViewPager.setAdapter(localArtistViewPaggerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1){
                    mFragment_local_album_detail.init();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private  void map(){
        mViewPager = view.findViewById(R.id.myViewPager);
    }


    @Override
    public void onClick(String albumId) {
        this.mFragment_local_album_detail.setAlbumId(albumId);
        this.mFragment_local_album_detail.setAlbumId(albumId);
        this.mFragment_local_album_detail.init();
        mViewPager.setCurrentItem(1);
    }
}