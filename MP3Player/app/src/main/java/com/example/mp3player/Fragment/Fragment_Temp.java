package com.example.mp3player.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.mp3player.R;

public class Fragment_Temp extends Fragment {
    View view;

    private CoordinatorLayout mCoordinatorLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewSongList;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_list_song, container, false);
        return view;
    }
    private void map(){
        mCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorlayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsingtoolbar);
        mToolbar =  view.findViewById(R.id.toolbarlist);
        mRecyclerViewSongList =  view.findViewById(R.id.recyclerview_song_list);
        mFloatingActionButton =  view.findViewById(R.id.floatingactionbutton);
    }
    private  void init(){

    }
}
