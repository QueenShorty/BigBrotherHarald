package com.estimote.blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {

    private TextView Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        String name = getIntent().getStringExtra("USER_NAME");

        Name = (TextView)findViewById(R.id.user_name);
        Name.setText(name);


    }
}
