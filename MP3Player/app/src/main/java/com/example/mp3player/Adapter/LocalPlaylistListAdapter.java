package com.example.mp3player.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Interface.ItemClickAlbumToFragment;
import com.example.mp3player.Interface.ItemClickPlaylist;
import com.example.mp3player.Interface.ItemClickPlaylistToFragment;
import com.example.mp3player.Model.Local.Album;
import com.example.mp3player.Model.Local.Playlist;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.List;

public class LocalPlaylistListAdapter extends RecyclerView.Adapter<LocalPlaylistListAdapter.ViewHolder> {
    private ItemClickPlaylistToFragment mItemClickPlaylistToFragment;
    private Context mContext;
    private List<Playlist> mLstPlaylist;


    public LocalPlaylistListAdapter(Context context, List<Playlist> lstPlaylist){
        this.mContext = context;
        this.mLstPlaylist = lstPlaylist;
    }

    @Override
    public LocalPlaylistListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.singer_album_item,parent, false);
        return new LocalPlaylistListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalPlaylistListAdapter.ViewHolder holder, int position) {
        Playlist playlist = mLstPlaylist.get(position);
        holder.idPlaylist = playlist.getId();
        holder.mTxtArtistName.setText(playlist.name);
        List<Song> songPlaylist = Song.find(Song.class, "playlist = ?",  String.valueOf(playlist.getId()));
        holder.mTxtNumSong.setText(String.valueOf(songPlaylist.size()));

    }

    @Override
    public int getItemCount() {
        return mLstPlaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public Long idPlaylist;
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
                    Playlist playlist = mLstPlaylist.get(getPosition());
                    Long id = playlist.getId();
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
            Playlist playlist = mLstPlaylist.get(getPosition());
            Long id = playlist.getId();
//            Intent intent = new Intent(mContext, PlayMusicActivity.class);
//            intent.putExtra("type_play","play_artist");
//            intent.putExtra("artist_id",id);
//            mContext.startActivity(intent);
            mItemClickPlaylistToFragment.onClick(String.valueOf(id));
        }
    }

    public void setOnClick(ItemClickPlaylistToFragment itemClickPlaylistToFragment){
        this.mItemClickPlaylistToFragment = itemClickPlaylistToFragment;
    }
}