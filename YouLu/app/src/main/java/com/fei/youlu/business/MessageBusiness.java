package com.fei.youlu.business;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.fei.youlu.entity.Conversation;
import com.fei.youlu.entity.Sms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fei on 07/06/2015.
 */
public class MessageBusiness {

    private Context mContext;
    private ArrayList<Conversation> mArrayList;

    public MessageBusiness(Context context) {
        mContext = context;
    }

    public List<Conversation> findAllConversations() {
        mArrayList = new ArrayList<Conversation>();
        //should use work thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver resolver = mContext.getContentResolver();
                Uri uri = Uri.parse("content://mms-sms/conversations");
                String[] columns = new String[] {
                        "thread_id",
                        "body",
                        "date",
                        "address"
                };
                Cursor cursor = resolver.query(uri, columns, null, null, null);
                while (cursor.moveToNext()) {
                    Conversation conversation = new Conversation();
                    int threadId = cursor.getInt(0);
                    conversation.setId(threadId);
                    String body = cursor.getString(1);
                    conversation.setBody(body);
                    long time = cursor.getLong(2);
                    conversation.setTime(time);
                    String number = cursor.getString(3);
                    conversation.setNumber(number);
                    Log.i("test",conversation.toString());
                    mArrayList.add(conversation);
                }
                cursor.close();

            }
        }).start();
        return mArrayList;

    }

    public List<Sms> findMessageByThread(final int threadId) {
        final List<Sms> smsList = new ArrayList<Sms>();
        //should use work thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver resolver = mContext.getContentResolver();
                Uri uri = Uri.parse("content://sms/");
                String[] columns = new String[] {
                        "_id",
                        "body",
                        "date",
                        "type"
                };
                Cursor cursor = resolver.query(uri, columns, "thread_id="+threadId, null, null);
                while (cursor.moveToNext()) {
                    Sms sms = new Sms();
                    int id = cursor.getInt(0);
                    sms.setId(threadId);
                    String body = cursor.getString(1);
                    sms.setBody(body);
                    long time = cursor.getLong(2);
                    sms.setTime(time);
                    int type = cursor.getInt(3);
                    sms.setType(type);
                    Log.i("test", sms.toString());
                    smsList.add(sms);
                }
                cursor.close();

            }
        }).start();
        return smsList;

    }

}
