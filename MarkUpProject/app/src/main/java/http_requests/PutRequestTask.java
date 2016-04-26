package http_requests;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.miaodonghan.markupproject.LoginActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class PutRequestTask extends AsyncTask<String, Integer, String> {

    Context context;
    int user_id;
    int doc_id;
    int version_id;
    String ip;
    String token;
    SharedPreferences sharedPreferences;


    public PutRequestTask(Context context, int version_id, String ip, int doc_id) {

        this.context = context;
        this.version_id = version_id;
        this.ip = ip;
        this.doc_id = doc_id;
        sharedPreferences = context.getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
        user_id = sharedPreferences.getInt(LoginActivity.userid_s,-1);
        token = sharedPreferences.getString(LoginActivity.Token_s,null);
    }

    @Override
    protected String doInBackground(String... data) {

        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(ip + "/api/"+user_id+"/docs/" + doc_id + "/version/" + version_id);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();

            jsonParam.put("name", data[1]);
            jsonParam.put("content", data[2]);

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
        Toast.makeText(context, "save successfully", Toast.LENGTH_LONG).show();

    }

}
