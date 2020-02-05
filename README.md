# DEPRECATED documentation - no longer actively maintained
This doc has been preserved to give you some details but some of them
may be out of date so be sure to check specific behavior in code. 

# README

## Add UserCom SDK to your app:

Copy usercom-sdk.aar into your libs directory in app module.

In **top level build.gradle** file add:
```groovy
allprojects {
    repositories {
        …
        maven {
            url 'https://android-sdk.user.com'
        }
    }
}
```

Then in **app module build.gradle**:
```groovy
dependencies {
    implementation 'com.user:android-sdk:1.0.4'
}
```

## Configure SDK in your app:

In your Application root file initialize UserCom.Builder with your SDK API key, context and base URL. It will initialize UserCom instance.

If you want to track you client’s mobile view screens activities you also can append .trackAllActivities(true) like on the sample code below:

```java
public class App extends Application {

    public static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        new UserCom.Builder(
                this,
                "api_secret", //your api secret key generated in User.com webpanel details
                "https://<your_app_subdomain>.user.com"
        )
                .trackAllActivities(true)  // false by default
                .openLinksInChromeCustomTabs(true) // true by default
                .setCustomTabsBuilder(getCustomTabsBuilder())
                .build();
    }

    private static CustomTabsIntent.Builder getCustomTabsBuilder() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(Color.GREEN);
        return builder;
    }
}
```
## Register your user
User will be registered anonymously right after SDK initialization. To set specific user attributes, pass them using `UserCom.getInstance().register()` function. Usually, we are registering the client's information in `LoginActivitiy` when a client passes data in forms.

You should copy registerUserComCustomer() method e.g. for example to `LoginActivity`(if you want to get client's attribute in this activity) and then call it in method in which you want to retrieve client's information.

```java
class LoginActivity{
    private void registerUserComCustomer() {

        Customer customer = new Customer()
                .id("idfromyourdatabase") // id of your user
                .firstName("John")
                .lastName("Doe")
                .email("myemail@example.com")
                .attr("loyalCustomer", true)
                .attr("age", 33)

        UserCom.getInstance()
                .register(customer, new CustomerUpdateCallback() {
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

## Sending events
Once your user is logged, SDK will be able to send various events related to user and his actions.

### Predefined events
#### Context events
Context events are related to your app lifecycle, e.x orientation changes, using windowed mode or clicking UserCom notification.
Example:
```java
UserCom.getInstance().sendEvent(new ScreenProperties(getApplicationContext()));
```
This will trigger SDK to send event to UserCom:
```json
{
  "screen_height": 800,
  "screen_width": 400
}
```

#### Custom events:
You can define your custom events. Create class which implements UserComProperty:
```java
import com.user.sdk.events.UserComProperty;

import java.util.HashMap;
import java.util.Map;

class MyCustomEvent implements UserComProperty {

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> eventBody = new HashMap<>();
        eventBody.put("attr1", "value1");
        eventBody.put("attr2", 137);
        return eventBody;
    }
}
```
Where value in eventBody map is String, Integer, Double or Boolean. Only flat structures are supported.
When you invoke 
```java
UserCom.getInstance().sendEvent(myCustomEvent);
```
UserComSDK will automatically add your user identifier and date time informations. In UserCom web panel your event will appearr as MyCustomEvent (classname)

## Handling notifications

### If you will integrate FCM into the app for the first time follow the Firebase instructions:

[https://firebase.google.com/docs/android/setup](https://firebase.google.com/docs/android/setup)


### If you already have FCM in project just invoke in your messaging service:
```java
UserCom.getInstance().onNotification(remoteMessage.getData(), getApplicationContext());
```
And everything will be done automatically.

### Pass notification to SDK:
```java
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.user.sdk.UserCom;

public class FcmService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        UserCom.getInstance().onNotification(remoteMessage.getData(), getApplicationContext());
    }
}
```

### Launch screen from notification

#### Setup

Add to builder .notificationRoutes(list)

```java
public class App extends Application {

    private UserCom userCom;

    @Override
    public void onCreate() {
        super.onCreate();
        userCom = new UserCom.Builder()
                .context(this)
                .apiKey("key")
                .notificationRoutes(routesList)
                .trackActivities()
                .build();
    }
}
```

```java
List<NotificationAction> actionList = new ArrayList<>();
actionList.add(new NotificationAction(NotificationResultActivity.class, intentKeys));
```

Where `NotificationResultActivity.class` is target activity class to be launched from specific notification.

Now you can send push notification:

```json
{
  "to": "device_key",
  "data": {
    "id": 9234873249,
    "user_com_notification": "user_com_notification",
    "type": 3,
    "title": "Home to UserCom demo",
    "subtitle": "Hope you are doing well",
    "icon": "http://lorempixel.com/people/64/64",
    "screen_name": "NotificationResultActivity"
  }
}
```

Now if you launch application, send request via FCM and click notification at the top bar, 
UserComSDK will open NotificationResultActivity for you,
with the whole notification body as intent parameters.

If you use custom-annotated screen names

```java
@ScreenName(name = "main_activity")
public class MainActivity extends Activity{}
```

you should use **main_activity** as a "screen_name" value:

```json
{
  "to": "device_key",
  "data": {
    "id": 9234873249,
    "user_com_notification": "user_com_notification",
    "type": 3,
    "title": "Home to UserCom demo",
    "subtitle": "Hope you are doing well",
    "icon": "http://lorempixel.com/people/64/64",
    "screen_name": "main_activity"
  }
}
```

#### Customization
To customize launch screen behavior you can add various properties to "data" object in notification:

```json
{
  "to": "device_key",
  "data": {
    "id": 9234873249,
    "user_com_notification": "user_com_notification",
    "type": 3,
    "title": "Home to UserCom demo",
    "subtitle": "Hope you are doing well",
    "screen_name": "main_activity",
    "a": "a",
    "b": "b",
    "c": "c"
  }
}
```

Then in your activity you can retrieve these properties with **getIntent().getExtras() method:

```java
getIntent().getExtras().get("id");
getIntent().getExtras().get("a");
```

### Customize In-app message Dialog

#### Change dialog style
In-app dialog uses `User.com.InApp.Dialog` style so you can override it to customize dialog style.
Default implementation looks like:
```xml
<style name="User.com.InApp.Dialog" parent="Theme.AppCompat.Light.Dialog.Alert">
    <item name="buttonBarPositiveButtonStyle">@style/User.com.InApp.Dialog.Button.Positive</item>
    <item name="buttonBarNegativeButtonStyle">@style/User.com.InApp.Dialog.Button.Negative</item>
</style>
```
Remember that dialog doesn't use any application theme so attributes like `?attr/colorAccent` doesn't work.

##### Positive button
`User.com.InApp.Dialog.Button.Positive` style is used for positive button.
Default implementation looks like:
```xml
<style name="User.com.InApp.Dialog.Button.Positive" parent="Widget.AppCompat.Button.ButtonBar.AlertDialog">
    <item name="android:textColor">@color/user_com_in_app_message_dialog_positive</item>
</style>
```
If you want to simple change button color just override `user_com_in_app_message_dialog_positive` color:
```xml
<color name="user_com_in_app_message_dialog_positive">@color/colorAccent</color>
```

##### Negative button
`User.com.InApp.Dialog.Button.Negative` is style for optional dismiss button.
Default implementation looks like:
```xml
<style name="User.com.InApp.Dialog.Button.Negative" parent="Widget.AppCompat.Button.ButtonBar.AlertDialog">
    <item name="android:textColor">?android:textColorSecondary</item>
</style>
``` 

#### Change dismiss text
When sdk received in-app message with `action_button_link` then dialog will show
default _"Cancel"_ dismiss button. To change it's name or add localization just override
`user_com_in_app_message_cancel_button` string resource, eg.
```xml
<string name="user_com_in_app_message_cancel_button">Dismiss</string>
```

#### Chrome Custom Tabs (CCT)
In-app dialog message as default use [Chrome Custom Tabs](https://developer.chrome.com/multidevice/android/customtabs) to open links.
You can specify this behavior in `UserCom.Builder.openLinksInChromeCustomTabs(boolean)` function.

You can pass custom `CustomTabsIntent.Builder` to customize CCT:
```java
@Override
public void onCreate() {
    super.onCreate();
    new UserCom.Builder()
            ...
            .openLinksInChromeCustomTabs(true)
            .setCustomTabsBuilder(getCustomTabsBuilder())
            .build();
}
   
private CustomTabsIntent.Builder getCustomTabsBuilder() {
    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
    builder.setToolbarColor(Color.GREEN);
    return builder;
} 
```
