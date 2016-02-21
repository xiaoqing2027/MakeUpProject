package com.example.miaodonghan.markupproject_01;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.util.Log;
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


class RequestTask extends AsyncTask<String, Integer, List<RequestTask.DocumentItem>> {
    ListActivity listActivity;

    public RequestTask(ListActivity listActivity) {
        this.listActivity = listActivity;
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
    protected List<RequestTask.DocumentItem> doInBackground(String... uri) {
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
    protected void onPostExecute(List<RequestTask.DocumentItem> docList) {

        // close a spinning sign

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        for(int i = 0; i < docList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", docList.get(i).id);
            map.put("name", docList.get(i).name);
            map.put("updatedAt", docList.get(i).updatedAt);
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(listActivity, data,
                R.layout.item, new String[]{"name", "updatedAt"},
                new int[]{R.id.name, R.id.updatedAt}
        );
        listActivity.setListAdapter(adapter);
    }
}