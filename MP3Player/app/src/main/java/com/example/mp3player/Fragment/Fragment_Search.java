package com.example.mp3player.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Adapter.LocalSongListAdapter;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Search extends Fragment {
    boolean isEdit = false;
    View view;
    CheckBox mCheckBoxAll;
    LocalSongListAdapter mLocalSongListAdapter;
    RecyclerView mRecyclerView;
    ImageButton mImgButtonEdit;
    ImageButton mImgButtonPlay;
    List<Song> mLstSong;
    TextInputEditText mTextInputEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
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
        this.mTextInputEditText = view.findViewById(R.id.textinput_search);
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
                    mLocalSongListAdapter.isEdit = false;
                    isEdit = false;
                    mImgButtonPlay.setVisibility(View.GONE);
                    mCheckBoxAll.setVisibility(View.GONE);
                    mLocalSongListAdapter.notifyDataSetChanged();

                    //Bat dau xoa
                }else{
                    mImgButtonEdit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.iconoptioncancel));
                    mLocalSongListAdapter.isEdit = true;
                    isEdit = true;
                    mImgButtonPlay.setVisibility(View.VISIBLE);
                    mCheckBoxAll.setVisibility(View.VISIBLE);
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

        //set search
        this.mTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                s = s.toString().toUpperCase();
                List<Song> lstFound = new ArrayList<Song>();
                if(s.length() > 0){
                    for(Song item:mLstSong){
                        if(item.artist.name.toUpperCase().contains(s) || item.album.name.toUpperCase().contains(s) || item.name.toUpperCase().contains(s)){
                            lstFound.add(item);
                        }
                    }
                    mLstSong = lstFound;
                    mLocalSongListAdapter.setLstSong(mLstSong);
                    mLocalSongListAdapter.notifyDataSetChanged();
                }else{
                    lstFound =Song.listAll(Song.class);
                    mLstSong = lstFound;
                    mLocalSongListAdapter.setLstSong(mLstSong);
                    mLocalSongListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private  void init(){
        mLstSong = Song.listAll(Song.class);
        mLocalSongListAdapter = new LocalSongListAdapter(getActivity(), mLstSong, isEdit);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLocalSongListAdapter);
    }
}
