package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

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
        sharedPreferences = getSharedPreferences(Login.Markup, Context.MODE_PRIVATE);

        String ip = getString(R.string.ip_address);
        Log.e("IPPPPPPPPL::", ip);
        token = sharedPreferences.getString(Login.Token_s,null);
        (new ListRequestTask(this,listview, selected_id,token)).execute( ip + "/api/doc");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Toast.makeText(this, "Goto Login page", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(this,Login.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }

}
