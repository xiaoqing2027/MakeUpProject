package com.example.miaodonghan.markupproject_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by miaodonghan on 4/10/16.
 */
public class Register extends AppCompatActivity {
    EditText email;
    EditText pwd1;
    EditText pwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        email = (EditText)findViewById(R.id.email_ET);
        pwd1 =(EditText)findViewById(R.id.pwd1);
        pwd2 =(EditText)findViewById(R.id.pwd2);
        String p1 = pwd1.getText().toString();
        String p2 = pwd2.getText().toString();
        String e_mail =email.getText().toString();
        if(!p1.equals(p2)){
            Toast.makeText(Register.this, "please input password agin", Toast.LENGTH_SHORT).show();
            return;
        }

        String ip = getString(R.string.ip_address);

        Log.e("IPPPPPPPPL::", ip + "/api/auth/register");
        (new RegisterRequestTask(this,e_mail, p1)).execute(ip + "/api/auth/register");
        //(new ListRequestTask(this,listview, selected_id)).execute("http://104.194.108.91:1337/api/doc");



    }
}
