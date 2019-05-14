package com.example.mp3player.MusicPlayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Interface.PlayBackInfoListenerInterface;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ServiceMusicPlayer  extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    private MediaPlayer mPlayer;
    private List<Song> mSongs;
    private int mSongPos;
    private final IBinder mMusicBind = new MusicBinder();
    public String mSongTitle = "";
    private static final int NOTIFY_ID = 1;
    public boolean isShuffle = false;
    public boolean isRepeat = false;

    private Random mRandom = new Random();

    private PlayBackInfoListenerInterface mPlaybackInfoListener;

    @Override
    public void onCreate() {
        super.onCreate();
        mSongPos = 0;
        // Khởi tạo một bộ phát đa phương tiện mới.
        mPlayer = new MediaPlayer();
        initMusicPlayer();
    }

    public void initMusicPlayer() {
        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnCompletionListener(this);
    }

    public void setList(List<Song> theSongs) {
        mSongs = theSongs;
    }


    public class MusicBinder extends Binder {
        public  ServiceMusicPlayer getService() {
            return ServiceMusicPlayer.this;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mPlayer.getCurrentPosition() > 0) {

            if(isRepeat){
                mp.reset();
                playSong();
            }else{
                mp.reset();
                playNext();
            }

        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        //set lai cac thong so ben activity
        //mPlaybackInfoListener.onSetTime();
        mPlaybackInfoListener.onSongChanged(mSongPos);

        mp.start();
        Intent notificationIntent = new Intent(this, PlayMusicActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        Notification.Builder nBuilder = new Notification.Builder(this);
        nBuilder.setContentIntent(pendingIntent)
                .setTicker(mSongTitle)
                .setSmallIcon(R.drawable.iconlocalplay)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(mSongTitle);
        Notification notif = nBuilder.getNotification();
        startForeground(NOTIFY_ID, notif);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mPlayer.stop();
        mPlayer.release();
        return false;
    }

    public void playSong() {
        mPlayer.reset();
        Song playSong = mSongs.get(mSongPos);
        mSongTitle = playSong.name;

        Long currentSong = playSong.idSong;
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentSong);
        try {
            mPlayer.setDataSource(getApplicationContext(), trackUri);

        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error starting data source", e);
        }

        mPlayer.prepareAsync();

        //set song time lai activit
    }

    public void setSong(int songIndex) {
        mSongPos = songIndex;
    }

    public int getSongPos() {
        return mSongPos;
    }

    public int getDur() {
        return mPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void pausePlayer() {
        mPlayer.pause();
    }

    public int getCurrentDur(){
        return mPlayer.getCurrentPosition();
    }

    public  void start(){
        mPlayer.start();
    }
    public void seek(int pos) {
        mPlayer.seekTo(pos);
    }

    public void go() {
        mPlayer.start();
    }

    public void playPrev() {
        mSongPos--;
        if (mSongPos < 0) mSongPos = mSongs.size() - 1;
        playSong();
    }

    public void playNext() {
        if (isShuffle) {
            int newSongPos = mSongPos;
            while (newSongPos == mSongPos) {
                newSongPos = mRandom.nextInt(mSongs.size());
            }
            mSongPos = newSongPos;
        } else {
            mSongPos++;
            if (mSongPos >= mSongs.size()) mSongPos = 0;
        }
        playSong();
    }

    public void chooseSongPlay(int postion){
        this.mSongPos = postion;
        playSong();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMusicBind;
    }

    public void setShuffle() {
        if (isShuffle) isShuffle = false;
        else isShuffle = true;
    }

    public void setRepeat() {
        if (isRepeat) isRepeat = false;
        else isRepeat = true;
    }

    public void setPlaybackInfoListener(PlayBackInfoListenerInterface listener) {
        mPlaybackInfoListener = listener;
    }
}
