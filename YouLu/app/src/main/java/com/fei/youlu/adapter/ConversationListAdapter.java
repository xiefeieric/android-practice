package com.fei.youlu.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fei.youlu.R;
import com.fei.youlu.entity.Conversation;
import com.fei.youlu.entity.Sms;
import com.fei.youlu.utils.DataUtils;

import java.util.List;

/**
 * Created by Fei on 12/06/2015.
 */
public class ConversationListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Conversation> mConversationList;

    public ConversationListAdapter(Context context, List<Conversation> conversationList) {
        mContext = context;
        mConversationList = conversationList;
    }

    @Override
    public int getCount() {
        return mConversationList.size();
    }

    @Override
    public Object getItem(int position) {
        return mConversationList.get(position);
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
            convertView = View.inflate(mContext, R.layout.conversation_list_item,null);
            ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            holder.ivPhoto = ivPhoto;
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvName = tvName;
            TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            holder.tvBody = tvBody;
            TextView tvTime = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvTime = tvTime;
        }

        Conversation conversation = mConversationList.get(position);

        Sms msg = checkInfo(conversation.getNumber());
        Bitmap bitmap = loadBitmap(msg.getPhotoId());
        Log.i("test","photo_id: "+conversation.getPhotoId());
        if (bitmap!=null) {
            holder.ivPhoto.setImageBitmap(bitmap);
        }
        holder.tvName.setText(msg.getName());
        holder.tvBody.setText(conversation.getBody());
        holder.tvTime.setText(DataUtils.parse(conversation.getTime()));

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

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvName;
        TextView tvBody;
        TextView tvTime;
    }


}
