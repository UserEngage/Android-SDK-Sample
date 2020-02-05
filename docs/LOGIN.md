# DEPRECATED documentation - no longer actively maintained
This doc has been preserved to give you some details but some of them
may be out of date so be sure to check specific behavior in code. 

## Register your user
Somewhere in your LoginActivity:
```java
class LoginActivity{
    private void registerUserComCustomer() {

        Customer customer = new Customer()
                .id("idfromyourdatabase") // id of your user
                .attr("email", "myemail@example.com")
                .attr("name", "John Doe")
                .attr("loyalCustomer", true)
                .attr("age", 33)
                .withDeviceInfo(this);

        UserCom.getInstance()
                .register(customer, new UserCom.RegisterCallback() {
                    @Override
                    public void onSuccess(RegisterResponse response) {
                        Log.d(TAG, "onSuccess: " + response);
                        // user is registered and now you can send events via SDK
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e(TAG, "onFailure: ", throwable);
                        // try again - something went wrong
                    }
                });
    }    
}
```

You can add any number of custom customer attributes, or use predefined, and combine them:
```java
    new Customer()
        .id("id")
        .firstName("John")
        .lastName("Doe")
        .email("myemail@example.com")
        .attr("custom_attribute", true);
```

By adding **withDeviceInfo(context)** you will get basic system and hardware information.

### Logout
To logout and clear all user-related resources withing SDK, invoke:
```java
UserCom.getInstance().logout();
```

## Setup push notifications

To configure Firebase Push Notifications, visit [https://firebase.google.com/docs/android/setup](https://firebase.google.com/docs/android/setup)

### Register token updates
```java
public class InstanceIdService extends FirebaseInstanceIdService {

    public static final String TAG = InstanceIdService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "onTokenRefresh: "+ FirebaseInstanceId.getInstance().getToken());
        UserCom.getInstance().updateFCMKey(FirebaseInstanceId.getInstance().getToken());
        super.onTokenRefresh();
    }
}
```

### Pass notifications to SDK:
```java
public class FcmService extends FirebaseMessagingService {
    public static final String TAG = FcmService.class.getSimpleName();

    public FcmService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: "+remoteMessage.getData());
        UserCom.getInstance().onNotification(remoteMessage.getData(), getApplicationContext());
    }
}
```

SDK will use only notifications containing UserCom TAG, 
so if you have already FCM in your project, 
SDK will not interfere with existing notifications.
