package com.estimote.proximity;

import android.app.Application;

import com.estimote.internal_plugins_api.cloud.CloudCredentials;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MyApplication extends Application {

    public CloudCredentials cloudCredentials =
            new EstimoteCloudCredentials("proximity-beacons-1-5-iq9", "b625fd3a5a0e35ffc4c93756c403f50c");
}
