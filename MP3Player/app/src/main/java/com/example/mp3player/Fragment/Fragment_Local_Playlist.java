package com.example.mp3player.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mp3player.Activity.PopUpCreatePlaylist;
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
    ImageButton mImageButtonCreatePlaylist;
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
        this.mImageButtonCreatePlaylist = view.findViewById(R.id.image_button_add);
    }
    public   void init(){
        try{
            mLstPlaylist = Playlist.listAll(Playlist.class);
            mLocalPlaylistListAdapter = new LocalPlaylistListAdapter(getActivity(), mLstPlaylist);
            mLocalPlaylistListAdapter.setOnClick(this);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mLocalPlaylistListAdapter);
        }catch (Exception ex){
            //mLstPlaylist = null;
        }

        this.mImageButtonCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(new Intent(getActivity(), PopUpCreatePlaylist.class), 1);
            }
        });
    }

    public void setOnClick(ItemClickPlaylist itemClickPlaylist){
        this.mItemClickPlaylist = itemClickPlaylist;
    }


    @Override
    public void onClick(String playlistId) {
        this.mItemClickPlaylist.onClick(playlistId);
    }
    public void onLoadData(){
        try{
            mLstPlaylist = Playlist.listAll(Playlist.class);
            mLocalPlaylistListAdapter = new LocalPlaylistListAdapter(getActivity(), mLstPlaylist);
            mLocalPlaylistListAdapter.setOnClick(this);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mLocalPlaylistListAdapter);
        }catch (Exception ex){
            //mLstPlaylist = null;
        }
        mLocalPlaylistListAdapter.notifyDataSetChanged();
    }
}
