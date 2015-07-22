package com.fei.youlu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fei.youlu.R;

import java.util.ArrayList;

/**
 * Created by Fei on 11/06/2015.
 */
public class KeyboardAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<String> mArrayList;

    public KeyboardAdapter(Context context) {

        mContext = context;
        mArrayList = new ArrayList<String>();
        mArrayList.add("1");
        mArrayList.add("2");
        mArrayList.add("3");
        mArrayList.add("4");
        mArrayList.add("5");
        mArrayList.add("6");
        mArrayList.add("7");
        mArrayList.add("8");
        mArrayList.add("9");
        mArrayList.add("8");
        mArrayList.add("0");
        mArrayList.add("#");
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(mContext, R.layout.keyboard_item, null);
        TextView tvKeyboardNumber = (TextView) convertView.findViewById(R.id.tvKeyboardNumber);
        tvKeyboardNumber.setText(mArrayList.get(position));
        return convertView;
    }
}
