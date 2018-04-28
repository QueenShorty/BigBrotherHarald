package com.estimote.blank;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
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

import com.estimote.blank.Database.RequestHandler;
import com.estimote.blank.Database.Api;
import com.estimote.sdk.cloud.internal.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.view.View.GONE;
import static com.estimote.blank.Database.Api.URL_READ_USER_LOC;

public class ManageUsers extends AppCompatActivity implements View.OnClickListener {

    private Button AddUser, Delete1;
    private TableRow Row1;
    private TableLayout UserTable;
    private TextView User1, Test;
    //ArrayList<String> userNames = new ArrayList<String>();

    Api Api = new Api();
    RequestHandler ReqHandler = new RequestHandler();
    String ROOT_URL = Api.ROOT_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        /*User1 = (TextView)findViewById(R.id.txt_user1);
        Test = (TextView)findViewById(R.id.textView_test);
        Delete1 = (Button)findViewById(R.id.bn_deleteuser1);*/
        AddUser = (Button)findViewById(R.id.bn_addUser);
        UserTable = (TableLayout)findViewById(R.id.userTable);
        //Row1 = (TableRow)findViewById(R.id.row1);

        getUsers();
        //displayUsers(userNames);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            /*case R.id.bn_addUser:
                //addUser();
                break;
            case R.id.bn_deleteuser1:
                Delete1.setVisibility(GONE);
                UserTable.removeView(Row1);
                break;*/
        }
    }

    public void getUsers()
    {
        enableStrictMode();
        JSONObject data = null;
        String testString = "";
        ArrayList<String> userNames = new ArrayList<String>();
        try {
            data = new JSONObject(ReqHandler.sendGetRequest(URL_READ_USER_LOC));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            testString = data.getString("USERLOC");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonarray = null;
        String username = "";
        try {
            jsonarray = new JSONArray(testString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = null;
            try {
                jsonobject = jsonarray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                username = jsonobject.getString("USERNAME");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            userNames.add(username);
        }

        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println(userNames);
        System.out.println("=========================");
        System.out.println("=========================");
        //Test.setText(data.toString());
        displayUsers(userNames);
    }

    public void displayUsers(ArrayList<String> userNames){
        int dp = (int) (getResources().getDimension(R.dimen.userTable_font_size) / getResources().getDisplayMetrics().density);
        for (int i = 0; i < userNames.size(); i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setId(i);
            tableRow.setMinimumHeight(dp);

            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setText(userNames.get(i));
            System.out.println(userNames.get(i));
            textView.setWidth(900);
            textView.setHeight(150);
            textView.setTextSize(dp);
            tableRow.addView(textView);
            Button button = new Button(this);
            button.setText("Delete");
            button.setId(i);

            tableRow.addView(button);
            UserTable.addView(tableRow);

        }

    }

    /*public void getStuff()
    {
        enableStrictMode();
        JSONObject test = null;
        try {
            test = new JSONObject(ReqHandler.sendGetRequest(URL_READ_USER_LOC));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("=========================");
        System.out.println("=========================");
        try {
            System.out.println(test.getString("USERLOC"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("=========================");
        System.out.println("=========================");
        Test.setText(test.toString());
    }*/

    public void enableStrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

}



