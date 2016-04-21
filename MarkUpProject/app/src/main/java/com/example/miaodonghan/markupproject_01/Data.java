package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by miaodonghan on 2/22/16.
 */
public class Data extends AsyncTask<String, Integer, List<Data.DocumentItem>> {

    ListView listview;
    Context context;
    int  selected_position;

    public Data(Context context, ListView listview,int  selected_position) {
        this.listview = listview;
        this.context = context;
        this.selected_position = selected_position;
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
    protected List<Data.DocumentItem> doInBackground(String... uri) {
        List<DocumentItem> docList = new ArrayList<>();
        try {
            InputStream response = new URL(uri[0]).openStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";

            JSONArray array = new JSONArray(res);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                DocumentItem item = new DocumentItem(
                        obj.getString("id"),
                        obj.getString("name"),
                        obj.getString("content"),
                        obj.getString("updatedAt")
                );
                docList.add(item);

            }

        } catch (Exception ex) {
            Log.e("backgroud task", ex.getMessage());
        }

        return docList;
    }
}
