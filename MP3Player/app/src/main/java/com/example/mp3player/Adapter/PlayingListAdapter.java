package com.example.mp3player.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mp3player.Interface.ItemClickListener;
import com.example.mp3player.Interface.ItemClickListenerToFragment;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.List;

public class PlayingListAdapter extends RecyclerView.Adapter<PlayingListAdapter.ViewHolder> {
    Context mContext;
    List<Song> mLstSong;
    List<com.example.mp3player.Model.Host.Song> mLstSongOnline;
    private ItemClickListenerToFragment mItemClickListenerToFragment;

    public  PlayingListAdapter(Context context, List<Song> lstSong){
        this.mContext = context;
        this.mLstSong = lstSong;
    }

    public PlayingListAdapter(Context content, List<com.example.mp3player.Model.Host.Song> mLstSongOnline, int temp){
        this.mContext = content;
        this.mLstSongOnline = mLstSongOnline;
        int a= 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.playing_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(mLstSongOnline != null){
            com.example.mp3player.Model.Host.Song song = mLstSongOnline.get(position);
            int a=0;
            holder.mTxtArtistName.setText(song.getTencasy());
            holder.mTxtIndex.setText(position + 1 + "");
            holder.mTxtSongName.setText(song.getTen());

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    //truyen du lieu cho fragment o day
                    if(mItemClickListenerToFragment != null){
                        mItemClickListenerToFragment.onClick(view, position, isLongClick);
                    }
                }
            });
        }else{
            Song song = mLstSong.get(position);
            int a=0;
            holder.mTxtArtistName.setText(song.artist.name);
            holder.mTxtIndex.setText(position + 1 + "");
            holder.mTxtSongName.setText(song.name);

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    //truyen du lieu cho fragment o day
                    if(mItemClickListenerToFragment != null){
                        mItemClickListenerToFragment.onClick(view, position, isLongClick);
                    }
                }
            });
        }

    }

    public void setmItemClickListenerToFragment(ItemClickListenerToFragment itemClickListenerToFragment){
        this.mItemClickListenerToFragment = itemClickListenerToFragment;
    }
    @Override
    public int getItemCount() {
        return mLstSong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private ItemClickListener itemClickListener;

        TextView mTxtIndex, mTxtSongName, mTxtArtistName;
        public ViewHolder(View itemView) {
            super(itemView);
            mTxtIndex = itemView.findViewById(R.id.textview_index);
            mTxtSongName = itemView.findViewById(R.id.textview_song_name);
            mTxtArtistName = itemView.findViewById(R.id.textview_artist_name);

            //set on click
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
}
