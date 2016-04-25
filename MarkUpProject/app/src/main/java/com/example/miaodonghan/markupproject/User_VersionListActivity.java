package com.example.miaodonghan.markupproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import http_requests.User_VersionListRequestTask;


public class User_VersionListActivity extends AppCompatActivity {
    public final static int version_selected_id = 0;

    ListView listview;
    EditText editor;
    int user_id;
    SharedPreferences sharedPreferences;

    //TextView version_header;
    public static int doc_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_version_list);

        ListView listView = (ListView) findViewById(R.id.user_version_listView);
        ImageView img = (ImageView) findViewById(R.id.img);

        editor = (EditText)findViewById(R.id.editText);
        doc_id = getIntent().getIntExtra("user_position",DocumentListActivity.selected_id);
        sharedPreferences = getSharedPreferences(LoginActivity.Markup, MODE_PRIVATE);
        user_id =sharedPreferences.getInt(LoginActivity.userid_s,-1);
        listview = (ListView) findViewById(R.id.version_list);
        String ip = getString(R.string.ip_address);

        (new User_VersionListRequestTask(this,listview, doc_id, img)).execute(ip + "/api/"+user_id+"/docs/"+doc_id+"/version");

    }
}
