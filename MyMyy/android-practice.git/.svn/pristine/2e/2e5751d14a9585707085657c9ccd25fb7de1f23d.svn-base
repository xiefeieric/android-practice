package eric.com.lingchat;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Eric on 26/03/2015.
 */
public class HttpData extends AsyncTask<String,Void,String> {

    private HttpClient mHttpClint;
    private HttpGet mHttpGet;
    private HttpResponse mHttpResponse;
    private HttpEntity mHttpEntity;
    private InputStream inputStream;
    private HttpGetDataListener listener;
    private String url;

    public HttpData(String url,HttpGetDataListener listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {


        try {
            mHttpClint = new DefaultHttpClient();
            mHttpGet = new HttpGet(url);
            mHttpResponse = mHttpClint.execute(mHttpGet);
            mHttpEntity = mHttpResponse.getEntity();
            inputStream = mHttpEntity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine())!=null) {


                sb.append(line);

            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.getDataUrl(s);


    }
}
