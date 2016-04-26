package http_requests;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.miaodonghan.markupproject.LoginActivity;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by miaodonghan on 4/26/16.
 */
public class User_VersionDeleteRequestTask extends AsyncTask<String, Integer, String> {
    int version_id;
    Context context;
    SharedPreferences sharedPreferences;
    int user_id;
    String token;

    public User_VersionDeleteRequestTask(Context context, int version_id) {

        this.context = context;
        this.version_id = version_id;
        sharedPreferences = context.getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);
    }
    @Override
    protected void onPreExecute() {
        // start a spinning sign
        user_id = sharedPreferences.getInt(LoginActivity.userid_s,-1);
        token = sharedPreferences.getString(LoginActivity.Token_s,null);

    }

    @Override
    protected String doInBackground(String... uri) {
        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(uri[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            //header
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setDoOutput(true);
            Log.e("deletErrorCode", urlConnection.getResponseCode()+"");

        } catch (Exception ex) {
            Log.e("backgroud task", ex.getMessage());
        }

        return "";
    }

    //@Override
    protected void onPostExecute(String result) {

        Toast.makeText(context, "Remove this version successfully", Toast.LENGTH_SHORT).show();

    }


}
