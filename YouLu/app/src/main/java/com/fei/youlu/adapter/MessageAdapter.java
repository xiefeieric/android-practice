package com.fei.youlu.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fei.youlu.R;
import com.fei.youlu.entity.Sms;
import com.fei.youlu.utils.DataUtils;

import java.util.List;

/**
 * Created by Fei on 12/06/2015.
 */
public class MessageAdapter extends BaseAdapter {

    private Context mContext;
    private List<Sms> mSmsList;
    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;
    private String mNumber;

    public MessageAdapter(Context context, List<Sms> smsList, String number) {
        mContext = context;
        mSmsList = smsList;
        mNumber = number;
    }

    @Override
    public int getCount() {
        return mSmsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSmsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getType(int position){
        Sms sms=mSmsList.get(position);
        if(sms.getType()==1){
            return TYPE_LEFT;
        }else{
            return TYPE_RIGHT;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        Sms sms = mSmsList.get(position);


        if (convertView==null || ((ViewHolder) convertView.getTag()).type!=getType(position)) {

            holder = new ViewHolder();

            if (getType(position) == TYPE_LEFT) {
                convertView = View.inflate(mContext, R.layout.message_item_left,null);
            } else if (getType(position) == TYPE_RIGHT) {
                convertView = View.inflate(mContext,R.layout.message_item_right,null);
            }

            ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            holder.ivPhoto = ivPhoto;
            TextView tvMessage = (TextView) convertView.findViewById(R.id.tvMessage);
            holder.tvMessage = tvMessage;
            TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.tvTime = tvTime;
            holder.type = getType(position);

            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();


        Sms msg = checkInfo(mNumber);
        Bitmap bitmap = loadBitmap(msg.getPhotoId());
        if (bitmap!=null) {
            holder.ivPhoto.setImageBitmap(bitmap);
        }

        holder.tvMessage.setText(sms.getBody());

        holder.tvTime.setText(DataUtils.parse(sms.getTime()));


        return convertView;
    }

    private Sms checkInfo(String number) {

        int rawContactId = 0;
        int photoId = 0;
        String name = null;

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] column = new String[] {
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.CACHED_PHOTO_ID
        };
        Cursor c = resolver.query(uri, column, CallLog.Calls.NUMBER + "=" + number, null, null);
        if (c.moveToNext()) {
//             rawContactId = c.getInt(0);
            name = c.getString(0);
            photoId = c.getInt(1);
        }

//        Uri uri2 = ContactsContract.Contacts.CONTENT_URI;
//        String[] col = new String[] {
//                ContactsContract.Contacts.PHOTO_ID
//        };
//        c = resolver.query(uri2, col, ContactsContract.Contacts.NAME_RAW_CONTACT_ID + "=" + rawContactId, null, null);
//        if (c.moveToNext()) {
//            photoId = c.getInt(0);
//        }
//
//        Uri uri3 = ContactsContract.RawContacts.CONTENT_URI;
//        String[] col2 = new String[] {
//                ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY
//        };
//        c = resolver.query(uri3, col2, ContactsContract.RawContacts._ID + "=" + rawContactId, null, null);
//        if (c.moveToNext()) {
//            name = c.getString(0);
//        }

        Sms sms = new Sms();
        sms.setPhotoId(photoId);
        sms.setName(name);

        c.close();
        return sms;
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
        TextView tvMessage;
        TextView tvTime;
        int type;
    }
}
