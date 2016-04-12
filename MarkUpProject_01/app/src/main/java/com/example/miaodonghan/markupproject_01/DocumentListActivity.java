package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by miaodonghan on 2/21/16.
 */
public class DocumentListActivity extends AppCompatActivity {
    public final static int selected_id = 0;
    ListView listview;
    String token;
    String expires;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_list);

        listview = (ListView) findViewById(R.id.doc_list);
        //get login information from register page
        token =getIntent().getStringExtra("token");
        expires =getIntent().getStringExtra("expires");
        Log.i(":111111:",expires);
        sharedPreferences = getSharedPreferences(Login.Markup, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Login.Token_s, token);
        editor.putString(Login.Expires_s, expires);
        editor.commit();

        String ip = getString(R.string.ip_address);
        Log.e("IPPPPPPPPL::", ip);
        (new ListRequestTask(this,listview, selected_id,token)).execute( ip + "/api/doc");
        //(new ListRequestTask(this,listview, selected_id)).execute("http://104.194.108.91:1337/api/doc");

    }


}
