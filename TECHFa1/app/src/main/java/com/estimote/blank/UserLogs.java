package com.estimote.blank;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.blank.Database.Api;
import com.estimote.blank.Database.RequestHandler;
import com.estimote.sdk.cloud.internal.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.estimote.blank.Database.Api.URL_GET_TIME;

public class UserLogs extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TableLayout LogsTable;
    private Spinner spinnerUsers;
    private Button SortButton;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    RequestHandler ReqHandler = new RequestHandler();

    public ArrayList<String> userLocations = new ArrayList<String>();
    public ArrayList<String> userNames = new ArrayList<String>();
    public ArrayList<String> userTimes = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logs);

        LogsTable = (TableLayout)findViewById(R.id.table_Logs);
        spinnerUsers = (Spinner) findViewById(R.id.users_spinner);
        SortButton = (Button) findViewById(R.id.bn_sort);
        SortButton.setOnClickListener(this);

        readUser();
        getData();
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, userNames);
        spinnerUsers.setAdapter(adp);

        System.out.println(userNames);

        displayTable(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bn_sort:
                String Name = spinnerUsers.getSelectedItem().toString();
                LogsTable.removeAllViews();
                displayTable(Name);
                break;
        }
    }

    public void getData() {
        enableStrictMode();
        JSONObject data = null;
        String testString = "";

        try {
            data = new JSONObject(ReqHandler.sendGetRequest(URL_GET_TIME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("+++++++++++++____________+++++++++++++++");
        System.out.println("+++++++++++++____________+++++++++++++++");
        System.out.println(data);
        System.out.println("+++++++++++++____________+++++++++++++++");
        System.out.println("+++++++++++++____________+++++++++++++++");

        try {
            testString = data.getString("CTIME");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonarray = null;

        try {
            jsonarray = new JSONArray(testString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String username = "";
        String location = "";
        String times = "";

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = null;
            try {
                jsonobject = jsonarray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                username = jsonobject.getString("USER");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {

                if (jsonobject.getString("ROOM").isEmpty()) {
                    location = "Unknown";

                } else {
                    location = jsonobject.getString("ROOM");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                times = jsonobject.getString("CLOCK");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            userNames.add(username);
            userLocations.add(location);
            userTimes.add(times);
        }
    }

    public void displayTable(String name) {
        int dp = (int) (getResources().getDimension(R.dimen.userTable_font_size) / getResources().getDisplayMetrics().density);
        System.out.println(name);
        if(name == null)
        {

            for (int i = 0; i < userNames.size(); i++) {

                TableRow tableRow = new TableRow(this);
                tableRow.setId(i);
                //USERNAMES
                TextView textView1 = new TextView(this);
                TextView textView2 = new TextView(this);
                TextView textView3 = new TextView(this);
                textView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textView2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textView3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                textView1.setText(userNames.get(i));
                textView2.setText(userLocations.get(i));
                textView3.setText(userTimes.get(i));

                textView1.setId(i);
                textView2.setId(i + userNames.size());
                textView3.setId(i + (userNames.size() * 2));

                textView1.setTextSize(dp);
                textView2.setTextSize(dp);
                textView3.setTextSize(dp);

                tableRow.addView(textView1);
                tableRow.addView(textView2);
                tableRow.addView(textView3);

                LogsTable.addView(tableRow);
            }
        }
        else{
            for (int i = 0; i < userNames.size(); i++) {
                if (name == userNames.get(i)) {
                    TableRow tableRow = new TableRow(this);
                    tableRow.setId(i);
                    //USERNAMES
                    TextView textView1 = new TextView(this);
                    TextView textView2 = new TextView(this);
                    TextView textView3 = new TextView(this);
                    textView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    textView2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    textView3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                    textView1.setText(userNames.get(i));
                    textView2.setText(userLocations.get(i));
                    textView3.setText(userTimes.get(i));

                    textView1.setId(i);
                    textView2.setId(i + userNames.size());
                    textView3.setId(i + (userNames.size() * 2));

                    textView1.setTextSize(dp);
                    textView2.setTextSize(dp);
                    textView3.setTextSize(dp);

                    tableRow.addView(textView1);
                    tableRow.addView(textView2);
                    tableRow.addView(textView3);

                    LogsTable.addView(tableRow);
                }
            }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void readUser() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_GET_TIME, null, CODE_GET_REQUEST);
        request.execute();
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String>
    {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
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

    public void enableStrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
