package http_requests;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.miaodonghan.markupproject.LoginActivity;
import com.example.miaodonghan.markupproject.R;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class User_PutRequestTask extends AsyncTask<String, Integer, String> {

    Context context;
    int user_id;
    int doc_id;
    int version_id;
    String ip;
    SharedPreferences sharedPreferences;
    int position;
    List<Map<String, Object>> data_version = new ArrayList<Map<String, Object>>();


    public User_PutRequestTask(Context context, int version_id, String ip,
                               int doc_id,List<Map<String, Object>> data_version, int position) {

        this.context = context;
        this.version_id = version_id;
        this.ip = ip;
        this.doc_id = doc_id;
        this.data_version =data_version;
        this.position =position;
        sharedPreferences = context.getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
        user_id = sharedPreferences.getInt(LoginActivity.userid_s,-1);
    }

    @Override
    protected String doInBackground(String... data) {

        HttpURLConnection urlConnection = null;
        try {

            //URL url = new URL(ip + "/api/"+user_id+"docs/" + doc_id + "/version/" + version_id);
            URL url = new URL(ip);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");

            JSONObject jsonParam = new JSONObject();

            jsonParam.put("share", data[1]);

            String requestData = jsonParam.toString();
            urlConnection.setRequestProperty("Content-Length", "" +  requestData.getBytes().length);
            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

            out.writeBytes(requestData);
            out.flush();
            out.close();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            Scanner s = new Scanner(in).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";
            return res;
        } catch (Exception ex) {
            Log.e("PutRequestTask:", ex.toString());
        } finally {
            urlConnection.disconnect();
        }

        return "";
    }


    @Override
    protected void onPostExecute(String result) {
        data_version.get(position).put("img", R.mipmap.shared);
        Toast.makeText(context, "Share successfully", Toast.LENGTH_SHORT).show();
    }

}
