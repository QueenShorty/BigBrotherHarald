package com.estimote.blank;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableRow;

import static android.view.View.GONE;

public class DeviceServices extends AppCompatActivity implements View.OnClickListener {

    private Button Scan;
    private TableRow Last;
    private ProgressBar Spinner;
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_services);
        String name = getIntent().getStringExtra("USER_NAME");

        Scan = (Button)findViewById(R.id.bn_scanButton);
        Spinner = (ProgressBar)findViewById(R.id.loading_spinner);
        Spinner.setVisibility(GONE);
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        Scan.setOnClickListener(this);
        Last = (TableRow)findViewById(R.id.last_table_row);
        Last.setVisibility(GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bn_scanButton:
                Spinner.setVisibility(View.VISIBLE);
                Spinner.animate()
                        .alpha(1f)
                        .setDuration(mShortAnimationDuration)
                        .setListener(null);
                Spinner.setVisibility(GONE);
                //Last.setVisibility(View.VISIBLE);
                break;
        }

    }


}
