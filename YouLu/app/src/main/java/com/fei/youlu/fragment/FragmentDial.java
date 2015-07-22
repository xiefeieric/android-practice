package com.fei.youlu.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fei.youlu.R;
import com.fei.youlu.adapter.CallLogListAdapter;
import com.fei.youlu.adapter.KeyboardAdapter;
import com.fei.youlu.business.CallLogBusiness;
import com.fei.youlu.entity.CallLog;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDial extends Fragment {

    private GridView gvKeyboard;
    private ListView lvCalls;
    private LinearLayout mLinearLayout;
    private TextView tvNumber;
    private ArrayList<String> mArrayList;
    private StringBuffer mStringBuffer = new StringBuffer();
    private ImageView ivDelete;
    private ImageView ivDial;
    private CallLogBusiness callLogBusiness;
    private List<CallLog> callLogList;
    private CallLogListAdapter adapter;


    public FragmentDial() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dial, container, false);
        //find listView
        lvCalls = (ListView) view.findViewById(R.id.lvCalls);
        lvCalls.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mLinearLayout.getVisibility()==View.VISIBLE) {


                    mLinearLayout.setVisibility(View.GONE);
                    Animation animation = new TranslateAnimation(0, 0, 0, mLinearLayout.getHeight());
                    animation.setDuration(600);
//                    animation.setFillAfter(true);
                    mLinearLayout.setAnimation(animation);
                }
                return false;
            }
        });


        mLinearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        mLinearLayout.setVisibility(View.GONE);

        tvNumber = (TextView) view.findViewById(R.id.tvNumber);
        tvNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mLinearLayout.getVisibility() == View.GONE) {
                    mLinearLayout.setVisibility(View.VISIBLE);
                    Animation animation = new TranslateAnimation(0, 0, mLinearLayout.getHeight(), 0);
                    animation.setDuration(600);
//                    animation.setFillAfter(true);
                    mLinearLayout.setAnimation(animation);
                } else if (mLinearLayout.getVisibility() == View.VISIBLE) {
                    mLinearLayout.setVisibility(View.GONE);
                    Animation animation = new TranslateAnimation(0, 0, 0, mLinearLayout.getHeight());
                    animation.setDuration(600);
//                    animation.setFillAfter(true);
                    mLinearLayout.setAnimation(animation);
                }

                return false;
            }
        });

        ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStringBuffer.length() > 0) {
//                    Log.i("test","string length: "+mStringBuffer.length());
//                    Log.i("test","charAt: "+mStringBuffer.charAt(mStringBuffer.length()-1));

                    mStringBuffer.deleteCharAt(mStringBuffer.length() - 1);
//                    Log.i("test","string length: "+mStringBuffer.length());
                    tvNumber.setText(mStringBuffer);
                }
            }
        });


        //set data source
        callLogBusiness = new CallLogBusiness(getActivity());
        callLogList = callLogBusiness.loadCallLogs();
        //set adapter
        if (callLogList!=null){
            adapter = new CallLogListAdapter(getActivity(),callLogList);
            lvCalls.setAdapter(adapter);
        }

        gvKeyboard = (GridView) view.findViewById(R.id.gvKeyboard);
        KeyboardAdapter keyboardAdapter = new KeyboardAdapter(getActivity());
        gvKeyboard.setAdapter(keyboardAdapter);
        gvKeyboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(),"clicked: "+mArrayList.get(position),Toast.LENGTH_SHORT).show();
                String clicked = mArrayList.get(position);
                mStringBuffer.append(clicked);
                if (mStringBuffer.length() > 3 && Character.isDigit(mStringBuffer.charAt(3))) {
                    mStringBuffer.insert(3, '-');
                }
                if (mStringBuffer.length() > 8 && Character.isDigit(mStringBuffer.charAt(8))) {
                    mStringBuffer.insert(8, '-');
                }
                tvNumber.setText(mStringBuffer);
                callLogList = callLogBusiness.loadCallLogs(mStringBuffer.toString());
//                adapter.notifyDataSetChanged();
                if (callLogList != null) {
//                    mLinearLayout.setVisibility(View.INVISIBLE);
                    adapter = new CallLogListAdapter(getActivity(), callLogList);
//                    adapter.notifyDataSetChanged();
                    lvCalls.setAdapter(adapter);
//                    mLinearLayout.setVisibility(View.VISIBLE);
                }


            }
        });

        ivDial = (ImageView) view.findViewById(R.id.ivDial);
        ivDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + tvNumber.getText().toString()));
                startActivity(intent);
                mStringBuffer.replace(0, mStringBuffer.length(), "138-8888");
                tvNumber.setText(mStringBuffer);
            }
        });

        return view;
    }

    private List<CallLog> queryList(String number) {

        for (int i=0;i<callLogList.size();i++) {

        }

        return null;
    }

}
