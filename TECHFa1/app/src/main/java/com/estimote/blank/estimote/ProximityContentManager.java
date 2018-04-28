package com.estimote.blank.estimote;

import android.content.Context;
import android.util.Log;

import com.estimote.internal_plugins_api.cloud.CloudCredentials;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.proximity.ProximityAttachment;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class ProximityContentManager {

    private Context context;
    private ProximityContentAdapter proximityContentAdapter;
    private CloudCredentials cloudCredentials;
    private ProximityObserver.Handler proximityObserverHandler;

    public ProximityContentManager(Context context, ProximityContentAdapter proximityContentAdapter, CloudCredentials cloudCredentials) {
        this.context = context;
        this.proximityContentAdapter = proximityContentAdapter;
        this.cloudCredentials = cloudCredentials;
    }

    public void start() {

        ProximityObserver proximityObserver = new ProximityObserverBuilder(context, (EstimoteCloudCredentials) cloudCredentials)
                .withOnErrorAction(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        Log.e("app", "proximity observer error: " + throwable);
                        return null;
                    }
                })
                .withBalancedPowerMode()
                .build();

        ProximityZone zone = proximityObserver.zoneBuilder()
                .forAttachmentKeyAndValue("proximity-beacons-1-5-iq9", "example-proximity-zone")
                .inCustomRange(3.0)
                .withOnChangeAction(new Function1<List<? extends ProximityAttachment>, Unit>() {
                    @Override
                    public Unit invoke(List<? extends ProximityAttachment> attachments) {

                        List<ProximityContent> nearbyContent = new ArrayList<>(attachments.size());

                        for (ProximityAttachment attachment : attachments) {
                            String title = attachment.getPayload().get("proximity-beacons-1-5-iq9/title");
                            if (title == null) {
                                title = "unknown";
                            }
                            String subtitle = Utils.getShortIdentifier(attachment.getDeviceId());

                            nearbyContent.add(new ProximityContent(title, subtitle));
                        }

                        proximityContentAdapter.setNearbyContent(nearbyContent);
                        proximityContentAdapter.notifyDataSetChanged();

                        return null;
                    }
                })
                .create();
        proximityObserver.addProximityZone(zone);

        proximityObserverHandler = proximityObserver.start();
    }

    public void stop() {
        proximityObserverHandler.stop();
    }
}
