package com.example.miaodonghan.markupproject_01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by miaodonghan on 4/10/16.
 */
public class Login extends AppCompatActivity {
    public static final String Markup ="markup";
    public static final String Email_s = "email";
    public static final String Password_s = "password";
    public static final String Token_s = "token";
    public static final String Expires_s = "expeires";
    EditText email_login;
    EditText pwd_login;
    SharedPreferences sharedPreferences;
    Button login_btn;
    TextView regist_link;
    String email_register;
    String pwd_register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email_login = (EditText)findViewById(R.id.email_login);
        pwd_login =(EditText)findViewById(R.id.pwd_login);
        login_btn =(Button)findViewById(R.id.login_btn);
        regist_link = (TextView) findViewById(R.id.register_link);
        //get register information from register page
        email_register =getIntent().getStringExtra("e");
        pwd_register =getIntent().getStringExtra("p");
        //write into sharedpreference
        sharedPreferences = getSharedPreferences(Markup, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Email_s, email_register);
        editor.putString(Password_s, pwd_register);
        editor.commit();

        //get and display data
        email_login.setText(sharedPreferences.getString(Email_s,null));
        Log.i("dddddd", "aaaaaa");
        pwd_login.setText(sharedPreferences.getString(Password_s,null));


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_login.getText().toString();
                String pwd = pwd_login.getText().toString();

                if (email.length() == 0 || pwd.length() == 0) {
                    Toast.makeText(Login.this, "please input email and password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String ip = getString(R.string.ip_address);
                LoginRequestTask loginRequestTask = new LoginRequestTask(Login.this,ip);
                Log.e("IPPPPPPPPL::", ip + "/api/auth/login");
                loginRequestTask.execute(email,pwd);

            }
        });

        regist_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,Register.class);
                Login.this.startActivity(intent);
            }
        });


    }
}
