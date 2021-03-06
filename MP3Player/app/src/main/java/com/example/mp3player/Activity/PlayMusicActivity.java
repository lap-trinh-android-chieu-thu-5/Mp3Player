package com.example.mp3player.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mp3player.Adapter.PlayMusicViewPaggerAdapter;
import com.example.mp3player.Fragment.Fragment_Playing_List;
import com.example.mp3player.Fragment.Fragment_Song_Disc;
import com.example.mp3player.Interface.ItemClickListenerToActivity;
import com.example.mp3player.Interface.PlayBackInfoListenerInterface;
import com.example.mp3player.Model.Local.Playlist;
import com.example.mp3player.Model.Local.Playlist_Song;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.MusicPlayer.ServiceMusicPlayer;
import com.example.mp3player.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PlayMusicActivity extends AppCompatActivity implements ItemClickListenerToActivity {
    public static List<Song> lstSong = new ArrayList<>();
    public static List<com.example.mp3player.Model.Host.Song> lstSongOnline = new ArrayList<>();
    public static PlayMusicViewPaggerAdapter playMusicViewPaggerAdapter;
    ViewPager mViewPaggerMusicPlayer;
    Toolbar mToolbarMusicPlay;
    TextView mTxtSongTime;
    TextView mTxtSongTimeTotal;
    SeekBar mSeekBarTime;
    ImageButton mImgBtnPlay, mImgBtnRepeat, mImgBtnNext,mImgBtnPreview, mImgBtnRandom;

    //Service play music
    private ServiceMusicPlayer mServiceMusicPlayer;
    private Intent mPlayIntent;
    private boolean mIsMusicBound = false;
    private boolean mIsPaused = false;
    private boolean mIsPlaybackPaused = false;
    private int mSeekToTimePause = 0;
    private boolean mIsPlaying = false;
    private int isNew = 0;
    private boolean isContinue = false;
    private boolean isOnline = false;

    //fragment
    Fragment_Song_Disc fragmentSongDisc;
    Fragment_Playing_List fragmentPlayingList;

    //manage update time
    private Handler mHandlerUpdateTime;


    @Override
    protected void onDestroy() {

        //stop thread update time
        if(mHandlerUpdateTime != null) {
            mHandlerUpdateTime.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {

        super.onStart();
        //start and manage service player
        controlService();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        map();
        init();
        if(MainActivity.serviceMusicPlayer != null){

        }
        //eventClick();

        //Nhan intent
        Intent intent = getIntent();
        if(intent != null){
            String type_play = intent.getExtras().getString("type_play");

            switch (type_play){
                case "play_all_song":
                    isOnline = false;
                    List<Song> songAll = Song.listAll(Song.class);
                    lstSong = songAll;
                    break;
                case "play_single_song":
                    isOnline = false;
                    if(intent.hasExtra("song_id")){
                        Long id = intent.getExtras().getLong("song_id");
                        List<Song> songCheckList = Song.find(Song.class, "id_song = ?", Long.toString(id));
                        Song song = songCheckList.get(0);
                        lstSong.clear();
                        lstSong.add(song);
                    }
                    break;
                case "play_favorite":
                    isOnline = false;
                    List<Song> songFavorite = Song.find(Song.class, "is_favorite = ?",  "1");
                    lstSong = songFavorite;
                    break;
                case "play_choose_song":
                    isOnline = false;
                    lstSong.clear();
                    List<Song> songChoose = new ArrayList<>();
                    ArrayList<String> arrayListChooeSong = intent.getStringArrayListExtra("list_choose_song");
                    for(int i = 0; i < arrayListChooeSong.size();i ++){
                        List<Song> songCheckList = Song.find(Song.class, "id_song = ?", arrayListChooeSong.get(i));
                        Song song = songCheckList.get(0);
                        songChoose.add(song);
                    }
                    lstSong = songChoose;
                    break;
                case "play_artist":
                    isOnline = false;
                    lstSong.clear();
                    Long artistId = intent.getExtras().getLong("artist_id");
                    List<Song> songArtist = Song.find(Song.class, "artist = ?",  String.valueOf(artistId));
                    lstSong = songArtist;
                    break;
                case "play_playlist":
                    isOnline = false;
                    lstSong.clear();
                    Long playlistId = intent.getExtras().getLong("playlist_id");
                    // Playlist playlist = Playlist.findById(Playlist.class, playlistId);
                    List<Playlist_Song> playlist_songs = Playlist_Song.find(Playlist_Song.class, "playlist = ?", String.valueOf(playlistId));
                    List<Song> songPlaylist = new ArrayList<>();
                    for(Playlist_Song item:playlist_songs){
                        songPlaylist.add(item.song);
                    }
                    lstSong = songPlaylist;
                    break;
                case "continue":
                    isContinue = true;
                    break;
                case "play_online_playlist_all":
                    isOnline = true;
                    ArrayList<com.example.mp3player.Model.Host.Song> songList = intent.getParcelableArrayListExtra("list_song");
                    for(com.example.mp3player.Model.Host.Song item:songList){
                        lstSongOnline.add(item);
                    }
                    break;
                case "play_single_song_online":
                    isOnline = true;
                    com.example.mp3player.Model.Host.Song song = intent.getParcelableExtra("song");
                    lstSongOnline.clear();
                    lstSongOnline.add(song);
                    break;
            }

        }
    }

    private void controlService() {
        if (mPlayIntent == null && mServiceMusicPlayer == null) {
            if(MainActivity.serviceMusicPlayer != null){
                isNew = 1;
                mServiceMusicPlayer = MainActivity.serviceMusicPlayer;
                mServiceMusicPlayer.isOnline = isOnline;
                if( isContinue){
                    if(isOnline){
                        lstSongOnline = mServiceMusicPlayer.getSongsOnline();
                        mIsMusicBound = true;
                        mServiceMusicPlayer.setPlaybackInfoListener(new PlaybackListener());

                        if(lstSongOnline.size() > 0){
                            updateTime();
                            mIsPlaying = true;
                            setTimeSong();
                            mToolbarMusicPlay.setTitle(mServiceMusicPlayer.mSongTitle);
                            mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
                        }
                    }else {
                        lstSong = mServiceMusicPlayer.getListSong();
                        mIsMusicBound = true;
                        mServiceMusicPlayer.setPlaybackInfoListener(new PlaybackListener());

                        if(lstSong.size() > 0){
                            updateTime();
                            mIsPlaying = true;
                            setTimeSong();
                            mToolbarMusicPlay.setTitle(mServiceMusicPlayer.mSongTitle);
                            mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
                        }
                    }

                }else{
                    if(isOnline){
                        mServiceMusicPlayer.setmSongsOnline(lstSongOnline);
                        mIsMusicBound = true;
                        mServiceMusicPlayer.setPlaybackInfoListener(new PlaybackListener());

                        if(lstSongOnline.size() > 0){
                            updateTime();
                            mIsPlaying = true;
                            playSongList();
                            //set title
                            mToolbarMusicPlay.setTitle(mServiceMusicPlayer.mSongTitle);
                            mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
                        }
                    }else {
                        mServiceMusicPlayer.setList(lstSong);
                        mIsMusicBound = true;
                        mServiceMusicPlayer.setPlaybackInfoListener(new PlaybackListener());

                        if(lstSong.size() > 0){
                            updateTime();
                            mIsPlaying = true;
                            playSongList();
                            //set title
                            mToolbarMusicPlay.setTitle(mServiceMusicPlayer.mSongTitle);
                            mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
                        }
                    }

                }
                customSimpleNotification(this);
            }else{
                mPlayIntent = new Intent(this, ServiceMusicPlayer.class);
                bindService(mPlayIntent, musicConnection, BIND_AUTO_CREATE);
            }
        }

        try{
            MainActivity.serviceMusicPlayer.context = this;

        }catch (Exception e){
            int a = 0;
        }
    }

    private void map(){
        mToolbarMusicPlay = findViewById(R.id.toolbar_music_play);
        mTxtSongTime = findViewById(R.id.textview_song_time);
        mTxtSongTimeTotal = findViewById(R.id.textview_song_time_total);
        mSeekBarTime = findViewById(R.id.seekbar_song);
        mImgBtnPlay = findViewById(R.id.image_button_play);
        mImgBtnRepeat = findViewById(R.id.image_button_repeat);
        mImgBtnNext = findViewById(R.id.image_button_next);
        mImgBtnPreview = findViewById(R.id.image_button_preview);
        mImgBtnRandom = findViewById(R.id.image_button_suffle);
        mViewPaggerMusicPlayer = findViewById(R.id.viewpagger_music_play);
    }

    private  void setTimeSong(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        mTxtSongTimeTotal.setText(simpleDateFormat.format(mServiceMusicPlayer.getDur()));
        mSeekBarTime.setMax(mServiceMusicPlayer.getDur());
        //mSeekBarTime.set
    }

    private void init(){
        setSupportActionBar(mToolbarMusicPlay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbarMusicPlay.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolbarMusicPlay.setTitleTextColor(Color.WHITE);

        //Add fragment
        fragmentSongDisc = new Fragment_Song_Disc();
        fragmentPlayingList = new Fragment_Playing_List();
        //set onClick view holder
        fragmentPlayingList.setmItemClickListenerToActivity(this);

        playMusicViewPaggerAdapter = new PlayMusicViewPaggerAdapter(getSupportFragmentManager());
        playMusicViewPaggerAdapter.addFragment(fragmentSongDisc);
        playMusicViewPaggerAdapter.addFragment(fragmentPlayingList);
        mViewPaggerMusicPlayer.setAdapter(playMusicViewPaggerAdapter);

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
                    mToolbarMusicPlay.setTitle(mServiceMusicPlayer.mSongTitle);

                    mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
                    fragmentSongDisc.startRotate();
                }else{
                    //dang chay thi pause
                    if(mIsPlaying == true && mIsPaused == false){

                        mIsPaused = true;
                        mServiceMusicPlayer.pausePlayer();
                        mSeekToTimePause = mServiceMusicPlayer.getDur();

                        mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconplay));
                        fragmentSongDisc.pauseRotate();
                    }else{
                        //dang pause thi choi lai
                        if(mIsPaused == true && mIsPlaying == true){
                            mIsPaused = false;
                            mServiceMusicPlayer.start();
                            mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
                            fragmentSongDisc.resumeRotate();
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
                    mImgBtnRandom.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconunsuffle));
                }else{
                    mImgBtnRandom.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconsuffle));
                }
                mServiceMusicPlayer.setShuffle();
            }
        });

        mImgBtnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mServiceMusicPlayer.isRepeat){
                    mImgBtnRepeat.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconunrepeat));
                }else{
                    mImgBtnRepeat.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconrepeat));
                }
                mServiceMusicPlayer.setRepeat();
            }
        });
    }


    // Connect with the service
    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceMusicPlayer.MusicBinder binder = (ServiceMusicPlayer.MusicBinder) service;
            mServiceMusicPlayer = binder.getService();
            mServiceMusicPlayer.isOnline = isOnline;
            if(isOnline){
                mServiceMusicPlayer.setmSongsOnline(lstSongOnline);
                mIsMusicBound = true;
                mServiceMusicPlayer.setPlaybackInfoListener(new PlaybackListener());
                MainActivity.serviceMusicPlayer = mServiceMusicPlayer;
                StaticClass.serviceMusicPlayer = mServiceMusicPlayer;
                if(lstSongOnline.size() > 0){
                    updateTime();
                    mIsPlaying = true;
                    playSongList();
                    //set title
                    mToolbarMusicPlay.setTitle(mServiceMusicPlayer.mSongTitle);
                    mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
                    fragmentSongDisc.startRotate();
                }
                customSimpleNotification(PlayMusicActivity.this);
                MainActivity.serviceMusicPlayer.context = PlayMusicActivity.this;
            }else{
                mServiceMusicPlayer.setList(lstSong);
                mIsMusicBound = true;
                mServiceMusicPlayer.setPlaybackInfoListener(new PlaybackListener());
                MainActivity.serviceMusicPlayer = mServiceMusicPlayer;
                StaticClass.serviceMusicPlayer = mServiceMusicPlayer;
                if(lstSong.size() > 0){
                    updateTime();
                    mIsPlaying = true;
                    playSongList();
                    //set title
                    mToolbarMusicPlay.setTitle(mServiceMusicPlayer.mSongTitle);
                    mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
                    fragmentSongDisc.startRotate();
                }
                customSimpleNotification(PlayMusicActivity.this);
                MainActivity.serviceMusicPlayer.context = PlayMusicActivity.this;
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsMusicBound = false;
        }
    };


    private void setController(){
        //set lai thong so cho phan dieu khien
    }

    public void playSongList(){
        mServiceMusicPlayer.setList(lstSong);
        mServiceMusicPlayer.setSong(0);
        mServiceMusicPlayer.playSong();
    }

    //onclick fragment view holder
    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        //neu la bai dang chay thi khong chuyen bai
        if(position == mServiceMusicPlayer.getSongPos()){
            return;
        }
        this.mServiceMusicPlayer.chooseSongPlay(position);
        setIconMusicController();
    }

    public class PlaybackListener implements PlayBackInfoListenerInterface {
        @Override
        public void  onProgressChanged(int progress){
            mSeekBarTime.setProgress(progress);
        }
        @Override
        public void  onSongChanged(int index) {
            int a = 0;
            if(isNew == 1){
                isNew ++;
                fragmentSongDisc.startRotate();
            }
            if(isOnline){
                com.example.mp3player.Model.Host.Song song = lstSongOnline.get(index);
                mToolbarMusicPlay.setTitle(song.getTen());
                setTimeSong();
            }else{
                Song song = lstSong.get(index);
                mToolbarMusicPlay.setTitle(song.name);
                setTimeSong();
            }

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

    private  void setIconMusicController(){
        mIsPaused = false;
        mImgBtnPlay.setBackground(ContextCompat.getDrawable(PlayMusicActivity.this, R.drawable.iconpause));
        fragmentSongDisc.startRotate();
    }

    public void customSimpleNotification(Context context){
        RemoteViews simpleView = new RemoteViews(context.getPackageName(), R.layout.custome_notification);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.iconlocalmusic)
                .setContentTitle("Custom Big View").build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.contentView = simpleView;
        notification.contentView.setTextViewText(R.id.textview_song_name, mServiceMusicPlayer.mSongTitle);

        setListeners(simpleView, context);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(22, notification);

        MainActivity.notification = notification;
    }

    private static void setListeners(RemoteViews view, Context context) {//
        Intent previous = new Intent(NOTIFY_PREVIOUS);
        Intent delete = new Intent(NOTIFY_DELETE);
        Intent pause = new Intent(NOTIFY_PAUSE);
        Intent next = new Intent(NOTIFY_NEXT);
        Intent play = new Intent(NOTIFY_PLAY);

        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_previous, pPrevious);//button 1


        PendingIntent pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_pause, pPause);

        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_next, pNext);

        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_play, pPlay);
    }

    public static final String NOTIFY_PREVIOUS = "com.tutorialsface.notificationdemo.previous";
    public static final String NOTIFY_DELETE = "com.tutorialsface.notificationdemo.delete";
    public static final String NOTIFY_PAUSE = "com.tutorialsface.notificationdemo.pause";
    public static final String NOTIFY_PLAY = "com.tutorialsface.notificationdemo.play";
    public static final String NOTIFY_NEXT = "com.tutorialsface.notificationdemo.next";
}