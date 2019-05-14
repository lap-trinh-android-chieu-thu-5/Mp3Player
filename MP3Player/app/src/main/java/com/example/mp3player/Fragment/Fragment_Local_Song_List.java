package com.example.mp3player.Fragment;

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
import android.widget.ImageView;

import com.example.mp3player.Adapter.LocalSongListAdapter;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;


import java.util.List;

public class Fragment_Local_Song_List extends Fragment {
    boolean isEdit = false;
    View view;
    LocalSongListAdapter mLocalSongListAdapter;
    RecyclerView mRecyclerView;
    ImageView mImgButtonEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_list_song, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        map();
        init();
    }

    private void map(){
        this.mRecyclerView = view.findViewById(R.id.recyclerview_song_list);
        this.mImgButtonEdit = view.findViewById(R.id.image_button_edit);

        //set event
        this.mImgButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    mImgButtonEdit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.iconedit));
                    mLocalSongListAdapter.isEdit = false;
                    isEdit = false;
                    mLocalSongListAdapter.notifyDataSetChanged();

                    //Bat dau xoa
                }else{
                    mImgButtonEdit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icondelete));
                    mLocalSongListAdapter.isEdit = true;
                    isEdit = true;
                    mLocalSongListAdapter.notifyDataSetChanged();

                    //Mo rong check box xoa ra
                }

            }
        });
    }
    private  void init(){
        List<Song> lstSong = Song.listAll(Song.class);
        mLocalSongListAdapter = new LocalSongListAdapter(getActivity(), lstSong);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLocalSongListAdapter);
    }


}

