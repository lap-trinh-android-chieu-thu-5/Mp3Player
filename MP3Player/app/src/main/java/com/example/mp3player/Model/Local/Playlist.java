package com.example.mp3player.Model.Local;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Playlist extends SugarRecord {

    public  String name;
    public  String urlThumbnail;

    public Playlist(){}
    public  Playlist(String name, String urlThumbnail){
        this.urlThumbnail = urlThumbnail;
        this.name = name;
    }
}
