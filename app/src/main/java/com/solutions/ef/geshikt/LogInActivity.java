package com.solutions.ef.geshikt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LogInActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


    }

    public void logIn(View view) {

        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    startActivity(new Intent(getApplication(), MapsActivity.class));
                } else {

                    Toast toast= Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    public void signUp(View view){


        ParseUser user =new ParseUser();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    startActivity(new Intent(getApplication(), MapsActivity.class));
                }else {
                    Toast toast= Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}
