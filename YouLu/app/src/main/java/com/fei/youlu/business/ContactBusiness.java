package com.fei.youlu.business;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.fei.youlu.entity.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fei on 07/06/2015.
 */
public class ContactBusiness {

    private Context mContext;

    public ContactBusiness(Context context) {
        mContext = context;
    }

    public List<Contact> loadContacts() {
        final ArrayList<Contact> mContactList = new ArrayList<Contact>();
        final Contact contact = new Contact();
        //should use work thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver resolver = mContext.getContentResolver();
                Uri uri = ContactsContract.Contacts.CONTENT_URI;
                String[] columns = new String[] {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.PHOTO_ID
                };
                Cursor cursor = resolver.query(uri, columns, null, null, null);
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(0);
                    contact.setId(id);
                    int photoId = cursor.getInt(1);
                    contact.setPhotoId(photoId);
                    Log.i("contacts", "id: " + id + " photo id: " + photoId);
                    //use id to query data table to get contacts information
                    Uri dataUri = ContactsContract.Data.CONTENT_URI;
                    String[] cols = new String[] {ContactsContract.Data._ID, ContactsContract.Data.MIMETYPE, ContactsContract.Data.RAW_CONTACT_ID, ContactsContract.Data.DATA1, ContactsContract.Data.DATA15};
                    Cursor c2 = resolver.query(dataUri, cols, ContactsContract.Data.RAW_CONTACT_ID+"="+id, null, null);
//            Cursor c2 = resolver.query(dataUri, null, null, null, null);
//            c2.moveToNext();
//            for (int i=0;i<c2.getColumnCount();i++) {
//                Log.i("contacts",c2.getColumnName(i));
//            }
                    while (c2.moveToNext()) {
                        String mimetype = c2.getString(1);
                        if (mimetype.equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            String phone = c2.getString(3);
                            contact.setPhone(phone);
                            Log.i("contact","phone: "+phone);
                        } else if (mimetype.equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                            String email = c2.getString(3);
                            contact.setEmail(email);
                            Log.i("contact","email: "+email);
                        } else if (mimetype.equals("vnd.android.cursor.item/postal-address_v2")) {
                            String address = c2.getString(3);
                            contact.setAddress(address);
                            Log.i("contact","address: "+address);
                        } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                            String name = c2.getString(3);
                            contact.setName(name);
                            Log.i("contact","name: "+name);
                        }
                    }
                    c2.close();

                    mContactList.add(contact);

                }
                cursor.close();

            }
        }).start();
        return mContactList;

    }

}
