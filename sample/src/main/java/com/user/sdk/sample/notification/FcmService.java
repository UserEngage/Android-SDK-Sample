package com.user.sdk.sample.notification;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.user.sdk.UserCom;
import com.user.sdk.notification.UserComNotification;

/**
 * Created by michalik on 28.08.17
 */
public class FcmService extends FirebaseMessagingService {
    public static final String TAG = FcmService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: "+remoteMessage.getData());
        UserCom.getInstance().onNotification(getApplicationContext(), remoteMessage);
        @Nullable UserComNotification notification = UserComNotification.create(remoteMessage.getData());
        if (notification != null) UserComNotificationHandler.getInstance().putNotification(notification);
    }
}
