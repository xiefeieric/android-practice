package com.fei.mymyy.activity;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.fei.mymyy.R;
import com.fei.mymyy.entity.PageModule;
import com.fei.mymyy.fragment.ModuleFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private List<PageModule> mList = new ArrayList<>();

    public static final int RETURN_PAGE_MODULE = 1;
    public static final int SELECT_PHOTO_FROM_SYSTEM=2;
    public static final int CROP_A_PICTURE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initViews();
        PageModule pageModule = (PageModule) getIntent().getSerializableExtra("pageModule");
        if (pageModule!=null) {
            mList.add(pageModule);
            if (mPagerAdapter!=null) {
                mPagerAdapter.notifyDataSetChanged();
            } else {
                setAdapter();
            }
        }

    }

    public void doClick(View view) {
        switch (view.getId()) {

            case R.id.imageView4:
//                Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditActivity.this,ChooseActivity.class);
                intent.putExtra("isFirst",false);
                startActivityForResult(intent, RETURN_PAGE_MODULE);

            case R.id.imageView6:
//                Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(Intent.ACTION_GET_CONTENT);
                i2.setType("image/*");
                startActivityForResult(i2, SELECT_PHOTO_FROM_SYSTEM);

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RETURN_PAGE_MODULE && resultCode==RESULT_OK) {
            PageModule pageModule = (PageModule) data.getSerializableExtra("pageModule");
            mList.add(pageModule);
            mPagerAdapter.notifyDataSetChanged();
        }

        if (requestCode==SELECT_PHOTO_FROM_SYSTEM && resultCode==RESULT_OK) {
            if (data!=null) {
                Uri uri = data.getData();
                Log.i("test","uri: "+uri);
                String[] project = {MediaStore.Images.Media.DATA};
                Log.i("test",project[0]);
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(uri, null, null, null, null);
//                while (cursor.moveToNext()) {
//                    Log.i("test","data: "+cursor.getString(2));
//                }
                cursor.moveToFirst();
                String path = cursor.getString(1);
                Log.i("test","path: "+path);
//                if (path!=null) {
                    cropImage(path);
//                }
                cursor.close();
            }

        }

        if (requestCode==CROP_A_PICTURE && resultCode==RESULT_OK) {

        }
    }

    private void cropImage(String path) {
        File file = new File(path);
        Intent intent = new Intent("com.android.camera.action.CROP");
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 600);

        intent.putExtra("return-data", false);
        intent.putExtra("output", path);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_A_PICTURE);
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

    }
    private void setAdapter() {
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ModuleFragment(mList.get(position));
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
