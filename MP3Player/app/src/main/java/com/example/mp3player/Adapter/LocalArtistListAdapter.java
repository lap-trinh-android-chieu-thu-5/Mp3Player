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
import com.example.mp3player.Model.Local.Artist;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.ArrayList;
import java.util.List;

public class LocalArtistListAdapter extends RecyclerView.Adapter<LocalArtistListAdapter.ViewHolder> {
    private Context mContext;
    private List<Artist> mLstArtist;


    public LocalArtistListAdapter(Context context, List<Artist> lstArtist){
        this.mContext = context;
        this.mLstArtist = lstArtist;
    }

    @Override
    public LocalArtistListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.singer_album_item,parent, false);
        return new LocalArtistListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalArtistListAdapter.ViewHolder holder, int position) {
        Artist artist = mLstArtist.get(position);
        holder.idArtist = artist.idArtist;
        holder.mTxtArtistName.setText(artist.name);
        int a = 0;
//        try{
//            holder.mTxtNumSong.setText(Song.find(Song.class, "artist = ?", String.valueOf(artist.idArtist)).size());
//        }catch (Exception e){
//            int a = 0;
//        }
    }

    @Override
    public int getItemCount() {
        return mLstArtist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public Long idArtist;
        TextView mTxtArtistName, mTxtNumSong;
        ImageButton mImgBtnPlay;
        ImageView mImgViewDisplay;
        public ViewHolder(View itemView) {
            super(itemView);
            //map
            mTxtArtistName = itemView.findViewById(R.id.textview_name);
            mTxtNumSong = itemView.findViewById(R.id.textview_number);

            mImgBtnPlay = itemView.findViewById(R.id.image_button_play);
            mImgViewDisplay = itemView.findViewById(R.id.imageview_display);

            //set on click
            itemView.setOnClickListener(this);

            mImgBtnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Mo sang cua so choi nhac o day
                    Artist artist = mLstArtist.get(getPosition());
                    Long id = artist.idArtist;
                    Intent intent = new Intent(mContext, PlayMusicActivity.class);
                    intent.putExtra("type_play","play_artist");
                    intent.putExtra("artist_id",id);
                    mContext.startActivity(intent);

                }
            });

        }

        @Override
        public void onClick(View v) {
            //Mo sang cua so choi nhac o day
            Artist artist = mLstArtist.get(getPosition());
            Long id = artist.idArtist;
            Intent intent = new Intent(mContext, PlayMusicActivity.class);
            intent.putExtra("type_play","play_artist");
            intent.putExtra("artist_id",id);
            mContext.startActivity(intent);
        }
    }
}
