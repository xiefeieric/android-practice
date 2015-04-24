package com.fei.aabillmanager.app.controls;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fei.aabillmanager.app.R;
import com.fei.aabillmanager.app.adapter.AdapterAppGrid;
import com.fei.aabillmanager.app.adapter.AdapterSlideMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 06/06/2014.
 */
public class SlideMenuView {

    private Activity mActivity;
    private List mMenuList;
    private boolean mIsClosed;
    private RelativeLayout layBottomBox;

    public SlideMenuView(Activity pActivity) {
        mActivity = pActivity;
        InitVariable();
        InitView();
        InitListeners();
    }

    public void InitVariable() {
       mMenuList = new ArrayList();
        mIsClosed = true;
    }
    public void InitView() {
        layBottomBox = (RelativeLayout) mActivity.findViewById(R.id.IncludeBottom);
    }
    public void InitListeners() {
        layBottomBox.setOnClickListener(new OnSlideMenuClick());
    }
    private void Open() {
        RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        _LayoutParams.addRule(RelativeLayout.BELOW,R.id.IncludeTitle);
        layBottomBox.setLayoutParams(_LayoutParams);
        mIsClosed = false;
    }

    private void Close() {
        RelativeLayout.LayoutParams _LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 68);
        _LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layBottomBox.setLayoutParams(_LayoutParams);
        mIsClosed = true;
    }

    private void Toggle() {
        if (mIsClosed) {
            Open();
        } else {
            Close();
        }

    }

    public void Add(SliderMenuItem pSlideMenuItem) {
        mMenuList.add(pSlideMenuItem);
    }

    public void BindList() {
        AdapterSlideMenu _AdapterSlideMenu = new AdapterSlideMenu(mActivity,mMenuList);
        ListView _ListView = (ListView) mActivity.findViewById(R.id.lvSlideMenuList);
        _ListView.setAdapter(_AdapterSlideMenu);
        _ListView.setOnItemClickListener(new OnSlideMenuItemClick());
    }



    private class OnSlideMenuClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Toggle();
        }
    }

    private class OnSlideMenuItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        }
    }

}
