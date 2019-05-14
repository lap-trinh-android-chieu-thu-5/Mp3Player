package com.example.mp3player.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.mp3player.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Song_Disc extends Fragment {
    View view;
    CircleImageView mCircleImageView;
    ObjectAnimator mOjectAnimator;

    private long mCurrentPlayTime = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_song_disc, container, false);
        map();
        return view;
    }

    public void startRotate(){
        mOjectAnimator.start();
    }
    public  void pauseRotate(){
        mCurrentPlayTime = mOjectAnimator.getCurrentPlayTime();
        mOjectAnimator.cancel();
    }

    public  void resumeRotate(){
        mOjectAnimator.start();
        mOjectAnimator.setCurrentPlayTime(mCurrentPlayTime);
    }

    private  void map(){
        mCircleImageView = view.findViewById(R.id.image_view_circle);

        mOjectAnimator = ObjectAnimator.ofFloat(mCircleImageView, "rotation", 0f, 360f);
        mOjectAnimator.setDuration(5000);
        mOjectAnimator.setRepeatCount(10000);

        mOjectAnimator.setInterpolator(new LinearInterpolator());
    }
}
