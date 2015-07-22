package com.fei.mymyy.business;

import android.content.Context;
import android.util.Log;

import com.fei.mymyy.activity.ChooseActivity;
import com.fei.mymyy.entity.PageModule;
import com.fei.mymyy.utils.GlobalConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fei on 25/06/15.
 */
public class PageModuleBusiness {

    private Context mContext;
    private List<PageModule> list;

    public PageModuleBusiness(Context context) {
        mContext = context;
    }

    public List<PageModule> loadAllModules() {
        list = new ArrayList<PageModule>();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = GlobalConstants.BASE_URL+"loadModules.jsp";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String respText = null;
                try {
                    respText = new String(bytes,"utf-8");
//                    Log.i("test",respText);
                    list = parseModules(respText);
//                    Log.i("test",list.toString());
                    ChooseActivity c=(ChooseActivity)mContext;
                    c.updateGridView(list);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Log.i("test","Download Failed");

            }

        });
        return list;
    }

    private List<PageModule> parseModules(String json) throws JSONException {
        List<PageModule> modules=new ArrayList<PageModule>();
        JSONArray ary=new JSONArray(json);
        for(int i=0; i<ary.length(); i++){
            JSONObject obj=ary.getJSONObject(i);
            PageModule m = new PageModule();
            m.setId(obj.getInt("id"));
            m.setName(obj.getString("name"));
            m.setSnapShot(obj.getString("snapshot"));
            m.setContent(obj.getString("content"));
//            Log.i("test",m.toString());
            modules.add(m);
        }
        return modules;
    }
}
