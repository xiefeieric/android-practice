package com.fei.musicclient.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.fei.musicclient.entity.Music;
import com.fei.musicclient.util.GlobalConstants;

import java.io.IOException;
import java.util.ArrayList;

public class MyService extends Service {

    private ArrayList<Music> mMusics;
    private int position;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private boolean isLoop = true;
    private MusicControlReceiver mMusicControlReceiver;
    private IntentFilter mIntentFilter;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isLoop) {

                    if (mMediaPlayer.isPlaying()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int currentTime = mMediaPlayer.getCurrentPosition();
//                        Log.i("Test","current time: "+currentTime);
                        int totalTime = mMediaPlayer.getDuration();
//                        Log.i("Test","totl time: "+totalTime);
                        Intent intent = new Intent();
                        intent.setAction(GlobalConstants.ACTION_UPDATE_MUSIC_PROGRESS);
                        intent.putExtra("currentTime", currentTime);
                        intent.putExtra("totalTime",totalTime);
                        sendBroadcast(intent);
                    }

                }
            }
        }).start();

        mMusicControlReceiver = new MusicControlReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(GlobalConstants.ACTION_PLAY_PAUSE_MUSIC);
        mIntentFilter.addAction(GlobalConstants.ACTION_NEXT_MUSIC);
        mIntentFilter.addAction(GlobalConstants.ACTION_PRE_MUSIC);
        mIntentFilter.addAction(GlobalConstants.ACTION_SEEK_BAR_CHANGE);
        registerReceiver(mMusicControlReceiver, mIntentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isLoop = false;
        unregisterReceiver(mMusicControlReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mMusics = (ArrayList<Music>) intent.getSerializableExtra("list");
        position = intent.getIntExtra("position",0);
        playMusic();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(),"onCompletion",Toast.LENGTH_SHORT).show();
                nextMusic();
            }
        });


        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void playMusic() {
        Music music = mMusics.get(position);

        try {
            Log.i("test", "position: " + position);
            mMediaPlayer.reset();
            Log.i("test", "reset");
            mMediaPlayer.setDataSource(GlobalConstants.GLOBAL_PATH + music.getMusicpath());
            Log.i("test", "setDataSource: " + GlobalConstants.GLOBAL_PATH + music.getMusicpath());
            mMediaPlayer.prepare();
            Log.i("test", "prepare");
            mMediaPlayer.start();
            Log.i("test", "start");

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Music is invalid",Toast.LENGTH_SHORT).show();
            nextMusic();

        }

//        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                Log.i("test","onError");
//                mp.reset();
//                return false;
//            }
//        });

        Intent intent = new Intent();
        intent.setAction(GlobalConstants.ACTION_UPDATE_MUSIC_INFO);
        intent.putExtra("music", music);
        sendBroadcast(intent);
    }

    public void playOrPause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }
    }

    public void nextMusic() {

        if (position<(mMusics.size()-1)) {
            position++;
            Log.i("test","position next:" + position);

        }
        playMusic();
//        Toast.makeText(this,"nextMusic clicked",Toast.LENGTH_SHORT).show();
    }

    public void preMusic() {
        if (position>0) {
            position--;
        }
        playMusic();
    }

    class MusicControlReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(GlobalConstants.ACTION_PLAY_PAUSE_MUSIC)) {
                playOrPause();
            } else if (action.equals(GlobalConstants.ACTION_NEXT_MUSIC)) {
                nextMusic();
            } else if (action.equals(GlobalConstants.ACTION_PRE_MUSIC)) {
                preMusic();
            } else if (action.equals(GlobalConstants.ACTION_SEEK_BAR_CHANGE)) {
                int progress = intent.getIntExtra("progress",0);
                mMediaPlayer.seekTo(progress);
            }
        }
    }


}
