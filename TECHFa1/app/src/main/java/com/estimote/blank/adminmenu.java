package com.estimote.blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class adminmenu extends AppCompatActivity implements View.OnClickListener {

    private Button ControlHubSettings, UserSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmenu);

        ControlHubSettings = (Button)findViewById(R.id.bn_controlHubSettings);
        UserSettings = (Button)findViewById(R.id.bn_userSettings);
        ControlHubSettings.setOnClickListener(this);
        UserSettings.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bn_controlHubSettings:
                break;
            case R.id.bn_userSettings:
                break;
        }

    }

}
