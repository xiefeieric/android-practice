package com.fei.musicclient.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fei.musicclient.R;
import com.fei.musicclient.entity.Music;
import com.fei.musicclient.services.MyService;
import com.fei.musicclient.util.BitmapUtils;
import com.fei.musicclient.util.GlobalConstants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AtyPlayMusic extends Activity {

    private TextView tvName,tvCurrentTime,tvTotalTime;
    private ImageView ivAlbum,ivPlay,ivPre,ivNext;
    private SeekBar mSeekBar;
    private MusicProgressUpdateReceiver mMusicProgressUpdateReceiver;
    private IntentFilter mIntentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        initViews();
        initListeners();

        Intent intent = getIntent();
        ArrayList<Music> musics = (ArrayList<Music>) intent.getSerializableExtra("list");
        int position = intent.getIntExtra("position",0);

        //start service
        Intent serviceIntent = new Intent(AtyPlayMusic.this, MyService.class);
        serviceIntent.putExtra("position",position);
        serviceIntent.putExtra("list", musics);
        startService(serviceIntent);

        mMusicProgressUpdateReceiver = new MusicProgressUpdateReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(GlobalConstants.ACTION_UPDATE_MUSIC_PROGRESS);
        mIntentFilter.addAction(GlobalConstants.ACTION_UPDATE_MUSIC_INFO);
        registerReceiver(mMusicProgressUpdateReceiver, mIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMusicProgressUpdateReceiver);
    }

    private void initViews() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvCurrentTime = (TextView) findViewById(R.id.textView3);
        tvTotalTime = (TextView) findViewById(R.id.textView4);
        ivAlbum = (ImageView) findViewById(R.id.imageView3);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar1);
        ivPlay = (ImageView) findViewById(R.id.ivPlay);
        ivNext = (ImageView) findViewById(R.id.ivNext);
        ivPre = (ImageView) findViewById(R.id.ivPre);

    }

    private void initListeners() {

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Intent intent = new Intent();
                    intent.setAction(GlobalConstants.ACTION_SEEK_BAR_CHANGE);
                    intent.putExtra("progress",progress);
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(GlobalConstants.ACTION_PLAY_PAUSE_MUSIC);
                sendBroadcast(intent);
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(GlobalConstants.ACTION_NEXT_MUSIC);
                sendBroadcast(intent);
            }
        });

        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(GlobalConstants.ACTION_PRE_MUSIC);
                sendBroadcast(intent);
            }
        });

    }


    class MusicProgressUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GlobalConstants.ACTION_UPDATE_MUSIC_PROGRESS)) {
                int currentTime = intent.getIntExtra("currentTime",0);
                int totalTime = intent.getIntExtra("totalTime",0);
                tvCurrentTime.setText(new SimpleDateFormat("mm:ss").format(new Date(currentTime)));
                tvTotalTime.setText(new SimpleDateFormat("mm:ss").format(new Date(totalTime)));
                mSeekBar.setMax(totalTime);
                mSeekBar.setProgress(currentTime);
            } else if (intent.getAction().equals(GlobalConstants.ACTION_UPDATE_MUSIC_INFO)) {
                Music music = (Music) intent.getSerializableExtra("music");
                tvName.setText(music.getName());

                //path: /data/data/com.fei.musicclient/cache/images/junshengjinshi.jpg
                String path = getApplicationContext().getCacheDir()+GlobalConstants.GLOBAL_PATH+music.getAlbumpic();
                Log.i("path","path: "+path);
                ivAlbum.setImageBitmap(BitmapUtils.loadBitmap(path));
            }

        }
    }

}
