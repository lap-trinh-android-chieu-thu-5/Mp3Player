package com.example.mp3player.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mp3player.Activity.ListSongOnlineActivity;
import com.example.mp3player.Adapter.OnlinePlaylistAdapter;
import com.example.mp3player.Model.Host.Playlist;
import com.example.mp3player.R;
import com.example.mp3player.Service.APIService;
import com.example.mp3player.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {
    View view;
    ArrayList<Playlist> playlists;
    ListView mListViewPlaylist;
    TextView mTextViewTitlePlaylist, mTextViewMorePlayist;
    OnlinePlaylistAdapter onlinePlaylistAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mListViewPlaylist = view.findViewById(R.id.listview_playlist);
        mTextViewTitlePlaylist = view.findViewById(R.id.textview_title);
        getData();
        return view;
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.getDataPlaylist();
        callback.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                playlists = (ArrayList<Playlist>)response.body();
                onlinePlaylistAdapter = new OnlinePlaylistAdapter(getActivity(), android.R.layout.simple_list_item_1,playlists);
                mListViewPlaylist.setAdapter(onlinePlaylistAdapter);
                mListViewPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), ListSongOnlineActivity.class);
                        intent.putExtra("itemplaylist", playlists.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                int b = 0;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
