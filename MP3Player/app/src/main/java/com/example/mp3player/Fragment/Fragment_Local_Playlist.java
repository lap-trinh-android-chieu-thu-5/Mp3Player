package com.example.mp3player.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mp3player.Adapter.LocalArtistListAdapter;
import com.example.mp3player.Adapter.LocalPlaylistListAdapter;
import com.example.mp3player.Interface.ItemClickArtist;
import com.example.mp3player.Interface.ItemClickArtistToFragment;
import com.example.mp3player.Interface.ItemClickPlaylist;
import com.example.mp3player.Interface.ItemClickPlaylistToFragment;
import com.example.mp3player.Model.Local.Artist;
import com.example.mp3player.Model.Local.Playlist;
import com.example.mp3player.R;

import java.util.List;

public class Fragment_Local_Playlist extends Fragment implements ItemClickPlaylistToFragment {
    ItemClickPlaylist mItemClickPlaylist;
    View view;
    LocalPlaylistListAdapter mLocalPlaylistListAdapter;
    RecyclerView mRecyclerView;
    List<Playlist> mLstPlaylist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_playlist, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        map();
        init();
    }

    private void map(){
        this.mRecyclerView = view.findViewById(R.id.recyclerview_playlist_list);
    }
    private  void init(){
        try{
            mLstPlaylist = Playlist.listAll(Playlist.class);
            mLocalPlaylistListAdapter = new LocalPlaylistListAdapter(getActivity(), mLstPlaylist);
            mLocalPlaylistListAdapter.setOnClick(this);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mLocalPlaylistListAdapter);
        }catch (Exception ex){
            //mLstPlaylist = null;
        }

    }

    public void setOnClick(ItemClickPlaylist itemClickPlaylist){
        this.mItemClickPlaylist = itemClickPlaylist;
    }


    @Override
    public void onClick(String playlistId) {
        this.mItemClickPlaylist.onClick(playlistId);
    }
}
