package com.example.miaodonghan.markupproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import http_requests.LoginRequestTask;


public class LoginActivity extends AppCompatActivity {
    public static final String Markup = "markup";
    public static final String Email_s = "email";
    public static final String Password_s = "password";
    public static final String Token_s = "token";
    public static final String Expires_s = "expeires";
    public static final String userid_s = "user_id";
    public static final String doc_name_s = "doc_name";
    public static final String doc_content_s = "doc_content";
    public static final String origin_name_s = "origin_name";
    public static final String origin_content_s = "origin_content";
    public static final String origin_updated_s = "origin_updated";
    //this is for url
    public static final String doc_id_s = "doc_id_s";
    EditText email_login;
    EditText pwd_login;
    SharedPreferences sharedPreferences;
    Button login_btn;
    TextView regist_link;
    String email;
    String pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email_login = (EditText) findViewById(R.id.email_login);
        pwd_login = (EditText) findViewById(R.id.pwd_login);
        login_btn = (Button) findViewById(R.id.login_btn);
        regist_link = (TextView) findViewById(R.id.register_link);

        sharedPreferences = getSharedPreferences(Markup, Context.MODE_PRIVATE);


        email_login.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //email_r = email.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.i("11111", email_login.getText().toString());
                email = email_login.getText().toString();

            }
        });
        pwd_login.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // p1_r = pwd1.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

                pwd = pwd_login.getText().toString();
                Log.i("11111", pwd);
            }
        });

//        if(email_register == ""){
//            email_register =email;
//        }
//        if(pwd_register == ""){
//            pwd_register = pwd;
//            Log.d("pwe", pwd_register);
//        }

        //get and display data


        email_login.setText(sharedPreferences.getString(Email_s, null));
        pwd_login.setText(sharedPreferences.getString(Password_s, null));


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email_internal = email_login.getText().toString();
                String pwd_internal = pwd_login.getText().toString();

                if (email_internal.length() == 0 || pwd_internal.length() == 0) {
                    Toast.makeText(LoginActivity.this, "please input email and password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String ip = getString(R.string.ip_address);
                LoginRequestTask loginRequestTask = new LoginRequestTask(LoginActivity.this,ip);
                Log.e("IPPPPPPPPL::", ip + "/api/auth/login");
                loginRequestTask.execute(email, pwd);

            }
        });

        regist_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });


    }
}
