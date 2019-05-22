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
import com.example.mp3player.Model.Host.Song;
import com.example.mp3player.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OnlineSongListAdapter  extends RecyclerView.Adapter<OnlineSongListAdapter.ViewHolder> {
    private Context mContext;
    private List<Song> lstSong;

    public OnlineSongListAdapter(Context context, List<Song> lstSong){
        this.mContext = context;
        this.lstSong = lstSong;
    }

    @Override
    public OnlineSongListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.song_online_item,parent, false);
        return new OnlineSongListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OnlineSongListAdapter.ViewHolder holder, int position) {
        Song song = lstSong.get(position);
        holder.mTxtArtistName.setText(song.getTen());
        holder.mTxtSongName.setText(song.getTen());
        holder.songId = Long.parseLong(song.getId());
        Picasso.with(mContext).load(lstSong.get(position).getHinh()).into(holder.mImgViewDisplay);
    }

    @Override
    public int getItemCount() {
        return lstSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public Long songId;
        TextView mTxtSongName, mTxtArtistName;
        ImageButton mImgBtnPlay;
        ImageView mImgViewDisplay;
        public ViewHolder(View itemView) {
            super(itemView);

            //map
            mTxtSongName = itemView.findViewById(R.id.textview_song_name);
            mTxtArtistName = itemView.findViewById(R.id.textview_artist_name);
            mImgBtnPlay = itemView.findViewById(R.id.image_button_play);
            mImgViewDisplay = itemView.findViewById(R.id.imageview_display);

            //set on click
            itemView.setOnClickListener(this);

            mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    //Mo sang cua so choi nhac o day
                    Song song = lstSong.get(getPosition());
                    Intent intent = new Intent(mContext, PlayMusicActivity.class);
                    intent.putExtra("type_play","play_single_song_online");
                    intent.putExtra("song",song);
                    mContext.startActivity(intent);

                }
            });

        }

        @Override
        public void onClick(View v) {
            //Mo sang cua so choi nhac o day
            Song song = lstSong.get(getPosition());
            Intent intent = new Intent(mContext, PlayMusicActivity.class);
            intent.putExtra("type_play","play_single_song_online");
            intent.putExtra("song",song);
            mContext.startActivity(intent);
        }

    }

    public void setLstSong(List<Song> lstSong){
        this.lstSong = lstSong;
    }
}
