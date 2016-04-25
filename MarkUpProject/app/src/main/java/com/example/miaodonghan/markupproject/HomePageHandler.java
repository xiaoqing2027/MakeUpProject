package com.example.miaodonghan.markupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by miaodonghan on 4/24/16.
 */
public class HomePageHandler extends AppCompatActivity {
    Button loginbtn;
    Button registerbtn;
    Button skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        loginbtn =(Button)findViewById(R.id.login_home);
        registerbtn =(Button)findViewById(R.id.register_home);
        skip =(Button)findViewById(R.id.skip);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageHandler.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageHandler.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageHandler.this, DocumentListActivity.class);
                startActivity(intent);
            }
        });

    }
}
