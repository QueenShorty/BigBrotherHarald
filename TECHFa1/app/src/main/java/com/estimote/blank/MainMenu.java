package com.estimote.blank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainMenu extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private Button LocationServ, FitBit, Admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        LocationServ = (Button)findViewById(R.id.bn_locationServ);
        FitBit = (Button)findViewById(R.id.bn_fitBit);
        Admin = (Button)findViewById(R.id.bn_admin);
        LocationServ.setOnClickListener(this);
        FitBit.setOnClickListener(this);
        Admin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.bn_locationServ:
                break;
            case R.id.bn_fitBit:
                break;
            case R.id.bn_admin:
                adminMenu();
                break;
        }
    }

    private void adminMenu() {
        Intent switchToAdminMenu = new Intent(this, adminmenu.class);
        startActivity(switchToAdminMenu);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
