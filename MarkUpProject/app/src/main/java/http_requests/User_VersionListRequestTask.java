package http_requests;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.miaodonghan.markupproject.DocumentListActivity;
import com.example.miaodonghan.markupproject.LoginActivity;
import com.example.miaodonghan.markupproject.MainActivity;
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


public class User_VersionListRequestTask extends AsyncTask<String, Integer, List<User_VersionListRequestTask.VersionItem>> {


    ListView listview;
    Context context;
    ImageView img;
    int doc_id;
    int user_id;
    String u_r_l;
    String token;
    List<Map<String, Object>> data_version = new ArrayList<Map<String, Object>>();
    SharedPreferences sharedPreferences;
    int flag =0;
    private int selectedItemIndex = 0;
    private int clickedItemIndex = selectedItemIndex;


    public User_VersionListRequestTask(Context context, ListView listview, int doc_id, ImageView img) {
        this.listview = listview;
        this.context = context;
        this.doc_id = doc_id;
        this.img = img;
        sharedPreferences =context.getSharedPreferences(LoginActivity.Markup,Context.MODE_PRIVATE);
    }


    static class VersionItem {
        public VersionItem(String id, String name, String content, String updatedAt, String share) {
            this.id = id;
            this.name = name;
            this.content = content;
            this.updatedAt = updatedAt;
            this.share =share;
        }

        String id;
        String name;
        String content;
        String updatedAt;
        String share;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
        token = sharedPreferences.getString(LoginActivity.Token_s,null);
        user_id =sharedPreferences.getInt(LoginActivity.userid_s,-1);
    }

    @Override
    protected List<User_VersionListRequestTask.VersionItem> doInBackground(String... uri) {
        List<VersionItem> docList = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        try {
            u_r_l =uri[0];
            URL url = new URL(uri[0]);

            urlConnection = (HttpURLConnection) url.openConnection();
            Log.i("U_r_l______000",u_r_l);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setDoInput(true);

            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";

            JSONArray array = new JSONArray(res);
            if(array.length() == 0){
                flag = 1;
                Log.e("flag:",flag+"");
            }else{
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    VersionItem item = new VersionItem(
                            obj.getString("id"),
                            obj.getString("name"),
                            obj.getString("content"),
                            obj.getString("updatedAt"),
                            obj.getString("share")
                    );
                    docList.add(item);

                }
            }

        } catch (Exception ex) {
            Log.e("backgroud task", ex.getMessage());
        }

        return docList;
    }

    @Override
    protected void onPostExecute(List<User_VersionListRequestTask.VersionItem> docList) {

        if(flag == 1){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setIcon(R.mipmap.login);
            builder1.setMessage("            You have go to create your own docment firstly!.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Go To DocumentList Page",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent intent = new Intent(context, DocumentListActivity.class);
                            context.startActivity(intent);
                        }
                    });

            builder1.setNegativeButton(
                    "Stay in this page",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }else{
            // close a spinning sign

            for(int i = docList.size()-1 ; i >=0; i--) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", docList.get(i).id);
                map.put("name", docList.get(i).name);
//                map.put("content", docList.get(i).content);
//                map.put("updatedAt", docList.get(i).updatedAt);
                map.put("content", docList.get(i).content.substring(0,30)+" ...");
                map.put("updatedAt", timeConvert(docList.get(i).updatedAt));
                Log.i("share value:", docList.get(i).share);
                if(docList.get(i).share.equals("0")){
                    map.put("img", R.mipmap.share);
                }else{
                    map.put("img", R.mipmap.shared);
                }

                data_version.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(context, data_version,
                    R.layout.user_version_item, new String[]{"name", "content", "updatedAt","img"},
                    new int[]{ R.id.name, R.id.content, R.id.update_time, R.id.img}
            );

            listview.setAdapter(adapter);


            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View view, final int position, long id) {
                    final String[] item_list = context.getResources().getStringArray(
                            R.array.user_version_item_array);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context)
                            .setTitle("Select your Choice")
                            .setSingleChoiceItems(R.array.user_version_item_array, selectedItemIndex,
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            clickedItemIndex = which;
                                            //Toast.makeText(context, item_list[which],Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .setPositiveButton(R.string.ok_btn, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    selectedItemIndex = clickedItemIndex;
                                    if(selectedItemIndex == 0){
                                        Intent intent = new Intent(context, MainActivity.class);
                                        int version_selected_id = Integer.parseInt(data_version.get(position).get("id").toString());
                                        intent.putExtra("version_position", version_selected_id);
                                        intent.putExtra("doc_position", doc_id);
                                        context.startActivity(intent);
                                    }
                                    if(selectedItemIndex == 1){
                                        int version_selected_id = Integer.parseInt(data_version.get(position).get("id").toString());
                                        String ip = u_r_l+"/"+version_selected_id;
                                        String name =data_version.get(position).get("name").toString();
                                        String content=data_version.get(position).get("content").toString();
                                        String share =data_version.get(position).get("img").toString();
                                        share =(share.equals(""+R.mipmap.share))? ("1"):("0");
                                        User_PutRequestTask user_putRequestTask = new User_PutRequestTask(context,version_selected_id,ip,doc_id,data_version,position);
                                        user_putRequestTask.execute(""+version_selected_id,name, content, share);
                                        Intent intent = new Intent(context, User_VersionListActivity.class);
                                        SharedPreferences.Editor e =sharedPreferences.edit();
                                        e.putInt(LoginActivity.doc_id_s,doc_id);
                                        e.commit();
                                        context.startActivity(intent);
                                    }
                                    if(selectedItemIndex == 2){
                                        String ip = context.getString(R.string.ip_address);
                                        int version_selected_id = Integer.parseInt(data_version.get(position).get("id").toString());
                                        (new User_VersionDeleteRequestTask(context,version_selected_id)).execute(ip + "/api/"+user_id+"/docs/"+doc_id+"/version/"+version_selected_id);
                                        //Toast.makeText(context, "Remove this version successfully!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(context, User_VersionListActivity.class);
                                        SharedPreferences.Editor e =sharedPreferences.edit();
                                        e.putInt(LoginActivity.doc_id_s,doc_id);
                                        e.commit();
                                        context.startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.show();

                }

            });



//            listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                    int version_selected_id = Integer.parseInt(data_version.get(position).get("id").toString());
//                    String ip = u_r_l+"/"+version_selected_id;
//                    String name =data_version.get(position).get("name").toString();
//                    String content=data_version.get(position).get("content").toString();
//                    String share =data_version.get(position).get("img").toString();
//                    share =(share.equals(""+R.mipmap.share))? ("1"):("0");
//                    User_PutRequestTask user_putRequestTask = new User_PutRequestTask(context,version_selected_id,ip,doc_id,data_version,position);
//                    user_putRequestTask.execute(""+version_selected_id,name, content, share);
//                    Intent intent = new Intent(context, User_DocListActivity.class);
//                    context.startActivity(intent);
//                    return true;
//                }
//            });
        }


    }
    private String timeConvert(String s){
        return s.substring(0,19).replace('T', ' ');

    }
}

