package eric.com.lingchat;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eric.com.lingchat.Utility.KeyBoardUtil;


public class MainActivity extends Activity implements HttpGetDataListener,View.OnClickListener {

    private HttpData httpData;
    private JSONObject jsonObject;
    private String result;
    private List<ListData> lists;
    private ListView lv;
    private EditText etSend;
    private Button btnButton;
    private String content_str;
    private MyAdapter adapter;
    private String[] welcome_array;
    private double currentTime,oldTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        lv= (ListView) findViewById(R.id.lv);
        etSend = (EditText) findViewById(R.id.etSend);
        btnButton = (Button) findViewById(R.id.btnSend);
        btnButton.setOnClickListener(this);
        lists = new ArrayList<>();
        adapter = new MyAdapter(lists,this);
        lv.setAdapter(adapter);
        ListData listData;
        listData = new ListData(getRandomWelcomeTips(),ListData.RECEIVER,getTime());
        lists.add(listData);
    }

    private String getRandomWelcomeTips(){
        String welcome_tip = null;
        welcome_array = this.getResources().getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random()*welcome_array.length-1);
        welcome_tip = welcome_array[index];
        return  welcome_tip;
    }


    public void parseText(String str) {

        try {
            JSONObject jb = new JSONObject(str);
            ListData listData = new ListData(jb.getString("text"),ListData.RECEIVER,getTime());
            lists.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataUrl(String data) {

        parseText(data);
        System.out.println(data);
//        try {
//            jsonObject = new JSONObject(data);
//            result = (String) jsonObject.get("text");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        System.out.println(result);
    }

    @Override
    public void onClick(View v) {

        getTime();

        content_str = etSend.getText().toString();
        etSend.setText("");
        String dropSpace = content_str.replace(" ","") ;
        String dropEnter = dropSpace.replace("\n","");
        ListData listData;
        listData = new ListData(content_str,ListData.SEND,getTime());
        lists.add(listData);

        if (lists.size()>30) {
            for (int i = 0; i<lists.size();i++) {
                lists.remove(i);
            }
        }

        adapter.notifyDataSetChanged();
        httpData = (HttpData) new HttpData("http://www.tuling123.com/openapi/api?key=c6c4b404f5a38ea7ed3d3ae865460ca9&info="+dropEnter,this).execute();
        System.out.println(content_str);

        KeyBoardUtil.closeKeybord(etSend,this);


    }

    private String getTime() {
        currentTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        if (currentTime - oldTime >=60000) {
            oldTime = currentTime;
            return str;
        } else {
            return "";
        }

    }
}
