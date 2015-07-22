package com.fei.youlu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fei.youlu.R;
import com.fei.youlu.adapter.CallLogListAdapter;
import com.fei.youlu.business.CallLogBusiness;
import com.fei.youlu.entity.CallLog;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCallLog extends Fragment {


    public FragmentCallLog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);
        //find listview
        ListView lvCalls = (ListView) view.findViewById(R.id.lvCalls);
        //set data source
        CallLogBusiness callLogBusiness = new CallLogBusiness(getActivity());
        List<CallLog> callLogList = callLogBusiness.loadCallLogs();
        //set adapter
        if (callLogList!=null){
            CallLogListAdapter adapter = new CallLogListAdapter(getActivity(),callLogList);
            lvCalls.setAdapter(adapter);
        }

        return view;
    }


}
