package com.example.miaodonghan.markupproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import us.feras.mdv.MarkdownView;


public class ShowPreviewActivity extends AppCompatActivity {

    //String editor_con = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        // editor_con = getIntent().getStringExtra("editor_c");
        Log.i("+++++++", MainActivity.editor_content);
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdown(MainActivity.editor_content);
    }
}
