package com.estimote.blank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainMenu extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Button DeviceServ, Profile, Admin;
    private TextView Name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        String name = getIntent().getStringExtra("USER_NAME");

        DeviceServ = (Button)findViewById(R.id.bn_deviceServ);
        Profile = (Button)findViewById(R.id.bn_profile);
        Admin = (Button)findViewById(R.id.bn_admin);
        Name = (TextView)findViewById(R.id.user_name);
        Name.setText(name);
        DeviceServ.setOnClickListener(this);
        Profile.setOnClickListener(this);
        Admin.setOnClickListener(this);

    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bn_deviceServ:
                deviceServ();
                break;
            case R.id.bn_profile:
                profilePage();
                break;
            case R.id.bn_admin:
                adminMenu();
                break;
        }
    }

    private void deviceServ() {
        Intent switchToDeviceServ = new Intent(this, DeviceServices.class );
        //switchToDeviceServ.putExtra("USER_NAME", name);
        startActivity(switchToDeviceServ);
    }

    private void profilePage() {
        Intent switchtoProfilePage = new Intent(this, ProfilePage.class);
        //switchtoProfilePage.putExtra("USER_NAME", name);
        startActivity(switchtoProfilePage);
    }

    private void adminMenu() {
        Intent switchToAdminMenu = new Intent(this, adminmenu.class);
        //switchToAdminMenu.putExtra("USER_NAME", name);
        startActivity(switchToAdminMenu);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
