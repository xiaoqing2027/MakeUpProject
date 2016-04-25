package com.example.miaodonghan.markupproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import http_requests.User_ListRequestTask;

/**
 * Created by miaodonghan on 4/24/16.
 */
public class User_DocListActivity extends AppCompatActivity {
    ListView listview;
    String token;
    SharedPreferences sharedPreferences;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_list);

        listview = (ListView) findViewById(R.id.doc_list);
        sharedPreferences = getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt(LoginActivity.userid_s,-1);
        String ip = getString(R.string.ip_address);
        token = sharedPreferences.getString(LoginActivity.Token_s, null);
        (new User_ListRequestTask(this, listview, token, user_id)).execute(ip + "/api/"+user_id+"/docs");
    }
}
