package http_requests;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.miaodonghan.markupproject.HomePageHandler;
import com.example.miaodonghan.markupproject.LoginActivity;

import java.net.HttpURLConnection;
import java.net.URL;


public class LogoutRequestTask extends AsyncTask<String, Integer, String> {
    Context context;
    String res;
    String ip;

    SharedPreferences sharedPreferences;

    public LogoutRequestTask(Context context, String ip) {

        this.context = context;
        this.ip = ip;
        this.sharedPreferences =context.getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... data) {
        String result= "";
        HttpURLConnection urlConnection = null;
        try {

            URL url = new URL(ip + "/api/auth/logout");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //String requestData ="";

//            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
//
//            out.writeBytes(requestData);
//            out.flush();
//            out.close();
//
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            Scanner s = new Scanner(in).useDelimiter("\\A");
//            res = s.hasNext() ? s.next() : "";
//            return res;


        } catch (Exception ex) {
            Log.e("er55r", ex.toString());
        } finally {
            urlConnection.disconnect();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(LoginActivity.Token_s,null);
        e.putInt(LoginActivity.userid_s,-1);
        e.putInt(LoginActivity.Expires_s,0);
        e.commit();
        Toast.makeText(context, "Logged out!!!.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, HomePageHandler.class);
        context.startActivity(intent);

    }
}
