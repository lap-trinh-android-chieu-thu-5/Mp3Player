package com.example.mp3player.Model.Local;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class Album extends SugarRecord {
    @Unique
    public  long idAlbum;

    public  String name;
    public  String urlThumbnail;

    public Album(){}
    public  Album(long idAlbum, String name, String urlThumbnail){
        this.idAlbum = idAlbum;
        this.name = name;
        this.urlThumbnail = urlThumbnail;
    }
}
