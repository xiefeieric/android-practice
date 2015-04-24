package com.fei.aabillmanager.app.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Eric on 16/06/2014.
 */
public abstract class AdapterBase extends BaseAdapter {

    private List mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;



    public AdapterBase(Context pContext, List pList) {
        mContext = pContext;
        mList = pList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public LayoutInflater GetLayoutInflater () {
        return mLayoutInflater;
    }

    public Context GetContext() {
        return mContext;
    }

    public List GetList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int pPosition) {
        return mList.get(pPosition);
    }

    @Override
    public long getItemId(int pPosition) {
        return pPosition;
    }

}


