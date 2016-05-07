package http_requests;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.miaodonghan.markupproject.R;
import com.example.miaodonghan.markupproject.VersionListActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class ListRequestTask extends AsyncTask<String, Integer, List<ListRequestTask.DocumentItem>> {
    ListView listview;
    Context context;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    // SharedPreferences sharedPreferences;

    public ListRequestTask(Context context, ListView listview) {
        this.listview = listview;
        this.context = context;
        //this.sharedPreferences = context.getSharedPreferences(LoginActivity.Markup,context.MODE_PRIVATE);
    }

    static class DocumentItem {
        public DocumentItem(String id, String name, String content, String updatedAt) {
            this.id = id;
            this.name = name;
            this.content = content;
            this.updatedAt = updatedAt;
        }

        String id;
        String name;
        String content;
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
            URL object = new URL(uri[0]);

            HttpURLConnection connection = (HttpURLConnection) object.openConnection();

            connection.setRequestMethod("GET");

            //connection.setRequestProperty("access_token", token);
            //connection.setRequestProperty("Authorization", "Bearer " + token);
            //connection.setDoInput(true);

            InputStream response = connection.getInputStream();

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
            ex.printStackTrace();
            Log.e("backgroud task", ex.toString());
        }

        return docList;
    }

    @Override
    protected void onPostExecute(List<ListRequestTask.DocumentItem> docList) {

        // close a spinning sign

<<<<<<< HEAD
        for(int i = docList.size()-1 ; i >=0; i--) {
=======
        for (int i = 0; i < docList.size(); i++) {
>>>>>>> eb5c667e9a39e7dd523eaa2327f3e1fa5754e266
            Map<String, String> map = new HashMap<>();
            map.put("id", docList.get(i).id);
            map.put("name", docList.get(i).name);
            map.put("content", docList.get(i).content.substring(0, 70) + "...");
            map.put("updatedAt", timeConvert(docList.get(i).updatedAt));
<<<<<<< HEAD
=======
            String p = timeConvert(docList.get(i).updatedAt);
>>>>>>> eb5c667e9a39e7dd523eaa2327f3e1fa5754e266
            data.add(map);
        }


        SimpleAdapter adapter = new SimpleAdapter(context, data,
                R.layout.doc_item, new String[]{"name", "content", "updatedAt"},
                new int[]{R.id.doc_name, R.id.doc_content, R.id.doc_updatedAt}
        );

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                Intent intent = new Intent(context, VersionListActivity.class);
                int selected_id = Integer.parseInt(data.get(position).get("id"));
//                SharedPreferences.Editor e =sharedPreferences.edit();
//                e.putString(LoginActivity.origin_name_s,data.get(position).get("name"));
//                e.putString(LoginActivity.origin_name_s,data.get(position).get("content"));

                intent.putExtra("position", selected_id);
                context.startActivity(intent);
            }

        });
    }

    private String timeConvert(String s) {
        return s.substring(0, 19).replace('T', ' ');
    }
}