package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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
public class RegisterRequestTask extends AsyncTask<String, Integer, String> {

    Context context;
    String email;
    String pwd;
    String ip;


    public RegisterRequestTask(Context context, String email, String pwd) {

        this.context = context;
        this.email = email;
        this.pwd = pwd;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... data) {
        String status = "";

        HttpURLConnection urlConnection = null;
        //String url = " http://192.168.155.6:1337/api/doc/" + data[0];
        try {

            URL url = new URL(ip + "/api/auth/register");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //header
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");

            JSONObject jsonParam = new JSONObject();
            //body
            jsonParam.put("email", data[0]);
            jsonParam.put("password", data[1]);

            String requestData = jsonParam.toString();
            urlConnection.setRequestProperty("Content-Length", "" +  requestData.getBytes().length);
            Log.e("------:", requestData.getBytes().length + "");
            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

            out.writeBytes(requestData);
            out.flush();
            out.close();
            Log.e("====:", requestData);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());


            Scanner s = new Scanner(in).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";
            Log.e("rrrrrr:",res);
            return res;
        } catch (Exception ex) {
            Log.e("er55r", ex.toString());
        } finally {
            urlConnection.disconnect();
        }

        return "";
    }


    //@Override
    protected void onPostExecute(String result) {

        //editor.setText(result);
        Intent intent= new Intent(context,Login.class);
        context.startActivity(intent);



    }
}
