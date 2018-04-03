package com.estimote.blank;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;

public class ManageUsers extends AppCompatActivity implements View.OnClickListener {

    private Button AddUser, Delete1, Delete2, Delete3, Delete4, Delete5, Delete6, Delete7;
    private TableRow Row1, Row2, Row3, Row4, Row5, Row6, Row7;
    private TableLayout UserTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        Delete1 = (Button)findViewById(R.id.bn_deleteuser1);
        Delete2 = (Button)findViewById(R.id.bn_deleteuser2);
        Delete3 = (Button)findViewById(R.id.bn_deleteuser3);
        Delete4 = (Button)findViewById(R.id.bn_deleteuser4);
        Delete5 = (Button)findViewById(R.id.bn_deleteuser5);
        Delete6 = (Button)findViewById(R.id.bn_deleteuser6);
        Delete7 = (Button)findViewById(R.id.bn_deleteuser7);
        AddUser = (Button)findViewById(R.id.bn_addUser);
        UserTable = (TableLayout)findViewById(R.id.userTable);
        Row1 = (TableRow)findViewById(R.id.row1);
        Row2 = (TableRow)findViewById(R.id.row2);
        Row3 = (TableRow)findViewById(R.id.row3);
        Row4 = (TableRow)findViewById(R.id.row4);
        Row5 = (TableRow)findViewById(R.id.row5);
        Row6 = (TableRow)findViewById(R.id.row6);
        Row7 = (TableRow)findViewById(R.id.row7);
        Delete1.setOnClickListener(this);
        Delete2.setOnClickListener(this);
        Delete3.setOnClickListener(this);
        Delete4.setOnClickListener(this);
        Delete5.setOnClickListener(this);
        Delete6.setOnClickListener(this);
        Delete7.setOnClickListener(this);
        AddUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bn_addUser:
                //addUser();
                break;
            case R.id.bn_deleteuser1:
                Delete1.setVisibility(GONE);
                UserTable.removeView(Row1);
                break;
            case R.id.bn_deleteuser2:
                Delete2.setVisibility(GONE);
                UserTable.removeView(Row2);
                break;
            case R.id.bn_deleteuser3:
                Delete3.setVisibility(GONE);
                UserTable.removeView(Row3);
                break;
            case R.id.bn_deleteuser4:
                Delete4.setVisibility(GONE);
                UserTable.removeView(Row4);
                break;
            case R.id.bn_deleteuser5:
                Delete5.setVisibility(GONE);
                UserTable.removeView(Row5);
                break;
            case R.id.bn_deleteuser6:
                Delete6.setVisibility(GONE);
                UserTable.removeView(Row6);
                break;
            case R.id.bn_deleteuser7:
                Delete7.setVisibility(GONE);
                UserTable.removeView(Row7);
                break;
        }
    }


}
