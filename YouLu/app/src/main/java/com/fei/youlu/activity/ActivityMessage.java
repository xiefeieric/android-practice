package com.fei.youlu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.fei.youlu.R;
import com.fei.youlu.adapter.MessageAdapter;
import com.fei.youlu.business.MessageBusiness;
import com.fei.youlu.entity.Sms;

import java.util.ArrayList;
import java.util.List;

public class ActivityMessage extends Activity {

    private ListView lvMessage;
    private List<Sms> mSmsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initViews();
        mSmsListView = new ArrayList<Sms>();
        MessageBusiness messageBusiness = new MessageBusiness(this);
        int threadId = getIntent().getIntExtra("threadId",0);
        String number = getIntent().getStringExtra("number");
        mSmsListView = messageBusiness.findMessageByThread(threadId);
        MessageAdapter adapter = new MessageAdapter(this,mSmsListView,number);
        lvMessage.setAdapter(adapter);

    }

    private void initViews() {
        lvMessage = (ListView) findViewById(R.id.lvMessage);
    }

}
