package com.estimote.blank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

        DeviceServ = (Button)findViewById(R.id.bn_deviceServ);
        Profile = (Button)findViewById(R.id.bn_profile);
        Admin = (Button)findViewById(R.id.bn_admin);
        Name = (TextView)findViewById(R.id.user_name);
        Name.setVisibility(View.GONE);
        //Name.setText(name);
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
        startActivity(switchToDeviceServ);
    }

    private void profilePage() {
        Intent switchtoProfilePage = new Intent(this, ProfilePage.class);
        startActivity(switchtoProfilePage);
    }

    private void adminMenu() {
        Intent switchToAdminMenu = new Intent(this, adminmenu.class);
        startActivity(switchToAdminMenu);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
