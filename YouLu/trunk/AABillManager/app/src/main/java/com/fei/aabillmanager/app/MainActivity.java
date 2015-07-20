package com.fei.aabillmanager.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.fei.aabillmanager.app.adapter.AdapterAppGrid;
import com.fei.aabillmanager.app.com.fei.aabillmanager.app.base.ActivityBase;
import com.fei.aabillmanager.app.com.fei.aabillmanager.app.base.ActivityFrame;


public class MainActivity extends ActivityFrame {

    private GridView gvAppGrid;
    private AdapterAppGrid mAdapterAppGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.main_body);
        InitVariable();
        InitView();
        InitListeners();
        BindData();
        createSlideMenu(R.array.SlideMenuActivityMain);

    }

    public void InitVariable() {
        mAdapterAppGrid = new AdapterAppGrid(this);
    }
    public void InitView() {
        gvAppGrid = (GridView)findViewById(R.id.gvAppGrid);
    }
    public void InitListeners() {

    }
    public void BindData() {
        gvAppGrid.setAdapter(mAdapterAppGrid);
    }


}
