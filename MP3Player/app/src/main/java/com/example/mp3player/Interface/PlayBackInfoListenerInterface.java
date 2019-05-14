package com.example.mp3player.Interface;

public interface PlayBackInfoListenerInterface {
    public void onProgressChanged(int progress);
    public void  onSongChanged(int index);
    public void  onSetTime();
}
