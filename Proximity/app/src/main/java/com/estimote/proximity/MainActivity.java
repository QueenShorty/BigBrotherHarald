package com.estimote.proximity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity.estimote.ProximityContentAdapter;
import com.estimote.proximity.estimote.ProximityContentManager;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;

import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MainActivity extends AppCompatActivity {

    private ProximityContentManager proximityContentManager;
    private ProximityContentAdapter proximityContentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Build Proximity Observer, main object for performing observing

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
                        .inFarRange()
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override public Unit invoke(ProximityAttachment proximityAttachment) {
                                /* Do something here */
                                Log.d("app", "Welcome to the Living room!");
                                return null;
                            }
                        })
                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {
                                /* Do something here */
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
                        .inFarRange()
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override public Unit invoke(ProximityAttachment proximityAttachment) {

                                Log.d("app", "Welcome to the kitchen");
                                return null;
                            }
                        })
                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {

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
                        .inFarRange()
                        .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                            @Override public Unit invoke(ProximityAttachment proximityAttachment) {

                                Log.d("app", "Start lifting");
                                return null;
                            }
                        })
                        .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                            @Override
                            public Unit invoke(ProximityAttachment proximityAttachment) {

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

}
