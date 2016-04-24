package com.example.miaodonghan.markupproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import http_requests.ListRequestTask;


public class DocumentListActivity extends AppCompatActivity {
    public final static int selected_id = 0;
    ListView listview;
    String token;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_list);

        listview = (ListView) findViewById(R.id.doc_list);
        sharedPreferences = getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);

        String ip = getString(R.string.ip_address);

        token = sharedPreferences.getString(LoginActivity.Token_s, null);
        (new ListRequestTask(this, listview, token)).execute(ip + "/api/doc");

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.action_settings:
//                Toast.makeText(this, "Goto LoginActivity page", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "I am action sitting",
                    Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.login) {
            Toast.makeText(this, "I am login",
                    Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.logout) {
            Toast.makeText(this, "I am logout",
                    Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.myprofile) {
            Toast.makeText(this, "I am myprofile",
                    Toast.LENGTH_LONG).show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
