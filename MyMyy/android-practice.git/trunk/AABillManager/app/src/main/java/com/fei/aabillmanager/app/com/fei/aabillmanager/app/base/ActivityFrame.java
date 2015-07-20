package com.fei.aabillmanager.app.com.fei.aabillmanager.app.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fei.aabillmanager.app.R;
import com.fei.aabillmanager.app.controls.SlideMenuView;
import com.fei.aabillmanager.app.controls.SliderMenuItem;

/**
 * Created by Eric on 06/06/2014.
 */
public class ActivityFrame extends ActivityBase {

    private SlideMenuView mSlideMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
    }

    protected void AppendMainBody(int pResId) {
        LinearLayout _MainBody = (LinearLayout)findViewById(R.id.layMainBody);

        View _View = LayoutInflater.from(this).inflate(pResId,null);
        RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        _MainBody.addView(_View,_LayoutParams);
    }

    protected void createSlideMenu(int pResID) {
        mSlideMenuView = new SlideMenuView(this);
        String [] _MenuItemArray = getResources().getStringArray(pResID);

        for (int i=0; i<_MenuItemArray.length; i++) {
            SliderMenuItem _Item = new SliderMenuItem(i,_MenuItemArray[i]);
            mSlideMenuView.Add(_Item);
        }

        mSlideMenuView.BindList();
    }

}
