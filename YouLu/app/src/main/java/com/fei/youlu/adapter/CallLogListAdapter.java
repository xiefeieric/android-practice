package com.fei.youlu.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fei.youlu.R;
import com.fei.youlu.entity.CallLog;
import com.fei.youlu.utils.DataUtils;

import java.util.List;

/**
 * Created by Fei on 10/06/2015.
 */
public class CallLogListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CallLog> mList;

    public CallLogListAdapter(Context context, List<CallLog> list) {
        mContext = context;
        mList = list;
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

        ViewHolder holder = null;

        if (convertView==null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.call_log_list_item,null);
            ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            holder.ivPhoto = ivPhoto;
            TextView tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
            holder.tvNumber = tvNumber;
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvName = tvName;
            TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvDate = tvDate;
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        CallLog callLog = mList.get(position);


        Bitmap bitmap = loadBitmap(callLog.getPhotoId());
        if (bitmap!=null) {
            holder.ivPhoto.setImageBitmap(bitmap);
        } else {
            holder.ivPhoto.setImageResource(R.drawable.img01g_15);
        }


        holder.tvNumber.setText(callLog.getNumber());


        if (callLog.getType()== android.provider.CallLog.Calls.MISSED_TYPE) {
            holder.tvName.setText(callLog.getName());
            holder.tvName.setTextColor(Color.RED);
        } else {
            holder.tvName.setText(callLog.getName());
        }

        if (callLog.getName()==null) {
            holder.tvName.setText("Unknown");
        }


        holder.tvDate.setText(DataUtils.parse(callLog.getDate()));
        return convertView;
    }

    private Bitmap loadBitmap(int photoId) {

        Bitmap bitmap = null;

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] column = new String[] {
                ContactsContract.Data.DATA15
        };
        Cursor c = resolver.query(uri, column, ContactsContract.Data._ID + "=" + photoId, null, null);
        if (c.moveToNext()) {
            byte[] bytes = c.getBlob(0);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        c.close();
        return bitmap;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvNumber;
        TextView tvName;
        TextView tvDate;
    }
}
