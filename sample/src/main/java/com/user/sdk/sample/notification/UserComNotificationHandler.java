package com.user.sdk.sample.notification;

import android.support.annotation.NonNull;

import com.user.sdk.notification.UserComNotification;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

public class UserComNotificationHandler {
    private static final UserComNotificationHandler INSTANCE = new UserComNotificationHandler();

    private final ReplaySubject<UserComNotification> notifications = ReplaySubject.create();

    public static UserComNotificationHandler getInstance() {
        return INSTANCE;
    }

    public void putNotification(@NonNull UserComNotification notification) {
        notifications.onNext(notification);
    }

    public Observable<UserComNotification> getNotifications() {
        return notifications;
    }
}
