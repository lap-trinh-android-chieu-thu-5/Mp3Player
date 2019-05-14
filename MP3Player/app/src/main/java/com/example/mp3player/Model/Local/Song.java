package com.example.mp3player.Model.Local;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class Song extends SugarRecord implements Parcelable {
    @Unique
    public  long idSong;

    public  String name;
    public  String urlThumbnail;
    public  boolean isFavorite;
    public  Artist artist;
    public  Album album;


    public  Song(){}

    public  Song(long idSong, String name, String urlThumbnail, boolean isFavorite, Artist artist, Album album){
        this.idSong = idSong;
        this.name = name;
        this.urlThumbnail = urlThumbnail;
        this.isFavorite = isFavorite;
        this.artist = artist;
        this.album = album;
    }

    protected Song(Parcel in) {
        idSong = in.readLong();
        name = in.readString();
        urlThumbnail = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idSong);
        dest.writeString(name);
        dest.writeString(urlThumbnail);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
