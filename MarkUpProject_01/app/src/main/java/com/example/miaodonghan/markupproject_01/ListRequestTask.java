package com.example.miaodonghan.markupproject_01;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class ListRequestTask extends AsyncTask<String, Integer, List<ListRequestTask.DocumentItem>> {

    ListView listview;
    Context context;
    int  selected_position;

    public ListRequestTask(Context context, ListView listview,int  selected_position) {
        this.listview = listview;
        this.context = context;
        this.selected_position = selected_position;
    }

    static class DocumentItem {
        public DocumentItem(String id, String name, String updatedAt) {
            this.id = id;
            this.name = name;
            this.updatedAt = updatedAt;
        }
        String id;
        String name;
        String updatedAt;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected List<ListRequestTask.DocumentItem> doInBackground(String... uri) {
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
                    obj.getString("updatedAt")
                );
                docList.add(item);

            }

        } catch (Exception ex) {
            Log.e("backgroud task", ex.getMessage());
        }

        return docList;
    }

    @Override
    protected void onPostExecute(List<ListRequestTask.DocumentItem> docList) {

        // close a spinning sign

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        for(int i = 0; i < docList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", docList.get(i).id);
            map.put("name", docList.get(i).name);
            map.put("updatedAt", docList.get(i).updatedAt);
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, data,
                R.layout.item, new String[]{"name", "updatedAt"},
                new int[]{R.id.name, R.id.updatedAt}
        );

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                Intent intent = new Intent(context, MainActivity.class);
                selected_position = position;
                intent.putExtra("position", selected_position);
                context.startActivity(intent);
            }

        });


    }
}