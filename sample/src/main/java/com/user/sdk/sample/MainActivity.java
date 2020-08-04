package com.user.sdk.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.user.sdk.UserCom;
import com.user.sdk.events.ProductEventType;
import com.user.sdk.events.ScreenName;
import com.user.sdk.events.TrackArguments;
import com.user.sdk.preloadContent.PreloadContent;
import com.user.sdk.preloadContent.PreloadContentCallback;

import java.util.HashMap;
import java.util.Map;


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

        findViewById(R.id.btn_preload_content_show).setOnClickListener(__ -> {
            UserCom.getInstance().showPreloadContent("example_content", new PreloadContentCallback() {
                @Override
                public void onSuccess(PreloadContent content) {
                    Toast.makeText(MainActivity.this, content.getContent(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Failed to get the content", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, throwable.getLocalizedMessage());
                }
            });
        });

        findViewById(R.id.btn_preload_content_fetch).setOnClickListener(__ -> {
            UserCom.getInstance().fetchPreloadContent("example_content", new PreloadContentCallback() {
                @Override
                public void onSuccess(PreloadContent content) {
                    Toast.makeText(MainActivity.this, content.getContent(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Failed to get the content", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, throwable.getLocalizedMessage());
                }
            });
        });

        findViewById(R.id.btn_send_product_event).setOnClickListener(__ -> {
            Map<String, Object> eventBody = new HashMap<>();
            eventBody.put("product_rating", 0);
            eventBody.put("custom_data", "some data");
            UserCom.getInstance().sendProductEvent("MY_PRODUCT_ID", ProductEventType.ADD_TO_CART, eventBody);
        });

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
