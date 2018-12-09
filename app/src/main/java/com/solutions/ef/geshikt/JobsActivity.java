package com.solutions.ef.geshikt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class JobsActivity extends BaseActivity {

    EditText editText2;
    EditText editText3;
    TextView textView5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editText2=findViewById(R.id.editText2);
        editText3=findViewById(R.id.editText3);
        textView5=findViewById(R.id.textView5);


        if(ParseUser.getCurrentUser() !=null){
            textView5.setText(ParseUser.getCurrentUser().getUsername());
        }else {
            textView5.setText("not logged in");
        }



    }


    public  void logInButtonPressed(View view){

        ParseUser.logInInBackground(editText2.getText().toString(), editText3.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null){
                    textView5.setText("Succesfully logged in!");
                }else {
                    textView5.setText(e.toString());
                }
            }
        });
    }
    public void logOut(View view){
        ParseUser.logOut();
        startActivity(new Intent(this,LogInActivity.class));
    }


    @Override
    int getContentViewId() {
        return R.layout.activity_jobs;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_jobs;
    }

}
