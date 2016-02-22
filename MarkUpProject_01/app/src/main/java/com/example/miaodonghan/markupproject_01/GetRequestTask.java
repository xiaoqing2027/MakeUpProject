package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class GetRequestTask extends AsyncTask<String, Integer, String> {


    Context context;

    int selected_position;
    EditText editor;

    public GetRequestTask(Context context, int selected_position,EditText editor) {

        this.context = context;
        this.selected_position = selected_position;
        this.editor =editor;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... uri) {
        String result ="";
        try {

            InputStream response = new URL(uri[0]).openStream();

            Scanner s = new Scanner(response).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";
            Log.e("++++++++++++++++", res.toString());
            JSONObject obj = new  JSONObject(res);


             result = "#"+obj.getString("name") + "\n\nupdate Time :  " + obj.getString("updatedAt")+"\n\n"+
                    obj.getString("content");


        } catch (Exception ex) {
           // Log.e("backgroud task", ex.getMessage());
        }

        return result;
    }

    //@Override
    protected void onPostExecute(String result) {

         editor.setText(result);

    }
}