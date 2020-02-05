package com.user.sdk.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.user.sdk.events.ScreenName;
import com.user.sdk.events.TrackArguments;

/**
 * Created by jaroslawmichalik on 03.12.2017
 */

@ScreenName(name = "example_activity")
@TrackArguments
public class NotificationResultActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
    }
}
