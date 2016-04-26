package com.example.miaodonghan.markupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import http_requests.LogoutRequestTask;
import http_requests.VersionListRequestTask;


public class VersionListActivity  extends AppCompatActivity {
    public final static int version_selected_id = 0;

    ListView listview;
    EditText editor;

    //TextView version_header;
    public static int doc_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_list);

        editor = (EditText)findViewById(R.id.editText);
        doc_id = getIntent().getIntExtra("position",DocumentListActivity.selected_id);


        listview = (ListView) findViewById(R.id.version_list);
        String ip = getString(R.string.ip_address);

        (new VersionListRequestTask(this,listview, doc_id)).execute(ip + "/api/doc/"+doc_id+"/version");

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
