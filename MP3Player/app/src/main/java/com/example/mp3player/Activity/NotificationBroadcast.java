package com.example.mp3player.Activity;

import android.app.NativeActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.mp3player.R;

public class NotificationBroadcast extends BroadcastReceiver {

    public static final String NOTIFY_PREVIOUS = "com.tutorialsface.notificationdemo.previous";
    public static final String NOTIFY_DELETE = "com.tutorialsface.notificationdemo.delete";
    public static final String NOTIFY_PAUSE = "com.tutorialsface.notificationdemo.pause";
    public static final String NOTIFY_PLAY = "com.tutorialsface.notificationdemo.play";
    public static final String NOTIFY_NEXT = "com.tutorialsface.notificationdemo.next";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NOTIFY_PLAY)) {
            MainActivity.serviceMusicPlayer.playSong();
        } else if (intent.getAction().equals(NOTIFY_PAUSE)) {

            if(MainActivity.serviceMusicPlayer.isPlaying == false){
                MainActivity.serviceMusicPlayer.start();
            }else {
                MainActivity.serviceMusicPlayer.pausePlayer();
            }


        } else if (intent.getAction().equals(NOTIFY_NEXT)) {
            MainActivity.serviceMusicPlayer.playNext();


        } else if (intent.getAction().equals(NOTIFY_DELETE)) {

        }else if (intent.getAction().equals(NOTIFY_PREVIOUS)) {
            MainActivity.serviceMusicPlayer.playPrev();
        }
        customSimpleNotification(MainActivity.serviceMusicPlayer.context);
    }

    public void customSimpleNotification(Context context){
        RemoteViews simpleView;
        if(MainActivity.serviceMusicPlayer.isPlaying == true){
            simpleView = new RemoteViews(context.getPackageName(), R.layout.custome_notification);

        }else{
            simpleView = new RemoteViews(context.getPackageName(), R.layout.custom_notification2);
        }

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.iconlocalmusic)
                .setContentTitle("Custom Big View").build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.contentView = simpleView;
        notification.contentView.setTextViewText(R.id.textview_song_name, MainActivity.serviceMusicPlayer.mSongTitle);


        setListeners(simpleView, context);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(22, notification);

        MainActivity.notification = notification;
    }

    private static void setListeners(RemoteViews view, Context context) {//
        Intent previous = new Intent(NOTIFY_PREVIOUS);
        Intent delete = new Intent(NOTIFY_DELETE);
        Intent pause = new Intent(NOTIFY_PAUSE);
        Intent next = new Intent(NOTIFY_NEXT);
        Intent play = new Intent(NOTIFY_PLAY);

        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_previous, pPrevious);//button 1


        PendingIntent pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_pause, pPause);

        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_next, pNext);

        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_play, pPlay);
    }

}