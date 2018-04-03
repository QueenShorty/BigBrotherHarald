package com.estimote.blank;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import static android.view.View.GONE;

public class adminmenu extends AppCompatActivity implements View.OnClickListener {

    private Button ControlHubSettings, UserSettings, ManageUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminmenu);
        String name = getIntent().getStringExtra("USER_NAME");

        ControlHubSettings = (Button)findViewById(R.id.bn_controlHubSettings);
        UserSettings = (Button)findViewById(R.id.bn_userSettings);
        ManageUsers = (Button)findViewById(R.id.bn_manageUsers);

        ControlHubSettings.setOnClickListener(this);
        UserSettings.setOnClickListener(this);
        ManageUsers.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bn_controlHubSettings:
                controlHub();
                break;
            case R.id.bn_manageUsers:
                manageUsers();
                break;
            case R.id.bn_userSettings:
                break;
        }

    }

    private void manageUsers() {
        Intent switchToManageUsers = new Intent(this, ManageUsers.class );
        startActivity(switchToManageUsers);
    }

    private void controlHub() {
        Intent switchToControlHub = new Intent(this, ControlHub.class );
        startActivity(switchToControlHub);
    }


}
