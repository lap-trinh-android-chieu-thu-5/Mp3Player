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
import com.example.mp3player.Model.Local.Album;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.List;

public class LocalAlbumListAdapter extends RecyclerView.Adapter<LocalAlbumListAdapter.ViewHolder> {
    private ItemClickAlbumToFragment mItemClickAlbumToFragment;
    private Context mContext;
    private List<Album> mLstAlbum;


    public LocalAlbumListAdapter(Context context, List<Album> lstAlbum){
        this.mContext = context;
        this.mLstAlbum = lstAlbum;
    }

    @Override
    public LocalAlbumListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.singer_album_item,parent, false);
        return new LocalAlbumListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalAlbumListAdapter.ViewHolder holder, int position) {
        Album album = mLstAlbum.get(position);
        holder.idArtist = album.idAlbum;
        holder.mTxtArtistName.setText(album.name);
        List<Song> songArtist = Song.find(Song.class, "artist = ?",  String.valueOf(album.getId()));
        holder.mTxtNumSong.setText(String.valueOf(songArtist.size()));

    }

    @Override
    public int getItemCount() {
        return mLstAlbum.size();
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
                    Album album = mLstAlbum.get(getPosition());
                    Long id = album.getId();
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
            Album album = mLstAlbum.get(getPosition());
            Long id = album.getId();
//            Intent intent = new Intent(mContext, PlayMusicActivity.class);
//            intent.putExtra("type_play","play_artist");
//            intent.putExtra("artist_id",id);
//            mContext.startActivity(intent);
            mItemClickAlbumToFragment.onClick(String.valueOf(id));
        }
    }

    public void setOnClick(ItemClickAlbumToFragment itemClickAlbumToFragment){
        this.mItemClickAlbumToFragment = itemClickAlbumToFragment;
    }
}
