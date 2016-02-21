package com.example.miaodonghan.markupproject_01;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import us.feras.mdv.util.HttpHelper;

/**
 * Created by miaodonghan on 2/21/16.
 */
public class DocumentListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_list);

        //ListView listActivity = (ListView) findViewById(R.id.listView);

        (new RequestTask(this)).execute("http://104.194.106.254:1337/api/doc");


    }


}
