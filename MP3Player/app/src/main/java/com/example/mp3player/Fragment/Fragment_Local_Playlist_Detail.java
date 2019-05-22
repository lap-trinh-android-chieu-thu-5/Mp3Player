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
import com.example.mp3player.Adapter.LocalSongListAdapter;
import com.example.mp3player.Model.Local.Playlist;
import com.example.mp3player.Model.Local.Playlist_Song;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Local_Playlist_Detail extends Fragment {
    CheckBox mCheckBoxAll;
    String mPlaylistId;
    boolean isEdit = false;
    View view;
    LocalSongListAdapter mLocalSongListAdapter;
    RecyclerView mRecyclerView;
    ImageButton mImgButtonEdit;
    ImageButton mImgButtonPlay, mImgButtonDelete;
    List<Song> mLstSong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_playlist_detail, container, false);
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
        this.mImgButtonEdit = view.findViewById(R.id.image_button_option);
        this.mImgButtonPlay = view.findViewById(R.id.image_button_play);
        this.mCheckBoxAll = view.findViewById(R.id.checkbox_all);
        this.mImgButtonDelete = view.findViewById(R.id.image_button_delete);

        if(isEdit){
            mImgButtonPlay.setVisibility(View.VISIBLE);
            mCheckBoxAll.setVisibility(View.VISIBLE);
            this.mImgButtonDelete.setVisibility(View.VISIBLE);
        }else {
            mImgButtonPlay.setVisibility(View.GONE);
            mCheckBoxAll.setVisibility(View.GONE);
            this.mImgButtonDelete.setVisibility(View.GONE);

        }


        //set event
        mImgButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mLocalSongListAdapter.onClickPlaySongChoose();
                ArrayList<String> lstSongChoose = new ArrayList<>();

                for(int i = 0; i < mRecyclerView.getChildCount(); i++){
                    View viewHolder = mRecyclerView.getChildAt(i);
                    CheckBox checkBox = viewHolder.findViewById(R.id.checkbox_edit);
                    if(checkBox.isChecked()){
                        lstSongChoose.add(String.valueOf(mLstSong.get(i).idSong));
                    }
                }
                if(lstSongChoose.size() == 0){
                    return;
                }else{
                    //Mo sang cua so choi nhac o day

                    Intent intent = new Intent(getContext(), PlayMusicActivity.class);
                    intent.putExtra("type_play","play_choose_song");
                    intent.putStringArrayListExtra("list_choose_song",lstSongChoose);
                    getContext().startActivity(intent);
                }

                //set icon cancle
            }
        });

        this.mImgButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    mImgButtonEdit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.iconoption));
                    mLocalSongListAdapter.isEdit = false;
                    isEdit = false;
                    mImgButtonPlay.setVisibility(View.GONE);
                    mImgButtonDelete.setVisibility(View.GONE);
                    mCheckBoxAll.setVisibility(View.GONE);

                    mLocalSongListAdapter.notifyDataSetChanged();

                    //Bat dau xoa
                }else{
                    mImgButtonEdit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.iconoptioncancel));
                    mLocalSongListAdapter.isEdit = true;
                    isEdit = true;
                    mImgButtonPlay.setVisibility(View.VISIBLE);
                    mCheckBoxAll.setVisibility(View.VISIBLE);
                    mImgButtonDelete.setVisibility(View.VISIBLE);

                    mLocalSongListAdapter.notifyDataSetChanged();

                    //Mo rong check box xoa ra
                }

            }
        });

        this.mCheckBoxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheckBoxAll.isChecked()){
                    mLocalSongListAdapter.isCheckAll = true;
                    mLocalSongListAdapter.notifyDataSetChanged();
                }else {
                    mLocalSongListAdapter.isCheckAll = false;
                    mLocalSongListAdapter.notifyDataSetChanged();
                }

            }
        });

        this.mImgButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mLocalSongListAdapter.onClickPlaySongChoose();
                for(int i = 0; i < mRecyclerView.getChildCount(); i++){
                    View viewHolder = mRecyclerView.getChildAt(i);
                    CheckBox checkBox = viewHolder.findViewById(R.id.checkbox_edit);
                    if(checkBox.isChecked()){
                        Playlist playlist = Playlist.findById(Playlist.class, Long.valueOf(mPlaylistId));
                        Song song = mLstSong.get(i);
                        List<Playlist_Song> playlist_songs = Playlist_Song.find(Playlist_Song.class, "song = ? and playlist = ?", String.valueOf(song.getId()), String.valueOf(playlist.getId()));
                        playlist_songs.get(0).delete();
                        init();
                    }
                }

            }
        });
    }
    public void init(){
        try{
            List<Playlist_Song> songPlaylist = Playlist_Song.find(Playlist_Song.class, "playlist = ?",  String.valueOf(this.mPlaylistId));
            mLstSong = new ArrayList<>();
            for(Playlist_Song item: songPlaylist){
                mLstSong.add(item.song);
            }

            //mLstSong = Song.find(Song.class, "playlist = ?",  String.valueOf(this.mPlaylistId));
            mLocalSongListAdapter = new LocalSongListAdapter(getActivity(), mLstSong, isEdit);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mLocalSongListAdapter);
        }catch (Exception e){
            //mLstSong = null;
        }

    }

    public  void setPlaylistId(String playlistId ){
        this.mPlaylistId = playlistId;
    }
}
