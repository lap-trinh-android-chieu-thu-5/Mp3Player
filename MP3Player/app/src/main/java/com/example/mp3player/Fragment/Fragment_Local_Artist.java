package com.example.mp3player.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Adapter.LocalArtistListAdapter;
import com.example.mp3player.Adapter.LocalFavoriteListAdapter;
import com.example.mp3player.Interface.ItemClickArtist;
import com.example.mp3player.Interface.ItemClickArtistToFragment;
import com.example.mp3player.Model.Local.Artist;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Local_Artist extends Fragment implements ItemClickArtistToFragment {
    ItemClickArtist mItemClickArtist;
    View view;
    LocalArtistListAdapter mLocalArtistListAdapter;
    RecyclerView mRecyclerView;
    List<Artist> mLstArtist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_artist, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        map();
        init();
    }

    private void map(){
        this.mRecyclerView = view.findViewById(R.id.recyclerview_artist_list);
    }
    private  void init(){
        mLstArtist = Artist.listAll(Artist.class);
        mLocalArtistListAdapter = new LocalArtistListAdapter(getActivity(), mLstArtist);
        mLocalArtistListAdapter.setOnClick(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLocalArtistListAdapter);
    }

    public void setOnClick(ItemClickArtist itemClickArtist){
        this.mItemClickArtist = itemClickArtist;
    }

    @Override
    public void onClick(String artistId) {
        this.mItemClickArtist.onClick(artistId);
    }
}
