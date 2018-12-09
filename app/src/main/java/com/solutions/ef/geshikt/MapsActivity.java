package com.solutions.ef.geshikt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MapsActivity extends BaseActivity {
    TextView textView2;
    EditText editText;
    Button button;


    @Override
    int getContentViewId() {
        return R.layout.activity_maps;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_maps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        if(ParseUser.getCurrentUser() ==null){
            Intent intent=new Intent(this,LogInActivity.class);
            startActivity(intent);
        }

        editText=findViewById(R.id.editText);
        textView2=findViewById(R.id.textView2);
        button=findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickedTheButton();

            }
        });



    }
    public  void onClickedTheButton(){
        ParseUser user =new ParseUser();
        user.setUsername(editText.getText().toString());
        user.setPassword("robbypass");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                   textView2.setText("SignUp succesfull");
                    Log.i("SignUp","SignUp succesfull");
                }else {
                    textView2.setText(e.toString());
                    Log.i("SignUp","SignUp failed");
                }
            }
        });
    }

    public void goToMap(View view){

        startActivity(new Intent(getApplicationContext(),GoogleMaps.class));
    }


}
