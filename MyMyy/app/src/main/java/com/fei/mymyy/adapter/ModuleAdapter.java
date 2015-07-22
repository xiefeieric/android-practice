package com.fei.mymyy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.fei.mymyy.R;
import com.fei.mymyy.entity.PageModule;
import com.fei.mymyy.utils.GlobalConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by Fei on 25/06/15.
 */
public class ModuleAdapter extends BaseAdapter {

    private Context mContext;
    private List<PageModule> mList;
    private DisplayImageOptions mOptions;
    private ImageLoader mImageLoader = ImageLoader.getInstance();

    public ModuleAdapter(Context context, List<PageModule> list) {
        mContext = context;
        mList = list;
        mOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
//        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView==null) {
            convertView = View.inflate(mContext,R.layout.item_gv_module,null);
            imageView = (ImageView) convertView.findViewById(R.id.imageView);
        } else {

            imageView = (ImageView) convertView.findViewById(R.id.imageView);
        }

//        Log.i("test","path: "+GlobalConstants.BASE_URL+mList.get(position).getSnapShot());

        mImageLoader.displayImage(GlobalConstants.BASE_URL+mList.get(position).getSnapShot(),imageView,mOptions);

        return convertView;
    }
}
