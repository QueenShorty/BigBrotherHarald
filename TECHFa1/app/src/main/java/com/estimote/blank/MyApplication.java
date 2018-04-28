package com.estimote.blank;

import android.app.Application;

import com.estimote.internal_plugins_api.cloud.CloudCredentials;
import com.estimote.proximity_sdk.proximity.EstimoteCloudCredentials;
import com.estimote.sdk.EstimoteSDK;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MyApplication extends Application {

    public CloudCredentials cloudCredentials =
            new EstimoteCloudCredentials("proximity-beacons-1-5-iq9", "b625fd3a5a0e35ffc4c93756c403f50c");

    @Override
    public void onCreate() {
        super.onCreate();

        /** TODO: Replace with your App ID and App Token.
         You can get them by adding a new app at https://cloud.estimote.com/#/apps
         */
        EstimoteSDK.initialize(getApplicationContext(), "t-e-c-h-fa1", "72248985f5d416995fa2537a2d4f82e5");

        // uncomment to enable debug-level logging
        // it's usually only a good idea when troubleshooting issues with the Estimote SDK
//        EstimoteSDK.enableDebugLogging(true);
    }


}
