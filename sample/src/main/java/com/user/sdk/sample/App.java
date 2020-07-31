package com.user.sdk.sample;

import android.app.Application;
import android.graphics.Color;
import android.support.customtabs.CustomTabsIntent;

import com.user.sdk.UserCom;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new UserCom.Builder(
                this,
                "mobile_sdk_key", //your mobile sdk key generated in User.com webpanel details
                "integrations_api_key", // your api secret key from User.com webpanel under Settings -> Setup & Integration
                "https://<your_app_subdomain>.user.com/"
        )
                .trackAllActivities(true)
                .openLinksInChromeCustomTabs(true)
                .build();
    }
}
