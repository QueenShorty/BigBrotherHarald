package com.estimote.blank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class Proximity extends AppCompatActivity
{
    private ProximityObserver proximityObserver;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add this:
        EstimoteCloudCredentials  cloudCredentials =
                new EstimoteCloudCredentials("beacontest-cyv", "ff9c0d860c99516bdbbe5300bc992146");

        // 2. Create the Proximity Observer
        ProximityObserver proximityObserver = new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
                .withOnErrorAction(new Function1<Throwable, Unit>() {
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .build();
        // 3. set proximity zone
        ProximityZone zone1 = this.proximityObserver.zoneBuilder()
                .forAttachmentKeyAndValue("floor", "1st")
                .inFarRange()
                .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment attachment) {
                        Log.d("app", "Welcome to the 1st floor");
                        return null;
                    }
                })
                .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment attachment) {
                        Log.d("app", "Bye bye, come visit us again on the 1st floor");
                        return null;
                    }
                })
                .create();
        this.proximityObserver.addProximityZone(zone1);

        //This if we want a message for each beacon.

//        ProximityZone zone2 = this.proximityObserver.zoneBuilder()
//                .forAttachmentKeyAndValue("location", "desk")
//                .inNearRange() or .inCustomRange(3.0)
//                .withOnChangeAction(new Function1<List<? extends ProximityAttachment>, Unit>() {
//                    @Override
//                    public Unit invoke(List<? extends ProximityAttachment> attachments) {
//                        List<String> desks = new ArrayList<>();
//                        for (ProximityAttachment attachment : attachments) {
//                            desks.add(attachment.getPayload().get("desk_owner"));
//                        }
//                        Log.d("app", "Nearby desks: " + desks);
//                        return null;
//                    }
//                })
//                .create();
//        this.proximityObserver.addProximityZone(zone2);
    }

}
