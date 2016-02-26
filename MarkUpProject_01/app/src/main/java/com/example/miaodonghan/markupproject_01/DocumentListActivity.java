package com.example.miaodonghan.markupproject_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by miaodonghan on 2/21/16.
 */
public class DocumentListActivity extends AppCompatActivity {
    public final static int selected_id = 0;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_list);

        listview = (ListView) findViewById(R.id.listView);

        (new ListRequestTask(this,listview, selected_id)).execute("http://104.194.121.69:1337/api/doc");
        //(new ListRequestTask(this,listview, selected_id)).execute("http://104.194.108.91:1337/api/doc");

    }


}
