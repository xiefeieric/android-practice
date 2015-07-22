package com.fei.youlu.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fei.youlu.R;
import com.fei.youlu.entity.Contact;

import java.util.List;

/**
 * Created by Fei on 07/06/2015.
 */
public class MyGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Contact> mContactList;

    public MyGridViewAdapter(Context context, List<Contact> contactList) {
        mContext = context;
        mContactList = contactList;
        Contact addContact = new Contact();
        mContactList.add(0,addContact);
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactList.get(position);
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
            convertView = View.inflate(mContext, R.layout.grid_view_item,null);
            ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            holder.ivPhoto = ivPhoto;
            TextView tvContact = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvContact = tvContact;
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        if (position==0) {
            holder.ivPhoto.setImageResource(R.drawable.img06_11);
            holder.tvContact.setText("Add Contact");
            //why return?
            return convertView;
        }

        Contact contact = mContactList.get(position);

        Bitmap bitmap = loadBitmap(contact.getPhotoId());
        if (bitmap!=null) {
            holder.ivPhoto.setImageBitmap(bitmap);
        } else {
            holder.ivPhoto.setImageResource(R.drawable.img01g_15);
        }

        holder.tvContact.setText(contact.getName());

        return convertView;
    }

    private Bitmap loadBitmap(int photoId) {
        Bitmap bitmap =null;
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] columns = {ContactsContract.Data.DATA15};
        Cursor cursor = resolver.query(uri, columns, ContactsContract.Data._ID + "=" + photoId, null, null);
        if (cursor.moveToNext()) {
            byte[] bytes = cursor.getBlob(0);
            bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        }
        cursor.close();
        return bitmap;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvContact;
    }
}
