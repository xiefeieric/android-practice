package com.fei.musicclient.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.fei.musicclient.R;
import com.fei.musicclient.util.GlobalConstants;
import com.fei.musicclient.util.HttpUtils;

import org.apache.http.HttpEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Fei on 04/06/2015.
 */
public class DownloadIntentService extends IntentService {

    public DownloadIntentService() {
        super("eric");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final String musicPath = intent.getStringExtra("path");
        final String httpPath = GlobalConstants.GLOBAL_PATH+musicPath;
        Log.i("Path", "Path: " + httpPath);

        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/",musicPath);
//        final File file = new File(getFilesDir(),musicPath);
        if (file.exists()) {
            Log.i("Music","Music already downloaded");
        } else {

            new Thread(new Runnable() {
                @Override
                public void run() {
//                for (int i=1;i<=5;i++) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.i("Download","Download: "+20*i+"%");
//                }
//                Log.i("Download","Download Success");

                    try {
                        HttpEntity entity = HttpUtils.httpRequest(HttpUtils.HTTP_GET_REQUEST, httpPath, null);
                        long musicLength = entity.getContentLength();
                        InputStream is = entity.getContent();
                        Log.i("Path", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/" + musicPath);
                        Log.i("Path", getFilesDir() + "/" + musicPath);
//                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/",musicPath);
//                    File file = new File(getFilesDir(),musicPath);
//                    File file = new File("/mnt/sdcard/",musicPath);
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }

                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] bytes = new byte[1024 * 100];
                        double length = 0.0;
                        double current = 0.0;

                        sendProgress("start download", "start download", "start download");
                        clearNotification();

                        while ((length = is.read(bytes)) != -1) {
                            fos.write(bytes, 0, bytes.length);
                            current = current + length;
                            String progress = Math.floor(current / musicLength * 100) + "%";
                            sendProgress("download progress: " + progress, "download progress: " + progress, "download progress: " + progress);
                        }
                        fos.flush();
                        fos.close();
                        clearNotification();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        sendProgress("download finished", "download finished", "download finished");
        clearNotification();

    }

    private void sendProgress(String ticker, String title, String text) {

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this,DownloadIntentService.class);
//        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        builder = builder.setContentInfo("").setContentText(title).setContentTitle(title)

        .setWhen(System.currentTimeMillis()).setSubText("").setTicker(ticker).setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = builder.build();
        notificationManager.notify(100, notification);

    }

    private void clearNotification(){
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(100);
    }
}
