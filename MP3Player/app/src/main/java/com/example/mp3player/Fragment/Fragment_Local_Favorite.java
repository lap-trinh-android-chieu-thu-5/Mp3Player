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
import android.widget.ImageView;

import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Adapter.LocalFavoriteListAdapter;
import com.example.mp3player.Adapter.LocalSongListAdapter;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Local_Favorite extends Fragment {
    boolean isEdit = false;
    View view;
    CheckBox mCheckBoxAll;
    LocalFavoriteListAdapter mLocalFavoriteListAdapter;
    RecyclerView mRecyclerView;
    ImageButton mImgButtonEdit;
    ImageButton mImgButtonPlay;
    List<Song> mLstSong;

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
        this.mImgButtonEdit = view.findViewById(R.id.image_button_option);
        this.mImgButtonPlay = view.findViewById(R.id.image_button_play);
        this.mCheckBoxAll = view.findViewById(R.id.checkbox_all);

        if(isEdit){
            mImgButtonPlay.setVisibility(View.VISIBLE);
            mCheckBoxAll.setVisibility(View.VISIBLE);
        }else {
            mImgButtonPlay.setVisibility(View.GONE);
            mCheckBoxAll.setVisibility(View.GONE);
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
                    mLocalFavoriteListAdapter.isEdit = false;
                    isEdit = false;
                    mImgButtonPlay.setVisibility(View.GONE);
                    mCheckBoxAll.setVisibility(View.GONE);

                    mLocalFavoriteListAdapter.notifyDataSetChanged();

                    //Bat dau xoa
                }else{
                    mImgButtonEdit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.iconoptioncancel));
                    mLocalFavoriteListAdapter.isEdit = true;
                    isEdit = true;
                    mImgButtonPlay.setVisibility(View.VISIBLE);
                    mCheckBoxAll.setVisibility(View.VISIBLE);

                    mLocalFavoriteListAdapter.notifyDataSetChanged();

                    //Mo rong check box xoa ra
                }

            }
        });

        this.mCheckBoxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheckBoxAll.isChecked()){
                    mLocalFavoriteListAdapter.isCheckAll = true;
                    mLocalFavoriteListAdapter.notifyDataSetChanged();
                }else {
                    mLocalFavoriteListAdapter.isCheckAll = false;
                    mLocalFavoriteListAdapter.notifyDataSetChanged();
                }

            }
        });
    }
    private  void init(){
        mLstSong = Song.find(Song.class, "is_favorite = ?",  "1");
        mLocalFavoriteListAdapter = new LocalFavoriteListAdapter(getActivity(), mLstSong, isEdit);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLocalFavoriteListAdapter);
    }
}
