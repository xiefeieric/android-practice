package com.fei.youlu.fragment;


import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.fei.youlu.R;
import com.fei.youlu.adapter.MyGridViewAdapter;
import com.fei.youlu.business.ContactBusiness;
import com.fei.youlu.entity.Contact;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentContacts extends Fragment {

    private GridView mGridView;
    private RelativeLayout rlayContactDetail;



    public FragmentContacts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contcts, container, false);
        mGridView = (GridView) view.findViewById(R.id.gvContacts);

        ContactBusiness contactBusiness = new ContactBusiness(getActivity());
        List<Contact> list = contactBusiness.loadContacts();
        MyGridViewAdapter adapter = new MyGridViewAdapter(getActivity(),list);
        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.getWindow().setContentView(R.layout.contact_details);
                rlayContactDetail = (RelativeLayout) alertDialog.getWindow().findViewById(R.id.rlayContactDetail);
                rlayContactDetail.setBackgroundColor(Color.TRANSPARENT);


            }
        });

        return view;
    }


}
