package com.fei.youlu.business;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fei on 10/06/2015.
 */
public class CallLogBusiness {

    private Context mContext;
    private List<com.fei.youlu.entity.CallLog> mList;

    public CallLogBusiness(Context context) {
        mContext = context;
    }

    public List<com.fei.youlu.entity.CallLog> loadCallLogs() {

        mList = new ArrayList<com.fei.youlu.entity.CallLog>();

        new Thread(new Runnable() {
            @Override
            public void run() {

                ContentResolver resolver = mContext.getContentResolver();
                Uri uri = CallLog.Calls.CONTENT_URI;
                String[] columns = new String[] {
                        CallLog.Calls._ID,      //0
                        CallLog.Calls.NUMBER,   //1
                        "name",                 //2
                        CallLog.Calls.DATE,     //3
                        "photo_id",              //4
                        CallLog.Calls.TYPE      //5
                };
                Cursor cursor = resolver.query(uri, columns, null, null, CallLog.Calls.DATE + " desc");

                while (cursor.moveToNext()) {
                    com.fei.youlu.entity.CallLog callLog = new com.fei.youlu.entity.CallLog();
                    callLog.setId(cursor.getInt(0));
                    callLog.setNumber(cursor.getString(1));
                    callLog.setName(cursor.getString(2));
                    callLog.setDate(cursor.getLong(3));
                    callLog.setPhotoId(cursor.getInt(4));
                    callLog.setType(cursor.getInt(5));
                    Log.i("test",callLog.toString());
                    mList.add(callLog);
                }
                cursor.close();

            }
        }).start();

        return mList;
    }

    public List<com.fei.youlu.entity.CallLog> loadCallLogs(final String number) {

        mList = new ArrayList<com.fei.youlu.entity.CallLog>();

        new Thread(new Runnable() {
            @Override
            public void run() {

                ContentResolver resolver = mContext.getContentResolver();
                Uri uri = CallLog.Calls.CONTENT_URI;
                String[] columns = new String[] {
                        CallLog.Calls._ID,      //0
                        CallLog.Calls.NUMBER,   //1
                        "name",                 //2
                        CallLog.Calls.DATE,     //3
                        "photo_id",              //4
                        CallLog.Calls.TYPE      //5
                };
                Cursor cursor = resolver.query(uri, columns, CallLog.Calls.NUMBER + " LIKE "+"'"+number+"%'", null, CallLog.Calls.DATE + " desc");

                while (cursor.moveToNext()) {
                    com.fei.youlu.entity.CallLog callLog = new com.fei.youlu.entity.CallLog();
                    callLog.setId(cursor.getInt(0));
                    callLog.setNumber(cursor.getString(1));
                    callLog.setName(cursor.getString(2));
                    callLog.setDate(cursor.getLong(3));
                    callLog.setPhotoId(cursor.getInt(4));
                    callLog.setType(cursor.getInt(5));
                    Log.i("test",callLog.toString());
                    mList.add(callLog);
                }
                cursor.close();

            }
        }).start();

        return mList;
    }

}
