package com.example.miaodonghan.markupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by miaodonghan on 4/24/16.
 */
public class HomePageHandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Intent intent = new Intent(this, DocumentListActivity.class);
        startActivity(intent);
    }
}
