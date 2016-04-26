package com.example.miaodonghan.markupproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
        if(user_id == -1){

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setIcon(R.mipmap.login);
            builder1.setMessage("          Please Login first!.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Go To Login Page",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Intent intent = new Intent(User_DocListActivity.this, LoginActivity.class);
                            startActivity(intent);
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
        }
        String ip = getString(R.string.ip_address);
        token = sharedPreferences.getString(LoginActivity.Token_s, null);
        (new User_ListRequestTask(this, listview, token, user_id)).execute(ip + "/api/"+user_id+"/docs");
    }
}
