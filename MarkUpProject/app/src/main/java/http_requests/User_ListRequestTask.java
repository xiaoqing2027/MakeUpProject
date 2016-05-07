package http_requests;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.miaodonghan.markupproject.LoginActivity;
import com.example.miaodonghan.markupproject.R;
import com.example.miaodonghan.markupproject.User_VersionListActivity;

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


public class User_ListRequestTask extends AsyncTask<String, Integer, List<User_ListRequestTask.DocumentItem>> {

    ListView listview;
    Context context;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    String token;
    SharedPreferences sharedPreferences;
    int user_id_s ;
    int user_id_pass;
    int flag = 1;


    public User_ListRequestTask(Context context, ListView listview, String token, int user_id ){
        this.listview = listview;
        this.context = context;
        this.token = token;
        user_id_pass = user_id;
        sharedPreferences = context.getSharedPreferences(LoginActivity.Markup,Context.MODE_PRIVATE);
    }

    static class DocumentItem {
        public DocumentItem(String id, String name, String content,String updatedAt) {
            this.id = id;
            this.name = name;
            this.content =content;
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
        user_id_s = sharedPreferences.getInt(LoginActivity.userid_s,-1);
    }

    @Override
    protected List<User_ListRequestTask.DocumentItem> doInBackground(String... uri) {
        List<DocumentItem> docList = new ArrayList<>();

        if(user_id_s != user_id_pass){
            flag = 0;
            return docList;
        }else{
            try {

                URL object = new URL(uri[0]);

                HttpURLConnection connection =(HttpURLConnection) object.openConnection();

                connection.setRequestMethod("GET");

                //connection.setRequestProperty("access_token", token);
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setDoInput(true);

                InputStream response = connection.getInputStream();


                Scanner s = new Scanner(response).useDelimiter("\\A");
                String res = s.hasNext() ? s.next() : "";


                JSONArray array = new JSONArray(res);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    //Log.e("aaaaaaaaaaaaaa",((JSONObject) array.get(i)).get("versions")+"");
                    if(!((JSONObject) array.get(i)).get("versions").toString().equals("[]")){
                        DocumentItem item = new DocumentItem(
                                obj.getString("id"),
                                obj.getString("name"),
                                obj.getString("content"),
                                obj.getString("updatedAt")
                        );
                        docList.add(item);
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("backgroud task", ex.toString());
            }

            return docList;

        }

    }

    @Override
    protected void onPostExecute(List<User_ListRequestTask.DocumentItem> docList) {

        if(flag == 0){
            Toast.makeText(context, "You are only allowed to access your own profile! ", Toast.LENGTH_LONG).show();
        }else{
            // close a spinning sign

            for(int i = docList.size()-1 ; i >=0; i--) {
                Map<String, String> map = new HashMap<>();
                map.put("id", docList.get(i).id);
                map.put("name", docList.get(i).name);
                map.put("content", docList.get(i).content.substring(0,70)+"...");
                map.put("updatedAt", timeConvert(docList.get(i).updatedAt));
                data.add(map);
            }


            SimpleAdapter adapter = new SimpleAdapter(context, data,
                    R.layout.doc_item, new String[]{"name", "content", "updatedAt"},
                    new int[]{R.id.doc_name, R.id.doc_content,  R.id.doc_updatedAt}
            );

            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    Intent intent= new Intent(context, User_VersionListActivity.class);
                    int selected_doc_id = Integer.parseInt(data.get(position).get("id"));
                    SharedPreferences.Editor e =sharedPreferences.edit();
                    e.putInt(LoginActivity.doc_id_s,selected_doc_id);
                    e.commit();
                    //intent.putExtra("user_position", selected_id);
                    context.startActivity(intent);
                }

            });

        }

    }
    private String timeConvert(String s){
        return s.substring(0,19).replace('T', ' ');

    }
}