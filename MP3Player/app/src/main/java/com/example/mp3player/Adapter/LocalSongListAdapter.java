package com.example.mp3player.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

public class LocalSongListAdapter extends RecyclerView.Adapter<LocalSongListAdapter.ViewHolder> {
    public boolean isEdit = false;
    private Context mContext;
    private List<Song> lstSong;

    public LocalSongListAdapter(Context context, List<Song> lstSong){
        this.mContext = context;
        this.lstSong = lstSong;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.local_song_list_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = lstSong.get(position);
        holder.mTxtArtistName.setText(song.artist.name);
        holder.mTxtSongName.setText(song.name);
        holder.songId = song.idSong;
        holder.isFavorite = song.isFavorite;


        //set button like
        if(song.isFavorite == true){
            holder.mImgBtnLike.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.iconloved));
        }else{
            holder.mImgBtnLike.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.iconlove));
        }

        //set show checkbox
        //Khong edit nen khong co check box
        if(isEdit == false){
            holder.checkBoxEdit.setVisibility(View.GONE);
        }else{
            holder.checkBoxEdit.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return lstSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public  boolean isCheck  = false;
        public boolean isFavorite;
        public Long songId;
        TextView mTxtSongName, mTxtArtistName;
        public CheckBox checkBoxEdit;
        ImageButton mImgBtnLike, mImgBtnPlay;
        ImageView mImgViewDisplay;
        public ViewHolder(View itemView) {
            super(itemView);

            //map
            mTxtSongName = itemView.findViewById(R.id.textview_song_name);
            mTxtArtistName = itemView.findViewById(R.id.textview_artist_name);
            checkBoxEdit = itemView.findViewById(R.id.checkbox_edit);
            mImgBtnLike = itemView.findViewById(R.id.image_button_like);
            mImgBtnPlay = itemView.findViewById(R.id.image_button_play);
            mImgViewDisplay = itemView.findViewById(R.id.imageview_display);


            //set on click
            itemView.setOnClickListener(this);

            mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Mo sang cua so choi nhac o day
                    Song song = lstSong.get(getPosition());
                    Long id = song.idSong;
                    Intent intent = new Intent(mContext, PlayMusicActivity.class);
                    intent.putExtra("type_play","play_single_song");
                    intent.putExtra("song_id",id);
                    mContext.startActivity(intent);

                }
            });

            mImgBtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isFavorite){
                        //huy like
                        mImgBtnLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.iconlove));
                        isFavorite = false;

                        //cap nhat db
                        List<Song> songCheckList = Song.find(Song.class, "id_song = ?", Long.toString(songId));
                        Song song = songCheckList.get(0);
                        if(song != null){
                            song.isFavorite = false;
                            song.save();
                        }


                    }else{
                        //like lai
                        mImgBtnLike.setBackground(ContextCompat.getDrawable(mContext, R.drawable.iconloved));
                        isFavorite = true;

                        List<Song> songCheckList = Song.find(Song.class, "id_song = ?", Long.toString(songId));
                        Song song = songCheckList.get(0);
                        if(song != null){
                            song.isFavorite = true;
                            song.save();
                        }

                    }
                }
            });

            checkBoxEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((CheckBox)v).isChecked()){
                        isCheck = true;
                    }else{
                        isCheck = false;
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            //Mo sang cua so choi nhac o day
            Song song = lstSong.get(getPosition());
            Long id = song.idSong;
            Intent intent = new Intent(mContext, PlayMusicActivity.class);
            intent.putExtra("type_play","play_single_song");
            intent.putExtra("song_id",id);
            mContext.startActivity(intent);
        }
    }
}
