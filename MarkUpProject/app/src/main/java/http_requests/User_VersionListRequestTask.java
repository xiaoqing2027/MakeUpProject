package http_requests;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.miaodonghan.markupproject.MainActivity;
import com.example.miaodonghan.markupproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class User_VersionListRequestTask extends AsyncTask<String, Integer, List<User_VersionListRequestTask.VersionItem>> {

    ListView listview;
    Context context;
    ImageView img;
    int doc_id;
    String u_r_l;
    List<Map<String, Object>> data_version = new ArrayList<Map<String, Object>>();

    public User_VersionListRequestTask(Context context, ListView listview, int doc_id, ImageView img) {
        this.listview = listview;
        this.context = context;
        this.doc_id = doc_id;
        this.img = img;
    }


    static class VersionItem {
        public VersionItem(String id, String name, String content, String updatedAt) {
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
    protected List<User_VersionListRequestTask.VersionItem> doInBackground(String... uri) {
        List<VersionItem> docList = new ArrayList<>();

        try {
            u_r_l =uri[0];
            InputStream response = new URL(uri[0]).openStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";

            JSONArray array = new JSONArray(res);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                VersionItem item = new VersionItem(
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

    @Override
    protected void onPostExecute(List<User_VersionListRequestTask.VersionItem> docList) {

        // close a spinning sign

        for (int i = 0; i < docList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", docList.get(i).id);
            map.put("name", docList.get(i).name);
            map.put("content", docList.get(i).content);
            map.put("updatedAt", docList.get(i).updatedAt);
            map.put("img", R.mipmap.share);
            data_version.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, data_version,
                R.layout.version_item, new String[]{"name", "content", "updatedAt","img"},
                new int[]{ R.id.name, R.id.content, R.id.update_time, R.id.img}
        );

        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, final int position, long id) {
                Intent intent = new Intent(context, MainActivity.class);
                int version_selected_id = Integer.parseInt(data_version.get(position).get("id").toString());
                intent.putExtra("version_position", version_selected_id);
                intent.putExtra("doc_position", doc_id);
                context.startActivity(intent);
            }

        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int version_selected_id = Integer.parseInt(data_version.get(position).get("id").toString());
                String ip = u_r_l+"/"+version_selected_id;
                User_PutRequestTask user_putRequestTask = new User_PutRequestTask(context,version_selected_id,ip,doc_id,data_version,position);
                user_putRequestTask.execute(""+version_selected_id,"1");
                //
                return false;
            }
        });

    }
}

