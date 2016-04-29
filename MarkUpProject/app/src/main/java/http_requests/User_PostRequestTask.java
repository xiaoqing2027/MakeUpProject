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


public class User_PostRequestTask extends AsyncTask<String, Integer, String> {
    Context context;
    int doc_id;
    int user_id;
    String ip;
    String token;
    SharedPreferences sharedPreferences;
    int error_code;


    public User_PostRequestTask(Context context, String ip, int doc_id) {

        this.context = context;
        this.ip = ip;
        this.doc_id = doc_id;
        sharedPreferences = context.getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
        user_id = sharedPreferences.getInt(LoginActivity.userid_s,-1);
        Log.e("post_userID", user_id+"");
        token = sharedPreferences.getString(LoginActivity.Token_s,null);
    }

    @Override
    protected String doInBackground(String... data) {

        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(ip + "/api/"+user_id+"/docs/" + doc_id + "/version");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //header
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setDoOutput(true);

            JSONObject jsonParam = new JSONObject();
            //body
            jsonParam.put("name", data[0]);
            jsonParam.put("content", data[1]);

            String requestData = jsonParam.toString();
            urlConnection.setRequestProperty("Content-Length", "" +  requestData.getBytes().length);
            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

            out.writeBytes(requestData);
            out.flush();
            out.close();
            error_code =urlConnection.getResponseCode();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";
            return res;
        } catch (Exception ex) {
            Log.e("User_PostRequestTask:er55r", ex.toString());
        } finally {
            urlConnection.disconnect();
        }

        return "";
    }


    @Override
    protected void onPostExecute(String result) {
        if(error_code == 200){
            Toast.makeText(context, "You create a new version successfully.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Bad.", Toast.LENGTH_SHORT).show();
        }

    }

}
