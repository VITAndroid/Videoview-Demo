package com.example.videoview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
    public EditText username,password;
    Button loginbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.uname);
        password = findViewById(R.id.pass);
        loginbut = findViewById(R.id.loginbutton);
    }

    public void onLogin(View view){
        Intent it = new Intent(Login.this, ValidateService.class);
        it.putExtra("username", username.getText().toString());
        it.putExtra("password", password.getText().toString());
        startService(it);
    }

}