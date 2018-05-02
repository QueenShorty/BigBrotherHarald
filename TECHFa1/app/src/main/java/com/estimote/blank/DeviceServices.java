package com.estimote.blank;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.estimote.blank.Database.Api;
import com.estimote.blank.Database.MainActivity;
import com.estimote.blank.Database.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;
import static com.estimote.blank.Database.Api.URL_READ_USER_LOC;

public class DeviceServices extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    private TableLayout Locations;
    private Button Refresh;
    RequestHandler ReqHandler = new RequestHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_services);
        String name = getIntent().getStringExtra("USER_NAME");

        Locations = (TableLayout)findViewById(R.id.DeviceLocationsTable);
        Refresh = (Button)findViewById(R.id.bn_refresh);
        Refresh.setOnClickListener(this);
        /*Scan = (Button)findViewById(R.id.bn_scanButton);
        Spinner = (ProgressBar)findViewById(R.id.loading_spinner);
        Spinner.setVisibility(GONE);
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        Scan.setOnClickListener(this);*/
        //Last = (TableRow)findViewById(R.id.last_table_row);
       // Last.setVisibility(GONE);

        getData();
        updateUser();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            /*case R.id.bn_scanButton:
                spinnerVisible();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        Spinner.setVisibility(GONE);
                        //Last.setVisibility(View.VISIBLE);
                    }
                }, 3000);
                break;*/
            case R.id.bn_refresh:
                updateUI();
                break;
        }

    }



    public void getData()
    {
        enableStrictMode();
        JSONObject data = null;
        String testString = "";
        ArrayList<String> userLocations = new ArrayList<String>();
        ArrayList<String> userNames = new ArrayList<String>();
        ArrayList<String> userPhoneTypes = new ArrayList<String>();
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
        String location = "";
        String phonetype = "";
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
            try {

                if (jsonobject.getString("USERLOCATION").isEmpty())
                {
                    location = "Unknown";

                }
                else
                {

                    location = jsonobject.getString("USERLOCATION");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                phonetype = jsonobject.getString("PHONETYPE");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            userLocations.add(location);
            userNames.add(username);
            userPhoneTypes.add(phonetype);

        }

        displayTable(userNames, userLocations, userPhoneTypes);
    }

    public void displayTable(ArrayList<String> userNames, ArrayList<String> location, ArrayList<String> phonetypes){
        int dp = (int) (getResources().getDimension(R.dimen.userTable_font_size) / getResources().getDisplayMetrics().density);
        int headerSize = (int) (getResources().getDimension(R.dimen.locationTable_header_size) / getResources().getDisplayMetrics().density);
        String userAndDevice = "";
        //TABLE HEADERS
        TableRow columnHeaders = new TableRow(this);
        TextView txtDevice = new TextView(this);
        TextView txtLocation = new TextView(this);
        txtDevice.setText("Device");
        txtLocation.setText("Location");
        txtDevice.setTextSize(headerSize);
        txtLocation.setTextSize(headerSize);
        txtDevice.setTypeface(txtDevice.getTypeface(), Typeface.BOLD);
        txtLocation.setTypeface(txtLocation.getTypeface(), Typeface.BOLD);
        columnHeaders.addView(txtDevice);
        columnHeaders.addView(txtLocation);
        Locations.addView(columnHeaders);
        for (int i = 0; i < userNames.size(); i++) {

            TableRow tableRow = new TableRow(this);
            tableRow.setId(i);
            tableRow.setMinimumHeight(dp);
            //USERNAMES
            TextView textView1 = new TextView(this);
            TextView textView2 = new TextView(this);



            textView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textView2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            userAndDevice = userNames.get(i) + "'s " + phonetypes.get(i);
            textView1.setText(userAndDevice);
            textView2.setText(location.get(i));
            textView1.setId(i);
            System.out.println(userNames.get(i));
            textView1.setWidth(900);
            textView1.setTextSize(dp);
            textView2.setId(i + userNames.size());
            System.out.println(location.get(i));
            textView2.setWidth(900);
            textView2.setTextSize(dp);
            tableRow.addView(textView1);
            tableRow.addView(textView2);

            Locations.addView(tableRow);
        }
    }

    public void updateUI(){
        Locations.removeAllViews();
        getData();
    }
   /* protected void spinnerVisible() {
        Spinner.setVisibility(View.VISIBLE);
        Spinner.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
    }*/

    public void enableStrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void updateUser() //At this point
    {
        String USERNAME = "Bob";
        String USERID = "edb3";
        String PHONETYPE = "ipad";
        String USERLOCATION = "Jupiter";

        HashMap<String, String> params = new HashMap<>();
        params.put("USERNAME", USERNAME);
        params.put("USERID", USERID);
        params.put("PHONETYPE", PHONETYPE);
        params.put("USERLOCATION", USERLOCATION);


        DeviceServices.PerformNetworkRequest request = new DeviceServices.PerformNetworkRequest(Api.URL_UPDATE_USER, params, CODE_POST_REQUEST);
        request.execute();
        updateUI();
    }

    public static class PerformNetworkRequest extends AsyncTask<Void, Void, String>
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

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