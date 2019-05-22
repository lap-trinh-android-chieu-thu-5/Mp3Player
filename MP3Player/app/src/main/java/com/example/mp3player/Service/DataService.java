package com.example.mp3player.Service;

import com.example.mp3player.Model.Host.Playlist;
import com.example.mp3player.Model.Host.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("getplaylist.php")
    Call<List<Playlist>> getDataPlaylist();

    @POST("getSongInPlayList.php")
    Call<List<Song>> getListSongPlayList(@Field("idplaylist") String idplaylist);
}
