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
                "4WYWkbBIszDKZf39bAAwgLfwVUYblWuIQ3gXWAYCibNhChZiXoWRZfNEmE91LaNQ", // your api secret key generated in panel
                "https://demo1.user.com" // User.com API url
        )
                .trackAllActivities(true)
                .openLinksInChromeCustomTabs(true)
                .build();
    }
}
