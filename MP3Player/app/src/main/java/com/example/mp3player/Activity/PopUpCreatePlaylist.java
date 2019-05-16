package com.example.mp3player.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mp3player.Model.Local.Playlist;
import com.example.mp3player.Model.Local.Song;
import com.example.mp3player.R;

import java.util.List;

public class PopUpCreatePlaylist extends Activity implements View.OnClickListener {
    private TextInputEditText mTextInputEditText;
    private ImageButton mImageButtonSave, mImageButtonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_create_playlist);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6), (int)(height*.2));

        map();
        init();
    }

    private void map(){
        mTextInputEditText = findViewById(R.id.textinput_playlist_name);
        mImageButtonSave = findViewById(R.id.image_button_save);
        mImageButtonCancel = findViewById(R.id.image_button_cancel);
    }
    private void init(){
        mImageButtonCancel.setOnClickListener(this);
        mImageButtonSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.image_button_save:
                String playlistName = mTextInputEditText.getText().toString();
                try {
                    List<Playlist> lstPlaylist = Playlist.find(Playlist.class, "name = ?",  playlistName);
                    if(lstPlaylist.size() > 0){
                        Toast toast=Toast.makeText(getApplicationContext(),"Đã tồn tại playlist này",Toast.LENGTH_SHORT);
                        toast.setMargin(50,50);
                        toast.show();
                    }else{
                        Playlist playlist = new Playlist(playlistName,"");
                        playlist.save();
                        finish();
                    }
                }catch (Exception e){
                    Playlist playlist = new Playlist(playlistName,"");
                    try{
                        playlist.save();
                    }catch (Exception ex){
                        int  a= 0;
                    }
                    finish();
                }
                break;
            case R.id.image_button_cancel:
                finish();
                break;
        }
    }
}
