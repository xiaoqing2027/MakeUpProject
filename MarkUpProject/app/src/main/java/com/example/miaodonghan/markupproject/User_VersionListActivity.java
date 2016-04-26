package com.example.miaodonghan.markupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import http_requests.LogoutRequestTask;
import http_requests.User_VersionListRequestTask;


public class User_VersionListActivity extends AppCompatActivity {
    public final static int version_selected_id = 0;

    EditText editor;
    int user_id;
    SharedPreferences sharedPreferences;

    //TextView version_header;
    public static int doc_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_version_list);

        ListView listView = (ListView) findViewById(R.id.user_version_listView);
        ImageView img = (ImageView) findViewById(R.id.img);
        sharedPreferences = getSharedPreferences(LoginActivity.Markup,MODE_PRIVATE);
        editor = (EditText)findViewById(R.id.editText);
        //doc_id = getIntent().getIntExtra("user_position",DocumentListActivity.selected_id);
        doc_id =sharedPreferences.getInt(LoginActivity.doc_id_s,-1);
        user_id =sharedPreferences.getInt(LoginActivity.userid_s,-1);
        //listview = (ListView) findViewById(R.id.version_list);
        String ip = getString(R.string.ip_address);

        (new User_VersionListRequestTask(this,listView, doc_id, img)).execute(ip + "/api/"+user_id+"/docs/"+doc_id+"/version");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.e("what's going on ?????", "aaaaaaa");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.home) {
            Toast.makeText(this, "GO to home page",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomePageHandler.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.login) {
            Toast.makeText(this, "I am login",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.logout) {
            Toast.makeText(this, "I am logout",
                    Toast.LENGTH_SHORT).show();
            String ip = getString(R.string.ip_address);
            LogoutRequestTask logoutRequestTask = new LogoutRequestTask(this,ip);
            Log.e("Loooggoutttt::", ip + "/api/auth/out");
            logoutRequestTask.execute();
            return true;
        }

        if (id == R.id.myprofile) {
            Toast.makeText(this, "I am myprofile",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, User_DocListActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
