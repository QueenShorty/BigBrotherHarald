package com.estimote.blank;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.blank.Database.MainActivity;
import com.estimote.blank.Database.RequestHandler;
import com.estimote.blank.Database.Api;
import com.estimote.sdk.cloud.internal.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;
import static com.estimote.blank.Database.Api.URL_READ_USER_LOC;
import static com.estimote.blank.Database.Api.URL_CREATE_USER;

public class ManageUsers extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private Button AddUser, Delete1;
    private EditText UserNameToAdd;
    private TableRow Row1;
    private TableLayout UserTable;
    private TextView User1, Test;
    //ArrayList<String> userNames = new ArrayList<String>();

    RequestHandler ReqHandler = new RequestHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        /*User1 = (TextView)findViewById(R.id.txt_user1);
        Test = (TextView)findViewById(R.id.textView_test);
        Delete1 = (Button)findViewById(R.id.bn_deleteuser1);*/
        AddUser = (Button)findViewById(R.id.bn_addUser);
        UserTable = (TableLayout)findViewById(R.id.userTable);
        AddUser.setOnClickListener(this);

        getUsers();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bn_addUser:
                addUser();
                break;
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

        //Test.setText(data.toString());
        displayUsers(userNames);
    }

    public void displayUsers(ArrayList<String> userNames){
        int dp = (int) (getResources().getDimension(R.dimen.userTable_font_size) / getResources().getDisplayMetrics().density);
        final int size = userNames.size();
        for (int i = 0; i < size; i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setId(i);
            tableRow.setMinimumHeight(dp);

            final TextView textView = new TextView(this);
            textView.setId(i);
            textView.setText(userNames.get(i));
            textView.setWidth(900);
            textView.setHeight(150);
            textView.setTextSize(dp);
            tableRow.addView(textView);

            UserTable.addView(tableRow);

        }

    }


    public void addUser(){

        Intent switchToAddUser = new Intent(this, addUser.class);
        startActivity(switchToAddUser);

    }

    public void deleteUser(String USERID){
        ManageUsers.PerformNetworkRequest request = new ManageUsers.PerformNetworkRequest(Api.URL_DELETE_USER + USERID, null, CODE_GET_REQUEST);
        request.execute();
    }

    public void enableStrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static class PerformNetworkRequest extends AsyncTask<Void, Void, String>
    {
        String url;
        HashMap<String, String> params;
        int requestCode;
        private View progressBar;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    //Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    // refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

}



