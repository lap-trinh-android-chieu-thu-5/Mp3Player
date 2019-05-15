package com.example.mp3player.Fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mp3player.Activity.LocalActivity;
import com.example.mp3player.Activity.MainActivity;
import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Adapter.MainViewPaggerAdapter;
import com.example.mp3player.Model.Local.Album;
import com.example.mp3player.Model.Local.Artist;
import com.example.mp3player.Model.Local.Playlist;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import org.w3c.dom.Text;

import java.util.List;

public class Fragment_Local extends Fragment implements View.OnClickListener {
    View view;

    TextView mTextViewNumberSong;
    TextView mTextViewNumberPlaylist;
    TextView mTextViewNumberFavorite;
    TextView mTextViewNumberArtist;
    TextView mTextViewNumberAlbum;

    ImageButton mImgBtnPlaySong;
    ImageButton mImgBtnPlayPlaylist;
    ImageButton mImgBtnPlayFavorite;
    ImageButton mImgBtnPlayArtist;
    ImageButton mImgBtnPlayAlbum;

    RelativeLayout mRlvSong;
    RelativeLayout mRlvPlaylist;
    RelativeLayout mRlvFavorite;
    RelativeLayout mRlvArtist;
    RelativeLayout mRlvAlbum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        map();
        init();
    }

    public void map(){
        mTextViewNumberSong = (TextView)view.findViewById(R.id.textview_number_song);
        mTextViewNumberPlaylist = (TextView)view.findViewById(R.id.textview_number_playlist);
        mTextViewNumberFavorite = (TextView)view.findViewById(R.id.textview_number_favorite);
        mTextViewNumberArtist = (TextView)view.findViewById(R.id.textview_number_artist);
        mTextViewNumberAlbum = (TextView)view.findViewById(R.id.textview_number_album);


        mImgBtnPlaySong = (ImageButton)view.findViewById(R.id.image_button_play_song);
        mImgBtnPlayPlaylist =(ImageButton)view.findViewById(R.id.image_button_play_playlist);
        mImgBtnPlayFavorite =(ImageButton)view.findViewById(R.id.image_button_play_favorite);
        mImgBtnPlayArtist =(ImageButton)view.findViewById(R.id.image_button_play_artist);
        mImgBtnPlayAlbum =(ImageButton)view.findViewById(R.id.image_button_play_album);

        mImgBtnPlaySong.setOnClickListener(this);
        mImgBtnPlayPlaylist.setOnClickListener(this);
        mImgBtnPlayFavorite.setOnClickListener(this);
        mImgBtnPlayArtist.setOnClickListener(this);
        mImgBtnPlayAlbum.setOnClickListener(this);

        mRlvSong = (RelativeLayout) view.findViewById(R.id.relative_song);
        mRlvPlaylist = (RelativeLayout) view.findViewById(R.id.relative_playlist);
        mRlvFavorite = (RelativeLayout) view.findViewById(R.id.relative_favorite);
        mRlvArtist = (RelativeLayout) view.findViewById(R.id.relative_artist);
        mRlvAlbum = (RelativeLayout) view.findViewById(R.id.relative_album);

        mRlvSong.setOnClickListener(this);
        mRlvPlaylist.setOnClickListener(this);
        mRlvFavorite.setOnClickListener(this);
        mRlvArtist.setOnClickListener(this);
        mRlvAlbum.setOnClickListener(this);

    }
    public void init(){
        mTextViewNumberSong.setText(String.valueOf(Song.listAll(Song.class).size()));
        try{
            mTextViewNumberPlaylist.setText(String.valueOf(Playlist.listAll(Playlist.class).size()));
        }catch (Exception e){
            mTextViewNumberPlaylist.setText("0");
        }
        mTextViewNumberFavorite.setText(String.valueOf(Song.find(Song.class, "is_favorite = ?", "1").size()));
        mTextViewNumberArtist.setText(String.valueOf(Artist.listAll(Artist.class).size()));
        mTextViewNumberAlbum.setText(String.valueOf(Album.listAll(Album.class).size()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.image_button_play_song: {
                Intent intentLocalActivity = new Intent(getActivity(), PlayMusicActivity.class);
                intentLocalActivity.putExtra("type_play", "play_all_song");

                getActivity().startActivity(intentLocalActivity);
                break;
            }
            case R.id.image_button_play_playlist: {
                break;
            }
            case R.id.image_button_play_favorite: {
                Intent intentLocalActivity = new Intent(getActivity(), PlayMusicActivity.class);
                intentLocalActivity.putExtra("type_play", "play_favorite");

                getActivity().startActivity(intentLocalActivity);
                break;
            }
            case R.id.image_button_play_artist: {
                Intent intentLocalActivity = new Intent(getActivity(), LocalActivity.class);
                intentLocalActivity.putExtra("type_play", "artist_list");
                getActivity().startActivity(intentLocalActivity);
                break;
            }
            case R.id.image_button_play_album: {
                break;
            }



            case R.id.relative_song: {
                Intent intentLocalActivity = new Intent(getActivity(), LocalActivity.class);
                intentLocalActivity.putExtra("fragment_open", "song_list");

                getActivity().startActivity(intentLocalActivity);
                break;
            }
            case R.id.relative_playlist: {
                break;
            }
            case R.id.relative_favorite: {
                Intent intentLocalActivity = new Intent(getActivity(), LocalActivity.class);
                intentLocalActivity.putExtra("fragment_open", "favorite_list");

                getActivity().startActivity(intentLocalActivity);
                break;
            }
            case R.id.relative_artist: {
                Intent intentLocalActivity = new Intent(getActivity(), LocalActivity.class);
                intentLocalActivity.putExtra("fragment_open", "artist_list");
                getActivity().startActivity(intentLocalActivity);
                break;
            }
            case R.id.relative_album: {
                Intent intentLocalActivity = new Intent(getActivity(), LocalActivity.class);
                intentLocalActivity.putExtra("fragment_open", "album_list");
                getActivity().startActivity(intentLocalActivity);
                break;
            }
        }

    }
}
