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
import com.fei.aabillmanager.app.adapter.base.AdapterBase;
import com.fei.aabillmanager.app.controls.SliderMenuItem;

import java.util.List;

/**
 * Created by Eric on 06/06/2014.
 */
public class AdapterSlideMenu extends AdapterBase {

    private Context mContext;

    private class Holder {

        TextView tvMenuName;
    }

    public AdapterSlideMenu(Context pContext, List pList) {
        super(pContext,pList);
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder _Holder;

        if (view == null) {
            view = GetLayoutInflater().inflate(R.layout.slidemenu_list_item,null);
            _Holder = new Holder();
            _Holder.tvMenuName = (TextView)view.findViewById(R.id.tvMenuName);
            view.setTag(_Holder);
        }
        else {
            _Holder = (Holder)view.getTag();
        }

        SliderMenuItem _Item = (SliderMenuItem)GetList().get(i);

        _Holder.tvMenuName.setText(_Item.getTitle());

        return view;
    }
}
