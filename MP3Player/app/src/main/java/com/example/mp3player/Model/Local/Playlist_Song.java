package com.example.mp3player.Model.Local;

import com.orm.SugarRecord;


public class Playlist_Song extends SugarRecord {
    public Playlist playlist;
    public Song song;

    public int getSongCountByIdPlaylist()
    {
       return this.find(Song.class, "author = ?", playlist.getId().toString()).size();
    }
}
