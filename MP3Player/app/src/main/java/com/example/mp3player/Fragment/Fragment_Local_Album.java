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

import com.example.mp3player.Adapter.LocalAlbumListAdapter;
import com.example.mp3player.Adapter.LocalArtistListAdapter;
import com.example.mp3player.Interface.ItemClickAlbum;
import com.example.mp3player.Interface.ItemClickAlbumToFragment;
import com.example.mp3player.Interface.ItemClickArtist;
import com.example.mp3player.Interface.ItemClickArtistToFragment;
import com.example.mp3player.Model.Local.Album;
import com.example.mp3player.Model.Local.Artist;
import com.example.mp3player.R;

import java.util.List;

public class Fragment_Local_Album extends Fragment implements ItemClickAlbumToFragment {
    ItemClickAlbum mItemClickAlbum;
    View view;
    LocalAlbumListAdapter mLocalAlbumListAdapter;
    RecyclerView mRecyclerView;
    List<Album> mLstAlbum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_album, container, false);
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
        mLstAlbum = Album.listAll(Album.class);
        mLocalAlbumListAdapter = new LocalAlbumListAdapter(getActivity(), mLstAlbum);
        mLocalAlbumListAdapter.setOnClick(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLocalAlbumListAdapter);
    }

    public void setOnClick(ItemClickAlbum itemClickAlbum){
        this.mItemClickAlbum = itemClickAlbum;
    }

    @Override
    public void onClick(String albumId) {
        this.mItemClickAlbum.onClick(albumId);
    }
}
