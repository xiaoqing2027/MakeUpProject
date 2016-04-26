package com.example.miaodonghan.markupproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import http_requests.LogoutRequestTask;
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
