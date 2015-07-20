package com.fei.aabillmanager.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fei.aabillmanager.app.R;

/**
 * Created by Eric on 06/06/2014.
 */
public class AdapterAppGrid extends BaseAdapter {

    private Context mContext;

    private class Holder {
        ImageView ivIcon;
        TextView tvName;
    }

    private Integer[] mImageInteger = {
            R.drawable.grid_payout,
            R.drawable.grid_bill,
            R.drawable.grid_report,
            R.drawable.grid_account_book,
            R.drawable.grid_category,
            R.drawable.grid_user
    };

    private String[] mImageString = new String[6];

    public AdapterAppGrid(Context pContext) {
        mContext = pContext;
        mImageString[0] = pContext.getString(R.string.appGridTextPayoutAdd);
        mImageString[1] = pContext.getString(R.string.appGridTextPayoutManage);
        mImageString[2] = pContext.getString(R.string.appGridTextStatisticsManage);
        mImageString[3] = pContext.getString(R.string.appGridTextAccountBookManage);
        mImageString[4] = pContext.getString(R.string.appGridTextCategoryManage);
        mImageString[5] = pContext.getString(R.string.appGridTextUserManage);

    }

    @Override
    public int getCount() {
        return mImageString.length;
    }

    @Override
    public Object getItem(int i) {
        return mImageString[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder _Holder;

        if (view == null) {
            LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
            view = _LayoutInflater.inflate(R.layout.main_body_item,null);
            _Holder = new Holder();
            _Holder.ivIcon = (ImageView)view.findViewById(R.id.ivIcon);
            _Holder.tvName = (TextView)view.findViewById(R.id.tvName);
            view.setTag(_Holder);
        }
        else {
            _Holder = (Holder)view.getTag();
        }

        _Holder.ivIcon.setImageResource(mImageInteger[i]);
        LinearLayout.LayoutParams _LayoutParams = new LinearLayout.LayoutParams(150,150);
        _Holder.ivIcon.setLayoutParams(_LayoutParams);
        _Holder.ivIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        _Holder.tvName.setText(mImageString[i]);

        return view;
    }
}
