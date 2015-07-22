package com.fei.mymyy.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fei.mymyy.R;
import com.fei.mymyy.adapter.ModuleAdapter;
import com.fei.mymyy.business.PageModuleBusiness;
import com.fei.mymyy.entity.PageModule;

import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends Activity {

    private GridView mGridView;
    private List<PageModule> mList;
    private ModuleAdapter mModuleAdapter;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        initViews();
        isFirst=getIntent().getBooleanExtra("isFirst", true);
        mList = new ArrayList<PageModule>();
        PageModuleBusiness business = new PageModuleBusiness(this);
        mList = business.loadAllModules();
        initAdapters();
    }

    private void initAdapters() {
        mModuleAdapter = new ModuleAdapter(this,mList);
//        Log.i("test",mList.toString());
        mGridView.setAdapter(mModuleAdapter);
    }

    private void initViews() {
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PageModule pageModule = mList.get(position);
                Intent intent = new Intent(ChooseActivity.this,EditActivity.class);
                intent.putExtra("pageModule",pageModule);
                setResult(RESULT_OK,intent);
                if (isFirst==true){
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        });
    }

    public void updateGridView(List<PageModule> list){
        mList=list;
        //setAdapter
        ModuleAdapter adapter=new ModuleAdapter(this, list);
        mGridView.setAdapter(adapter);
    }

}
