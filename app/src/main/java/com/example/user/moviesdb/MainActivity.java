package com.example.user.moviesdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.login_button);
        signinButton = (Button) findViewById(R.id.signin_button);

        loginButton.setOnClickListener(this);
        signinButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                /*Intent loginintent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(loginintent);*/
                break;
            case R.id.signin_button:
                Intent registerintent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerintent);
                break;
        }
    }
}
