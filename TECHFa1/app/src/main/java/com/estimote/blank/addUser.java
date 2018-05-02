package com.estimote.blank;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.estimote.blank.Database.Api;
import com.estimote.blank.Database.MainActivity;
import com.estimote.blank.Database.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

import static android.view.View.GONE;
import static com.estimote.blank.Database.Api.URL_READ_USER_LOC;

public class addUser extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextName, editTextDevice;
    ProgressBar progressBar;
    Button buttonAddUpdate, DeleteUser;
    Spinner SpinDeleteUser, SpinDevice;
    ArrayList<String> userIDs = new ArrayList<>();
    ArrayList<String> userNames = new ArrayList<>();


    RequestHandler ReqHandler = new RequestHandler();

    String myDeviceModel = android.os.Build.MODEL;
    String[] deviceSpinner = new String[] {
            myDeviceModel
    };

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDevice = (EditText) findViewById(R.id.editTextDevice);
        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);
        SpinDeleteUser = (Spinner) findViewById(R.id.spinner_DeleteUser);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        DeleteUser = (Button) findViewById(R.id.bn_Delete_User);
        SpinDevice = (Spinner) findViewById(R.id.spinner_Device);

        buttonAddUpdate.setOnClickListener(this);
        DeleteUser.setOnClickListener(this);
        userIDs = getUsers("USERID");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println(userIDs);
        System.out.println("=========================");
        System.out.println("=========================");


        userNames = getUsers("USERNAMES");
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, userNames);
        ArrayAdapter<String> deviceadp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, deviceSpinner);
        SpinDeleteUser.setAdapter(adp);
        SpinDevice.setAdapter(deviceadp);


        readUser();
    }


    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.buttonAddUpdate:
                createUser();
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextName.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(editTextDevice.getWindowToken(), 0);
                editTextDevice.setText("");
                editTextName.setText("");
                break;
            case R.id.bn_Delete_User:
                String USERID = "";
                String USERNAME = SpinDeleteUser.getSelectedItem().toString();
                for (int i = 0; i < userIDs.size(); i++)
                {
                    if (USERNAME == userNames.get(i))
                    {
                        USERID = userIDs.get(i);
                    }
                }
                deleteUser(USERID);
                break;
        }
    }

    public void updateUI(){
        userIDs = getUsers("USERID");
        userNames = getUsers("USERNAMES");
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, userNames);
        ArrayAdapter<String> deviceadp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, deviceSpinner);
        SpinDeleteUser.setAdapter(adp);
        SpinDevice.setAdapter(deviceadp);
    }

    public ArrayList<String> getUsers(String type)
    {
        enableStrictMode();
        JSONObject data = null;
        String testString = "";
        ArrayList<String> userIDs = new ArrayList<String>();
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
        String userid = "";
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
                userid = jsonobject.getString("USERID");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                username = jsonobject.getString("USERNAME");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            userIDs.add(userid);
            userNames.add(username);
        }

        //Test.setText(data.toString());
        if (type == "USERID")
        {
            return userIDs;
        }
        else
        {
            System.out.println(userNames);
            return userNames;
        }
    }

    private void createUser()
    {

        String USERNAME = editTextName.getText().toString().trim();
        String PHONETYPE = editTextDevice.getText().toString().trim();
        String USERID = "";
        RandomString temp = new RandomString(4, ThreadLocalRandom.current());
        USERID = temp.toString().trim();
        USERID = USERID.substring(USERID.length() - 4);

        //validating the inputs
        if (TextUtils.isEmpty(USERNAME)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(PHONETYPE));
        {
            PHONETYPE = SpinDevice.getSelectedItem().toString();
        }


        //if validation passes
        HashMap<String, String> params = new HashMap<>();
        params.put("USERNAME", USERNAME);
        params.put("USERID", USERID);
        params.put("PHONETYPE", PHONETYPE);

        //Calling the create USER API
        addUser.PerformNetworkRequest request = new addUser.PerformNetworkRequest(Api.URL_CREATE_USER, params, CODE_POST_REQUEST);
        request.execute();
        updateUI();

    }

    private void deleteUser(String USERID)
    {
        addUser.PerformNetworkRequest request = new addUser.PerformNetworkRequest(Api.URL_DELETE_USER + USERID, null, CODE_GET_REQUEST);
        request.execute();
        updateUI();
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
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

    private void readUser()
    {
        addUser.PerformNetworkRequest request = new addUser.PerformNetworkRequest(Api.URL_READ_USER_LOC, null, CODE_GET_REQUEST);
        request.execute();
    }

    public class RandomString {

        /**
         * Generate a random string.
         */
        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

        public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public final String lower = upper.toLowerCase(Locale.ROOT);

        public static final String digits = "0123456789";

        public static final String alphanum = upper + digits;

        private final Random random;

        private final char[] symbols;

        private final char[] buf;

        public RandomString(int length, Random random, String symbols) {
            if (length < 1) throw new IllegalArgumentException();
            if (symbols.length() < 2) throw new IllegalArgumentException();
            this.random = Objects.requireNonNull(random);
            this.symbols = symbols.toCharArray();
            this.buf = new char[length];
        }

        /**
         * Create an alphanumeric string generator.
         */
        public RandomString(int length, Random random) {
            this(length, random, alphanum);
        }

        /**
         * Create an alphanumeric strings from a secure generator.
         */
        public RandomString(int length) {
            this(length, new SecureRandom());
        }

        /**
         * Create session identifiers.
         */
        public RandomString() {
            this(21);
        }

    }

    public void enableStrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
