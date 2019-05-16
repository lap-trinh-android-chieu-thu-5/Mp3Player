package com.example.mp3player.Model.Local;

import com.orm.SugarRecord;

public class Playlist_Song extends SugarRecord {
    public Playlist playlist;
    public Song song;
    public  Playlist_Song(){}
    public  Playlist_Song(Playlist playlist, Song song){
        this.playlist = playlist;
        this.song = song;
    }
}
