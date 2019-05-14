package com.example.mp3player.Model.Local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.List;

public class ScanLocalMusic {
    public ScanLocalMusic(){

    }
    public void scan(ContentResolver musicResolver){
        // Query external audio resources
        Cursor musicCursor;

        Uri SongUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        try{
            musicCursor = musicResolver.query(SongUri, null, null, null, null);
        }catch (Exception ex){
            musicCursor = null;
        }
        // Iterate over results if valid
        if (musicCursor != null && musicCursor.moveToFirst()) {
            // Get columns
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);

            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int artistIDColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);

            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int albumIDColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

//            Artist.deleteAll(Artist.class);
//            Song.deleteAll(Song.class);
//            Album.deleteAll(Album.class);

//            List<Artist> lstArtist1 = Artist.listAll(Artist.class);
//            List<Album> lstAlbum1 = Album.listAll(Album.class);
//            List<Song> lstSong1 = Song.listAll(Song.class);

            do {
                Long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);

                String thisArtist = musicCursor.getString(artistColumn);
                Long thisArtistID = musicCursor.getLong(artistIDColumn);


                String thisAlbum = musicCursor.getString(albumColumn);
                Long thisAlbumID = musicCursor.getLong(albumIDColumn);

                Artist artistCheck;
                Album albumCheck;
                Song songCheck;

                List<Artist> artistCheckList = Artist.find(Artist.class, "id_artist = ?", Long.toString(thisArtistID));
                if(artistCheckList.size() == 0){
                    artistCheck = new Artist(thisArtistID, thisArtist,"");
                    artistCheck.save();
                }else{
                    artistCheck = artistCheckList.get(0);
                }

                List<Album> albumCheckList = Album.find(Album.class, "id_album = ?", Long.toString(thisAlbumID));
                if(albumCheckList.size() == 0){
                    albumCheck = new Album(thisAlbumID, thisAlbum, "")      ;
                    albumCheck.save();
                }else{
                    albumCheck = albumCheckList.get(0);
                }

                List<Song> songCheckList = Song.find(Song.class, "id_song = ?", Long.toString(thisId));
                if(songCheckList.size() == 0){
                    songCheck = new Song(thisId, thisTitle,"", false, artistCheck, albumCheck);
                    songCheck.save();
                }

                List<Artist> lstArtist = Artist.listAll(Artist.class);
                List<Album> lstAlbum = Album.listAll(Album.class);
                List<Song> lstSong = Song.listAll(Song.class);

                boolean breakpoint = true;
            }
            while (musicCursor.moveToNext());
        }
    }



}
