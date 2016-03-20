package com.example.miaodonghan.markupproject_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by miaodonghan on 3/20/16.
 */
public class VersionListActivity  extends AppCompatActivity {
    public final static int version_selected_id = 0;
    ListView listview;
    EditText editor;
    //TextView version_header;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_list);

        editor = (EditText)findViewById(R.id.editText);
        id = getIntent().getIntExtra("position",DocumentListActivity.selected_id);
        //version_header =(TextView)findViewById(R.id.version_list_header);

        listview = (ListView) findViewById(R.id.version_list);
        String ip = getString(R.string.ip_address);

        Log.e("IPPPPPPPPL::",  ip + "/api/doc/"+id+"/version");
        (new VersionListRequestTask(this,listview, version_selected_id)).execute( ip + "/api/doc/"+id+"/version");
        //(new ListRequestTask(this,listview, selected_id)).execute("http://104.194.108.91:1337/api/doc");

    }
}
