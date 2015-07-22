package com.fei.musicclient.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fei.musicclient.R;
import com.fei.musicclient.adapter.MusicAdapter;
import com.fei.musicclient.business.MusicBusiness;
import com.fei.musicclient.entity.Music;
import com.fei.musicclient.services.DownloadIntentService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener {

    private ListView lvMusic;
    private MusicAdapter adapter;
    private Button btnDownload,btnCancel,btnLike;
    private List<Music> mMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        new MusicBusiness(this).execute();
        initListeners();

//        File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
//        Log.i("SDStatus", "sd card can read: " +sd.canRead());
//
//        Log.i("SDStatus", "sd card can write: " +sd.canWrite());
    }

    private void initViews() {

        lvMusic = (ListView) findViewById(R.id.lvMusic);
    }

    private void initListeners() {

        lvMusic.setOnItemLongClickListener(this);

        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,AtyPlayMusic.class);
                intent.putExtra("position",position);
                intent.putExtra("list",(ArrayList<Music>)mMusicList);
                startActivity(intent);
            }
        });


    }

    public void updateListView(List<Music> musics) {

        this.mMusicList = musics;
        adapter = new MusicAdapter(this,musics,lvMusic);
        lvMusic.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.stopThread();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        final Music music = mMusicList.get(position);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setContentView(R.layout.alert_dialog_music);

        btnDownload = (Button) alertDialog.getWindow().findViewById(R.id.btnDownload);
        btnCancel = (Button) alertDialog.getWindow().findViewById(R.id.btnCancel);
        btnLike = (Button) alertDialog.getWindow().findViewById(R.id.btnLike);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Download Started", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
                intent.putExtra("path",music.getMusicpath());
                startService(intent);
                alertDialog.cancel();
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Like it",Toast.LENGTH_SHORT).show();
                alertDialog.cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        return false;
    }


}
