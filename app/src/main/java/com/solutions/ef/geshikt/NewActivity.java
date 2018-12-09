package com.solutions.ef.geshikt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewActivity extends BaseActivity{


    @Override
    int getContentViewId() {
        return R.layout.activity_new;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_new;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        TextView textView4=findViewById(R.id.textView4);
        textView4.setText("New new new new!!!");
    }
}
