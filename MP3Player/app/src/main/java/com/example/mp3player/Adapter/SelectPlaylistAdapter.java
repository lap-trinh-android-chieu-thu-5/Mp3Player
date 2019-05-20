package com.example.mp3player.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Activity.PopUpAddPlaylist;
import com.example.mp3player.Interface.OnFinishPopUp;
import com.example.mp3player.Model.Local.Playlist;
import com.example.mp3player.Model.Local.Playlist_Song;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.List;

public class SelectPlaylistAdapter extends RecyclerView.Adapter<SelectPlaylistAdapter.ViewHolder> {
    private Context mContext;
    private List<Playlist> mListPlaylist;
    private String mSongId;
    private OnFinishPopUp onFinishPopUp;

    public SelectPlaylistAdapter(Context context, List<Playlist> lstPlaylist,  String songId){
        this.mContext = context;
        this.mListPlaylist = lstPlaylist;
        this.mSongId = songId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.popup_playlist_item,parent, false);
        return new SelectPlaylistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Playlist playlist = mListPlaylist.get(position);
        holder.playlist = playlist;
        holder.textView.setText(playlist.name);
    }

    @Override
    public int getItemCount() {
        return mListPlaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public Playlist playlist;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview_playlist_name);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            List<Song> songs = Song.find(Song.class, "id_song = ?", mSongId);
            //List<Playlist_Song> playlist_songs =  Playlist_Song.find(Playlist_Song.class, "playlist = ? && song = ?",  String.valueOf(this.playlist.getId()), String.valueOf(songs.get(0).getId()));
            int a = 0;
            //if(playlist_songs.size() == 0){
                Playlist_Song playlist_song = new Playlist_Song( this.playlist, songs.get(0));
                playlist_song.save();
            //}
            onFinishPopUp.OnFinish();
        }
    }
    public void setOnFinish(OnFinishPopUp onFinishPopUp){
        this.onFinishPopUp = onFinishPopUp;
    }
}
