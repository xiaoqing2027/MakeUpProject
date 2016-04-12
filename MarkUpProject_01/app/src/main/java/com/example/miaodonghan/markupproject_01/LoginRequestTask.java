package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by miaodonghan on 4/10/16.
 */
public class LoginRequestTask extends AsyncTask<String, Integer, String> {

    Context context;
    String res;
    String pwd;
    String ip;
    String token;
    String expires;



    public LoginRequestTask(Context context, String ip) {

        this.context = context;
        this.ip = ip;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... data) {
        String result= "";

        HttpURLConnection urlConnection = null;
        //String url = " http://192.168.155.6:1337/api/doc/" + data[0];
        try {

            URL url = new URL(ip + "/api/auth/login");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //header
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            //urlConnection.setRequestProperty("access_token", "utf-8");

            JSONObject jsonParam = new JSONObject();
            //body
            jsonParam.put("email", data[0]);
            jsonParam.put("password", data[1]);

            String requestData = jsonParam.toString();
            urlConnection.setRequestProperty("Content-Length", "" + requestData.getBytes().length);

            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

            out.writeBytes(requestData);
            out.flush();
            out.close();
            Log.e("====login:", requestData);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());


            Scanner s = new Scanner(in).useDelimiter("\\A");
            res = s.hasNext() ? s.next() : "";
            result = res;
            Log.e("rrrrrr_login:",res);
            return res;
        } catch (Exception ex) {
            Log.e("er55r", ex.toString());
        } finally {
            urlConnection.disconnect();
        }

        return result;
    }


    //@Override
    protected void onPostExecute(String result) {



        //editor.setText(result);
        Intent intent= new Intent(context,DocumentListActivity.class);

        try {
            JSONObject r= new JSONObject(result);
            Log.i(":111111:",r.toString());
            token = r.getString("token");
            Log.i(":22222:",token);
            expires = r.getString("expires");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intent.putExtra("token", token);
        intent.putExtra("expires",expires);
        context.startActivity(intent);



    }
}
