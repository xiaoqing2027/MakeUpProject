package com.example.miaodonghan.markupproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import http_requests.PostRequestTask;
import http_requests.PutRequestTask;
import us.feras.mdv.MarkdownView;


public class ShowPreviewActivity extends AppCompatActivity {
    Button savebtn;
    Button savebtn_newversion;
    SharedPreferences sharedPreferences;
    String ip;
    int doc_id;
    int version_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        savebtn = (Button) findViewById(R.id.save_old_p);
        savebtn.setOnClickListener(mySaveTtn);
        savebtn_newversion = (Button) findViewById(R.id.save_new_p);
        savebtn_newversion.setOnClickListener(mySaveTtn_newversion);
        ip = getString(R.string.ip_address);

        Log.i("+++++++", MainActivity.editor_content);
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdown(MainActivity.editor_content);
    }

        View.OnClickListener mySaveTtn = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            PutRequestTask putRequestTask = new PutRequestTask(ShowPreviewActivity.this,version_id,ip,doc_id);
//            String text = editor.getText().toString();
//            int start = editor.getLayout().getLineStart(0);
//            int end = editor.getLayout().getLineStart(1);
//            int cstart =editor.getLayout().getLineStart(2);
//            String name = text.substring(start, end);
//            String content = text.substring(cstart,text.length());
            String name = sharedPreferences.getString(LoginActivity.doc_name_s, null);
            String content = sharedPreferences.getString(LoginActivity.doc_content_s, null);
            String token = sharedPreferences.getString(LoginActivity.Token_s, null);
            if(token !=null){
                putRequestTask.execute(""+version_id,name,content);
            }else{
                Toast.makeText(ShowPreviewActivity.this, "Please LoginActivity first!", Toast.LENGTH_LONG).show();
            }

        }
    };
    View.OnClickListener mySaveTtn_newversion = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            PostRequestTask postRequestTask = new PostRequestTask(ShowPreviewActivity.this,ip,doc_id);

//            String text = editor.getText().toString();
//            int start = editor.getLayout().getLineStart(0);
//            int end = editor.getLayout().getLineStart(1);
//            int cstart =editor.getLayout().getLineStart(2);
//            String name = text.substring(start, end);
//            String content = text.substring(cstart,text.length());
            String name = sharedPreferences.getString(LoginActivity.doc_name_s, null);
            String content = sharedPreferences.getString(LoginActivity.doc_content_s, null);
            String token = sharedPreferences.getString(LoginActivity.Token_s,null);
            if(token !=null){
                postRequestTask.execute(name, content);
            }else{
                Toast.makeText(ShowPreviewActivity.this, "Please Login first!", Toast.LENGTH_LONG).show();
            }

        }
    };

}
