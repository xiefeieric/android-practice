package com.fei.musicclient.business;

import android.os.AsyncTask;
import android.util.Log;

import com.fei.musicclient.activity.MainActivity;
import com.fei.musicclient.entity.Music;
import com.fei.musicclient.util.GlobalConstants;
import com.fei.musicclient.util.HttpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fei on 02/06/2015.
 */
public class MusicBusiness extends AsyncTask<String,String,List<Music>> {

    private MainActivity mContext;

    public MusicBusiness(MainActivity context) {
        mContext = context;
    }

    @Override
    protected List<Music> doInBackground(String... params) {

        String uri = GlobalConstants.GLOBAL_PATH+"loadMusics.jsp";
        try {
            HttpEntity entity = HttpUtils.httpRequest(HttpUtils.HTTP_GET_REQUEST, uri, null);
            String json = EntityUtils.toString(entity);
            Log.i("JSON","json: "+json);
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.getString("result").equals("ok")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                List<Music> musics = parseJSON(jsonArray);
                return musics;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Music> parseJSON(JSONArray jsonArray) throws JSONException {

        List<Music> musics = new ArrayList<Music>();
        for (int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Music music = new Music();
            music.setId(jsonObject.getInt("id"));
            music.setAlbum(jsonObject.getString("album"));
            music.setAlbumpic(jsonObject.getString("albumpic"));
            music.setAuthor(jsonObject.getString("author"));
            music.setComposer(jsonObject.getString("composer"));
            music.setDowncount(jsonObject.getString("downcount"));
            music.setDurationtime(jsonObject.getString("durationtime"));
            music.setFavcount(jsonObject.getString("favcount"));
            music.setMusicpath(jsonObject.getString("musicpath"));
            music.setName(jsonObject.getString("name"));
            music.setSinger(jsonObject.getString("singer"));
            musics.add(music);
        }

        return musics;
    }

    @Override
    protected void onPostExecute(List<Music> musics) {
        super.onPostExecute(musics);
        mContext.updateListView(musics);
    }
}
