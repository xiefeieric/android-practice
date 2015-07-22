package com.fei.youlu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fei.youlu.R;
import com.fei.youlu.activity.ActivityMessage;
import com.fei.youlu.adapter.ConversationListAdapter;
import com.fei.youlu.business.MessageBusiness;
import com.fei.youlu.entity.Conversation;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMessage extends Fragment {

    private ListView lvMessage;
    private List<Conversation> mConversationList;


    public FragmentMessage() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        //find listView
        lvMessage = (ListView) view.findViewById(R.id.lvMessage);
        mConversationList = new ArrayList<Conversation>();
        MessageBusiness messageBusiness = new MessageBusiness(getActivity());
        mConversationList = messageBusiness.findAllConversations();
        ConversationListAdapter adapter = new ConversationListAdapter(getActivity(),mConversationList);
        lvMessage.setAdapter(adapter);
        setListeners();
        return view;
    }

    private void setListeners() {
        lvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActivityMessage.class);
                intent.putExtra("threadId",mConversationList.get(position).getId());
                intent.putExtra("number",mConversationList.get(position).getNumber());
                startActivity(intent);
            }
        });
    }


}
