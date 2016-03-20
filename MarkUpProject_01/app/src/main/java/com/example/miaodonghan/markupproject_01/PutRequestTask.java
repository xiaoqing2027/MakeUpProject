package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/**
 * Created by miaodonghan on 2/22/16.
 */
public class PutRequestTask  extends AsyncTask<String, Integer, String> {


    Context context;

    int selected_id;
    EditText editor;


    public PutRequestTask(Context context, int selected_id, EditText editor) {

        this.context = context;
        this.selected_id = selected_id;
        this.editor = editor;
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
            //String ip = getString(R.string.ip_address);
            URL url = new URL("http://104.194.116.83:1337/api/doc/" + data[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", data[1]);
            jsonParam.put("content", data[2]);
            String requestData = jsonParam.toString();
            urlConnection.setRequestProperty("Content-Length", "" + requestData.getBytes().length);

            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

            out.writeBytes(requestData);
            out.flush();
            out.close();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            Scanner s = new Scanner(in).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";
            return res;
        } catch (Exception ex) {
            Log.e("er55r", ex.getMessage());
        } finally {
            urlConnection.disconnect();
        }

        return "";
    }


    //@Override
    protected void onPostExecute(String result) {

        //editor.setText(result);

    }

}
