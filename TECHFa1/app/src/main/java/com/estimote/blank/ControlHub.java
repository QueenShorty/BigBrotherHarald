package com.estimote.blank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.view.View.GONE;

public class ControlHub extends AppCompatActivity implements View.OnClickListener {

    private Button AddDevice, Delete1, Delete2, Delete3, Delete4;
    private TextView Room1, Room2, Room3, Room4;
    private TableLayout RoomTable;
    private TableRow Row1, Row2, Row3, Row4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlhub);

        AddDevice = (Button)findViewById(R.id.bn_addRoom);
        RoomTable = (TableLayout)findViewById(R.id.tableLayout2);
        Delete1 = (Button)findViewById(R.id.bn_delete1);
        Delete2 = (Button)findViewById(R.id.bn_delete2);
        Delete3 = (Button)findViewById(R.id.bn_delete3);
        Delete4 = (Button)findViewById(R.id.bn_delete4);
        Row1 = (TableRow)findViewById(R.id.row1);
        Row2 = (TableRow)findViewById(R.id.row2);
        Row3 = (TableRow)findViewById(R.id.row3);
        Row4 = (TableRow)findViewById(R.id.row4);
        AddDevice.setOnClickListener(this);
        Delete1.setOnClickListener(this);
        Delete2.setOnClickListener(this);
        Delete3.setOnClickListener(this);
        Delete4.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bn_addRoom:
                break;
            case R.id.bn_delete1:
                Delete1.setVisibility(GONE);
                RoomTable.removeView(Row1);
                break;
            case R.id.bn_delete2:
                Delete2.setVisibility(GONE);
                RoomTable.removeView(Row2);
                break;
            case R.id.bn_delete3:
                Delete3.setVisibility(GONE);
                RoomTable.removeView(Row3);
                break;
            case R.id.bn_delete4:
                Delete4.setVisibility(GONE);
                RoomTable.removeView(Row4);
                break;
        }
    }

}
