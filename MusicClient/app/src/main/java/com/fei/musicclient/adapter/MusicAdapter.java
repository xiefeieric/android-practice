package com.fei.musicclient.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fei.musicclient.R;
import com.fei.musicclient.entity.Music;
import com.fei.musicclient.util.BitmapUtils;
import com.fei.musicclient.util.GlobalConstants;
import com.fei.musicclient.util.HttpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fei on 02/06/2015.
 */
public class MusicAdapter extends BaseAdapter {

    private Context mContext;
    private List<Music> mMusics;
    private ListView mListView;
    private List<ImageLoadTask> mImageLoadTaskList = new ArrayList<ImageLoadTask>();
    private final Thread workThread;
    private Boolean isLoop = true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MUSIC_IMAGE_LOADED:
                    ImageLoadTask loadTask = (ImageLoadTask) msg.obj;

                    if (loadTask!=null) {
                        ImageView imageView = (ImageView) mListView.findViewWithTag(loadTask.position);
                        if (imageView!=null) {
                            imageView.setImageBitmap(loadTask.bitmap);
                        }
                    }

                    break;
            }
        }
    };

    private HashMap<String,SoftReference<Bitmap>> cache = new HashMap<String,SoftReference<Bitmap>>();

    public static final int MUSIC_IMAGE_LOADED = 1;

    public MusicAdapter(Context context, List<Music> musics, ListView listView) {
        mContext = context;
        mMusics = musics;
        mListView = listView;

        workThread = new Thread(){
            @Override
            public void run() {

                while (isLoop) {

                    if (!mImageLoadTaskList.isEmpty()) {

                        ImageLoadTask task = mImageLoadTaskList.remove(0);
                        try {
                            String path = GlobalConstants.GLOBAL_PATH+task.path;
                            Bitmap image = imageLoad(path);
                            task.bitmap = image;
                            Message msg = new Message();
                            msg.what = MUSIC_IMAGE_LOADED;
                            msg.obj = task;
                            mHandler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            synchronized (workThread) {
                            workThread.wait();
                        }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                }

            }
        };

        workThread.start();
    }

    private Bitmap imageLoad(String path) throws IOException {

        if (cache!=null && cache.get(path)!=null) {

            SoftReference<Bitmap> reference = cache.get(path);
            Log.i("Bitmap", "Load from MemoryCache");
            return reference.get();


        }

        String fileCachePath = mContext.getCacheDir()+path;
//        Log.i("FileCachePath",fileCachePath);
        File file = new File(fileCachePath);
        if (file.exists()) {
            Bitmap bitmapFile = BitmapUtils.loadBitmap(fileCachePath);
            if (bitmapFile != null) {
                Log.i("Bitmap", "Load from FileCache");
                cache.put(path, new SoftReference(bitmapFile));
                return bitmapFile;
            }
        }




        HttpEntity entity = HttpUtils.httpRequest(HttpUtils.HTTP_GET_REQUEST, path, null);
        byte[] bytes = EntityUtils.toByteArray(entity);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Bitmap bitmap = BitmapUtils.loadBitmap(bytes,50,50);

        cache.put(path,new SoftReference(bitmap));
        BitmapUtils.savePic(new File(fileCachePath),bitmap);

        return bitmap;
    }

    @Override
    public int getCount() {
        return mMusics.size();
    }

    @Override
    public Object getItem(int position) {
        return mMusics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        if (convertView==null) {
            convertView = View.inflate(mContext, R.layout.list_item_music,null);
            ImageView ivMusicIcon = (ImageView) convertView.findViewById(R.id.ivMusicIcon);
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            TextView tvSinger = (TextView) convertView.findViewById(R.id.tvSinger);
            TextView tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            TextView tvDuration = (TextView) convertView.findViewById(R.id.tvDuration);
            holder = new ViewHolder();
            holder.ivMusicIcon = ivMusicIcon;
            holder.tvName = tvName;
            holder.tvSinger = tvSinger;
            holder.tvAuthor = tvAuthor;
            holder.tvDuration = tvDuration;
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        Music music = mMusics.get(position);
        holder.tvName.setText(music.getName());
        holder.tvSinger.setText(music.getSinger());
        holder.tvAuthor.setText(music.getAuthor());
        holder.tvDuration.setText(music.getDurationtime());

        holder.ivMusicIcon.setTag(position);

        ImageLoadTask imageLoadTask = new ImageLoadTask();
        imageLoadTask.path = music.getAlbumpic();
        imageLoadTask.position = position;
        mImageLoadTaskList.add(imageLoadTask);

        synchronized (workThread) {
            workThread.notify();
        }

        return convertView;
    }

    public void stopThread() {
        synchronized (workThread) {
            isLoop = false;
            workThread.notify();
        }
    }
}

class ImageLoadTask {
    String path;
    Bitmap bitmap;
    int position;
}

class ViewHolder {

    ImageView ivMusicIcon;
    TextView tvName;
    TextView tvSinger;
    TextView tvAuthor;
    TextView tvDuration;
}
