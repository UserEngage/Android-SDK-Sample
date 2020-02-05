package com.user.sdk.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.user.sdk.UserCom;
import com.user.sdk.events.ScreenName;
import com.user.sdk.events.TrackArguments;


/**
 * Created by michalik on 24.08.17
 */
@ScreenName(name = "main_activity")
@TrackArguments
public class MainActivity extends Activity {

    public static final String INTENT_KEY1 = "INTENT_KEY1";
    public static final String INTENT_KEY2 = "INTENT_KEY2";

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        sendUserComEvents();
        findViewById(R.id.btn_fcm).setOnClickListener(__ -> startActivity(new Intent(this, FcmTestActivity.class)));
        findViewById(R.id.btn_fragment).setOnClickListener(__ -> startActivity(new Intent(this, SimpleFragmentActivity.class)));
        findViewById(R.id.btn_signout).setOnClickListener(__ -> logout());
        super.onCreate(savedInstanceState);
    }

    private void sendUserComEvents() {
        UserCom.getInstance().sendEvent(new MyCustomEvent());
    }

    private void logout() {
        UserCom.getInstance().logout();
        finish();
    }
}
