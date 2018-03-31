package com.estimote.blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DeviceServices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_services);
        String name = getIntent().getStringExtra("USER_NAME");
    }
}
