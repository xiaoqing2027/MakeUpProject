package com.example.miaodonghan.markupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import http_requests.LogoutRequestTask;
import http_requests.User_PostRequestTask;
import us.feras.mdv.MarkdownView;


public class ShowPreviewActivity extends AppCompatActivity {

    Button savebtn_newversion;
    //Button back_edit;
    SharedPreferences sharedPreferences;
    String ip;
    int doc_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        doc_id = getIntent().getIntExtra("doc_id_for_preview",-1);
        Log.e("preview________", doc_id+"");
        //back_edit =(Button) findViewById(R.id.preview_pdf);

        //savebtn = (Button) findViewById(R.id.user_save_old_p);
        //savebtn.setOnClickListener(mySaveTtn);
        savebtn_newversion = (Button) findViewById(R.id.user_save_new_p);
        savebtn_newversion.setOnClickListener(mySaveTtn_newversion);
        sharedPreferences = getSharedPreferences(LoginActivity.Markup,MODE_PRIVATE);
        ip = getString(R.string.ip_address);

        Log.i("+++++++", MainActivity.editor_content);
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.user_markdownView);
        markdownView.loadMarkdown(MainActivity.editor_content);
    }


    View.OnClickListener mySaveTtn_newversion = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            User_PostRequestTask user_postRequestTask = new User_PostRequestTask(ShowPreviewActivity.this,ip,doc_id);
            String name = sharedPreferences.getString(LoginActivity.doc_name_s,"");
            Log.i("name:::::::post", name);
            String content = sharedPreferences.getString(LoginActivity.doc_content_s, "");
            Log.i("content:::::::post", content);
            String token = sharedPreferences.getString(LoginActivity.Token_s,"");
            Log.e("tokennnnnnnn", token);
            if(!token.equals("")){
                user_postRequestTask.execute(name, content);
            }else{
                Toast.makeText(ShowPreviewActivity.this, "Please Login first!", Toast.LENGTH_LONG).show();
            }

        }
    };

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


//    View.OnClickListener mySaveTtn = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            PutRequestTask putRequestTask = new PutRequestTask(ShowPreviewActivity.this,version_id,ip,doc_id);
////            String text = editor.getText().toString();
////            int start = editor.getLayout().getLineStart(0);
////            int end = editor.getLayout().getLineStart(1);
////            int cstart =editor.getLayout().getLineStart(2);
////            String name = text.substring(start, end);
////            String content = text.substring(cstart,text.length());
//
//            String name = sharedPreferences.getString(LoginActivity.doc_name_s, null);
//            Log.i("name:::::::put", name);
//            String content = sharedPreferences.getString(LoginActivity.doc_content_s, null);
//            Log.i("content:::::::put", content);
//            String token = sharedPreferences.getString(LoginActivity.Token_s, null);
//            if(token !=null){
//                putRequestTask.execute(""+version_id,name,content);
//            }else{
//                Toast.makeText(ShowPreviewActivity.this, "Please LoginActivity first!", Toast.LENGTH_LONG).show();
//            }
//
//        }
//    };

}
