package com.fei.youlu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fei.youlu.R;
import com.fei.youlu.adapter.MyViewPagerAdapter;
import com.fei.youlu.fragment.FragmentCallLog;
import com.fei.youlu.fragment.FragmentContacts;
import com.fei.youlu.fragment.FragmentDial;
import com.fei.youlu.fragment.FragmentMessage;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private RadioButton rbtnRecords,rbtnContacts,rbtnMessage,rbtnDail;
    private RadioGroup mRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setAdapters();
        initListeners();
    }

    private void initListeners() {

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        rbtnRecords.setChecked(true);
                        break;
                    case 1:
                        rbtnContacts.setChecked(true);
                        break;
                    case 2:
                        rbtnMessage.setChecked(true);
                        break;
                    case 3:
                        rbtnDail.setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rbtnRecords.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewPager.setCurrentItem(0);
                }
            }
        });

        rbtnContacts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewPager.setCurrentItem(1);
                }
            }
        });

        rbtnMessage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewPager.setCurrentItem(2);
                }
            }
        });

        rbtnDail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewPager.setCurrentItem(3);
                }
            }
        });
    }

    private void setAdapters() {

        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(new FragmentCallLog());
        mFragmentList.add(new FragmentContacts());
        mFragmentList.add(new FragmentMessage());
        mFragmentList.add(new FragmentDial());
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(adapter);
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.vpContent);
        rbtnRecords = (RadioButton) findViewById(R.id.rbtnRecords);
        rbtnRecords.setChecked(true);
        rbtnContacts = (RadioButton) findViewById(R.id.rbtnContacts);
        rbtnMessage = (RadioButton) findViewById(R.id.rbtnMessage);
        rbtnDail = (RadioButton) findViewById(R.id.rbtnDail);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

    }


}
