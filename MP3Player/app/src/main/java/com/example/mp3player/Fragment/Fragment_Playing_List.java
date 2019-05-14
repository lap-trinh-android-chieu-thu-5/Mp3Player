package com.example.mp3player.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mp3player.Activity.PlayMusicActivity;
import com.example.mp3player.Adapter.PlayingListAdapter;
import com.example.mp3player.Interface.ItemClickListenerToActivity;
import com.example.mp3player.Interface.ItemClickListenerToFragment;
import com.example.mp3player.R;

public class Fragment_Playing_List extends Fragment implements ItemClickListenerToFragment {
    private ItemClickListenerToActivity mItemClickListenerToActivity;
    View view;
    RecyclerView mRecyclerView;
    PlayingListAdapter mPlayingListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playing_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview_playing_list);

        if(PlayMusicActivity.lstSong.size() > 0){
            mPlayingListAdapter = new PlayingListAdapter(getActivity(), PlayMusicActivity.lstSong);
            //set item listner
            mPlayingListAdapter.setmItemClickListenerToFragment(this);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mPlayingListAdapter);
        }
        return view;
    }


    @Override
    public void onClick(View view, int position, boolean isLongClick) {
        this.mItemClickListenerToActivity.onClick(view,position,isLongClick);
    }
    public void setmItemClickListenerToActivity( ItemClickListenerToActivity mItemClickListenerToActivity){
        this.mItemClickListenerToActivity = mItemClickListenerToActivity;
    }
}
