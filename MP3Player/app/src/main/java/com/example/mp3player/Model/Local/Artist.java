package com.example.mp3player.Model.Local;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class Artist extends SugarRecord {
    @Unique
    public  long idArtist;

    public  String name;
    public  String urlThumbnail;

    public Artist(){}
    public  Artist(long idArtist, String name, String urlThumbnail){
        this.idArtist = idArtist;
        this.name = name;
        this.urlThumbnail = urlThumbnail;
    }

}
