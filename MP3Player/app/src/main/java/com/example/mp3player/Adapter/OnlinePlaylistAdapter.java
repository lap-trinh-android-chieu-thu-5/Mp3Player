package com.example.mp3player.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp3player.Model.Host.Playlist;
import com.example.mp3player.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OnlinePlaylistAdapter extends ArrayAdapter<Playlist> {
    public OnlinePlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
        TextView txtPlaylistName;
        ImageView imageViewBackground, imageViewPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.playlist_online_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageViewBackground = convertView.findViewById(R.id.imageview_background_playlist);
            viewHolder.txtPlaylistName = convertView.findViewById(R.id.textview_playlist_name);
            viewHolder.imageViewPlaylist = convertView.findViewById(R.id.imageview_playlist);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Playlist playlist = getItem(position);
        Picasso.with(getContext()).load(playlist.getHinhnen()).into(viewHolder.imageViewBackground);
        //Picasso.with(getContext()).load(playlist.getIcon()).into(viewHolder.imageViewPlaylist);
        viewHolder.txtPlaylistName.setText(playlist.getTen());
        return convertView;
    }
}
