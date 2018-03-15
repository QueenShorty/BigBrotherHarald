package com.estimote.blank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    private SignInButton SignIn;
    private Button SignOut, MainMenu;
    private ImageView Logo;
    private TextView Name;
    private GoogleApiClient googleApiClient;
    private static final int REQ_Code = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignIn = (SignInButton)findViewById(R.id.bn_login);
        SignOut = (Button)findViewById(R.id.bn_logout);
        MainMenu = (Button)findViewById(R.id.bn_mainMenu);
        Logo = (ImageView)findViewById(R.id.Logo);
        Name = (TextView)findViewById(R.id.name);
        SignIn.setOnClickListener(this);
        SignOut.setOnClickListener(this);
        MainMenu.setOnClickListener(this);
        Name.setVisibility(View.GONE);
        SignOut.setVisibility(View.GONE);
        MainMenu.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bn_login:
                signIn();
                break;
            case R.id.bn_logout:
                signOut();
                break;
            case R.id.bn_mainMenu:
                mainMenu();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    private void signIn()
    {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_Code);
    }

    private  void signOut()
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }

    private void mainMenu()
    {
        Intent switchToMainMenu = new Intent(this, MainMenu.class);
        startActivity(switchToMainMenu);
        
    }

    private void handleResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            Name.setText(name);
            updateUI(true);

            Intent switchToHomepage = new Intent(this, MainMenu.class);
            startActivity(switchToHomepage);

        }
        else
        {
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin)
    {
        if(isLogin)
        {
            SignIn.setVisibility(View.GONE);
            Name.setVisibility(View.VISIBLE);
            MainMenu.setVisibility(View.VISIBLE);
            SignOut.setVisibility(View.VISIBLE);

        }
        else
        {
            SignIn.setVisibility(View.VISIBLE);
            Name.setVisibility(View.GONE);
            MainMenu.setVisibility(View.GONE);
            SignOut.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_Code)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

}
