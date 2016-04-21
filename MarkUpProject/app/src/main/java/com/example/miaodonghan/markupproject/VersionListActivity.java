package com.example.miaodonghan.markupproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ListView;

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


        //version_header =(TextView)findViewById(R.id.version_list_header);

        listview = (ListView) findViewById(R.id.version_list);
        String ip = getString(R.string.ip_address);

        (new VersionListRequestTask(this,listview, version_selected_id, doc_id)).execute(ip + "/api/doc/"+doc_id+"/version");

    }
}
