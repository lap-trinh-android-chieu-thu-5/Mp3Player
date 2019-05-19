package com.example.mp3player.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mp3player.Activity.LocalActivity;
import com.example.mp3player.Activity.MainActivity;
import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Adapter.PlayMusicViewPaggerAdapter;
import com.example.mp3player.Interface.PlayBackInfoListenerInterface;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.MusicPlayer.ServiceMusicPlayer;
import com.example.mp3player.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Controller_Service extends Fragment {
    public static List<Song> lstSong = new ArrayList<>();
    TextView mTxtSongTime;
    TextView mTxtSongTimeTotal, mTxtSongName;
    SeekBar mSeekBarTime;
    ImageButton mImgBtnPlay, mImgBtnRepeat, mImgBtnNext,mImgBtnPreview, mImgBtnRandom;

    //Service play music
    private ServiceMusicPlayer mServiceMusicPlayer;
    private boolean mIsPaused = false;
    private boolean mIsPlaybackPaused = false;
    private int mSeekToTimePause = 0;
    private boolean mIsPlaying = false;

    //manage update time
    private Handler mHandlerUpdateTime;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_controller_service, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();

        map();
        init();

        this.mServiceMusicPlayer = MainActivity.serviceMusicPlayer;
        lstSong = this.mServiceMusicPlayer.getListSong();
        updateTime();
        mIsPlaying = true;
        //playSongList();

        //set title
        mTxtSongName.setText(mServiceMusicPlayer.mSongTitle);

        mImgBtnPlay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.iconpause));
        mServiceMusicPlayer.setPlaybackInfoListener(new PlaybackListener());
        setTimeSong();
    }

    private void map(){
        mTxtSongTime = view.findViewById(R.id.textview_song_time);
        mTxtSongTimeTotal = view.findViewById(R.id.textview_song_time_total);
        mSeekBarTime = view.findViewById(R.id.seekbar_song);
        mImgBtnPlay = view.findViewById(R.id.image_button_play);
        mImgBtnRepeat = view.findViewById(R.id.image_button_repeat);
        mImgBtnNext = view.findViewById(R.id.image_button_next);
        mImgBtnPreview = view.findViewById(R.id.image_button_preview);
        mImgBtnRandom = view.findViewById(R.id.image_button_suffle);
        mTxtSongName = view.findViewById(R.id.textview_song_name);
    }
    private  void setTimeSong(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        mTxtSongTimeTotal.setText(simpleDateFormat.format(mServiceMusicPlayer.getDur()));
        mSeekBarTime.setMax(mServiceMusicPlayer.getDur());
        //mSeekBarTime.set
    }

    private void init(){
        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mServiceMusicPlayer.seek(seekBar.getProgress());
            }
        });

        mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chay lan dau
                if(mIsPaused == false && mIsPlaying == false){
                    mIsPlaying = true;

                    playSongList();

                    //set title

                    mImgBtnPlay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.iconpause));
                }else{
                    //dang chay thi pause
                    if(mIsPlaying == true && mIsPaused == false){

                        mIsPaused = true;
                        mServiceMusicPlayer.pausePlayer();
                        mSeekToTimePause = mServiceMusicPlayer.getDur();

                        mImgBtnPlay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.iconplay));
                    }else{
                        //dang pause thi choi lai
                        if(mIsPaused == true && mIsPlaying == true){
                            mIsPaused = false;
                            mServiceMusicPlayer.start();
                            mImgBtnPlay.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.iconpause));
                        }else{
                        }
                    }
                }
            }
        });

        mImgBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServiceMusicPlayer.playNext();
            }
        });


        mImgBtnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServiceMusicPlayer.playPrev();
            }
        });

        mImgBtnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServiceMusicPlayer.isShuffle){
                    mImgBtnRandom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.iconunsuffle));
                }else{
                    mImgBtnRandom.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.iconsuffle));
                }
                mServiceMusicPlayer.setShuffle();
            }
        });

        mImgBtnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServiceMusicPlayer.isRepeat){
                    mImgBtnRepeat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.iconunrepeat));
                }else{
                    mImgBtnRepeat.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.iconrepeat));
                }
                mServiceMusicPlayer.setRepeat();
            }
        });
    }

    public void playSongList(){
        mServiceMusicPlayer.setList(lstSong);
        mServiceMusicPlayer.setSong(0);
        mServiceMusicPlayer.playSong();
    }

    public class PlaybackListener implements PlayBackInfoListenerInterface {
        @Override
        public void  onProgressChanged(int progress){
            mSeekBarTime.setProgress(progress);
        }
        @Override
        public void  onSongChanged(int index) {
            Song song = lstSong.get(index);
            setTimeSong();
            //set title
            mTxtSongName.setText(mServiceMusicPlayer.mSongTitle);
            //setIconMusicController();
        }

        @Override
        public void onSetTime() {
            setTimeSong();
        }
    }

    private void updateTime(){
        mHandlerUpdateTime = new Handler();
        mHandlerUpdateTime.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSeekBarTime.setProgress(mServiceMusicPlayer.getCurrentDur());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                mTxtSongTime.setText(simpleDateFormat.format(mServiceMusicPlayer.getCurrentDur()));
                mHandlerUpdateTime.postDelayed(this, 300);
            }
        },300);

    }

}
