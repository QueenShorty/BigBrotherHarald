package com.estimote.blank;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.estimote.blank.Database.Api;
import com.estimote.blank.Database.RequestHandler;
import com.estimote.blank.estimote.ProximityContentAdapter;
import com.estimote.blank.estimote.ProximityContentManager;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.blank.estimote.ProximityContentAdapter;
import com.estimote.blank.estimote.ProximityContentManager;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class Proximity extends AppCompatActivity {

    private ProximityContentManager proximityContentManager;
    private ProximityContentAdapter proximityContentAdapter;
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        //Build Proximity Observer, main object for performing observing

        getLoc();

    }

    public void getLoc(){
        EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials("proximity-beacons-1-5-iq9", "b625fd3a5a0e35ffc4c93756c403f50c");
        ProximityObserver proximityObserver = new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
                .withBalancedPowerMode()
                .withOnErrorAction(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        return null;
                    }
                })
                .build();

        //Defines zones for each beacons,
        //Zones will be divided into room with enter and exit features
        //Each zone will correlate to a beacon

        //candy beacon LocationOne = living_room

        ProximityZone venueZone =
                proximityObserver.zoneBuilder()
                        .forAttachmentKeyAndValue("locationOne", "living_room")
                        .inNearRange()
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override public Unit invoke(ProximityAttachment proximityAttachment) {
                                /* Do something here */
                                String zone = "Living Room";
                                String USERNAME = "Daniel";
                                String USERID = "567qer";
                                String PHONETYPE = "Android";
                                System.out.println("-----------------------------------------" + "Living Room");
                                updateUser(USERNAME, USERID, PHONETYPE, zone);
                                Log.d("app", "Welcome to the Living room!");
                                return null;
                            }
                        })
                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {
                                /* Do something here */
                                String zone = "Living Room";
                                String USERNAME = "Daniel";
                                String USERID = "567qer";
                                String PHONETYPE = "Android";
                                System.out.println("-----------------------------------------" + "Living Room");
                                updateUser(USERNAME, USERID, PHONETYPE, zone);
                                Log.d("app", "Bye bye, come to the living room again!");
                                return null;
                            }
                        })
                        .create();

        //Defining Proximity Zone 2
        //Correlates to the Lemon Beacon = kitchen

        ProximityZone venueZone2 =
                proximityObserver.zoneBuilder()
                        .forAttachmentKeyAndValue("locationTwo", "kitchen")
                        .inNearRange()
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override public Unit invoke(ProximityAttachment proximityAttachment) {
                                String zone = "Kitchen";
                                String USERNAME = "Daniel";
                                String USERID = "567qer";
                                String PHONETYPE = "Android";
                                System.out.println("-----------------------------------------" + "Kitchen");
                                updateUser(USERNAME, USERID, PHONETYPE, zone);
                                Log.d("app", "Welcome to the kitchen");
                                return null;
                            }
                        })
                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {
                                String zone = "Kitchen";
                                String USERNAME = "Daniel";
                                String USERID = "567qer";
                                String PHONETYPE = "Android";
                                String USERLOCATION = zone;
                                updateUser(USERNAME, USERID, PHONETYPE, USERLOCATION);
                                Log.d("app", "Cya your'e leaving the kitchen");
                                return null;
                            }
                        })
                        .create();

        //Defining Proximity Zone 3
        //Correlates to the beetroom beacon = Gym

        ProximityZone venueZone3 =
                proximityObserver.zoneBuilder()
                        .forAttachmentKeyAndValue("locationThree", "gym")
                        .inNearRange()
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override public Unit invoke(ProximityAttachment proximityAttachment) {
                                String zone = "Bedroom";
                                String USERNAME = "Daniel";
                                String USERID = "567qer";
                                String PHONETYPE = "Android";
                                System.out.println("-----------------------------------------" + "Bedroom");
                                updateUser(USERNAME, USERID, PHONETYPE, zone);
                                Log.d("app", "Start lifting");
                                return null;
                            }
                        })
                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {
                                String zone = "Bedroom";
                                String USERNAME = "Daniel";
                                String USERID = "567qer";
                                String PHONETYPE = "Android";
                                System.out.println("-----------------------------------------" + "Bedroom");
                                updateUser(USERNAME, USERID, PHONETYPE, zone);
                                Log.d("app", "Wobble bye");
                                return null;
                            }
                        })
                        .create();



        proximityContentAdapter = new ProximityContentAdapter(this);
        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(proximityContentAdapter);


        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        new Function0<Unit>() {
                            @Override
                            public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                startProximityContentManager();
                                return null;
                            }
                        },
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override
                            public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });

        //
        ProximityObserver.Handler observationHandler =
                proximityObserver
                        .addProximityZone(venueZone)
                        .addProximityZone(venueZone2)
                        .addProximityZone(venueZone3)
                        .start();


    }

    private void startProximityContentManager() {
        proximityContentManager = new ProximityContentManager(this, proximityContentAdapter, ((MyApplication) getApplication()).cloudCredentials);
        proximityContentManager.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (proximityContentManager != null)
            proximityContentManager.stop();
    }

    private void updateUser(String USERNAME,String USERID,String PHONETYPE, String USERLOCATION) //At this point
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("USERNAME", USERNAME);
        params.put("USERID", USERID);
        params.put("PHONETYPE", PHONETYPE);
        params.put("USERLOCATION", USERLOCATION);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + USERLOCATION);


        Proximity.PerformNetworkRequest request = new Proximity.PerformNetworkRequest(Api.URL_UPDATE_USER, params, CODE_POST_REQUEST);
        request.execute();

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

