package com.example.miaodonghan.markupproject_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by miaodonghan on 4/10/16.
 */
public class Login extends AppCompatActivity {
    EditText email_login;
    EditText pwd_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email_login = (EditText)findViewById(R.id.email_login);
        pwd_login =(EditText)findViewById(R.id.pwd_login);

        String email = email_login.getText().toString();
        String pwd = pwd_login.getText().toString();

        String ip = getString(R.string.ip_address);

        Log.e("IPPPPPPPPL::", ip + "/api/auth/login");
        (new LoginRequestTask(this,email, pwd)).execute(ip + "/api/auth/login");

    }
}
