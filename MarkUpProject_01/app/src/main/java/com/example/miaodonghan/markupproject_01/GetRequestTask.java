package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    String testDoc;

    public GetRequestTask(Context context, int selected_position,String testDoc) {

        this.context = context;
        this.selected_position = selected_position;
        this.testDoc =testDoc;
    }

    static class DocumentItem {
        String id;
        String name;
        String content;
        String updatedAt;


        public DocumentItem(String id, String name, String content, String updatedAt) {
            this.id = id;
            this.name = name;
            this.updatedAt = updatedAt;
            this.content = content;

        }

    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... uri) {
        String result ="";
       // Map<String, String> map = new HashMap<>();
        try {
            InputStream response = new URL(uri[0]).openStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";
            Log.e(" testtestResult"," ");
            JSONObject obj = new  JSONObject(res);
//            map.put("id", obj.getString("id"));
//            map.put("name", obj.getString("name"));
//            map.put("content", obj.getString("content"));
//            map.put("updatedAt", obj.getString("updatedAt"));

             result = "###"+obj.getString("name") + "\n\n" + obj.getString("updatedAt")+"\n"+
                    obj.getString("content");
        } catch (Exception ex) {
           // Log.e("backgroud task", ex.getMessage());
        }

        return result;
    }

    //@Override
    protected void onPostExecute(String result) {
        Log.e("####################"," ");
            testDoc = result;

    }
}