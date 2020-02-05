# DEPRECATED documentation - no longer actively maintained
This doc has been preserved to give you some details but some of them
may be out of date so be sure to check specific behavior in code. 

UserCom SDK tracks user activity across the app. 
The following activities will be tracked by default:

## Screen tracking

```java
@Override
    public void onCreate() {
        super.onCreate();
        userCom = new UserCom.Builder()
                .context(this) //application context
                .apiKey("api_secret") //your api secret key generated in panel [url]
                .baseUrl("http://localhost:8080/") //use only if you have self hosted UE engine
                .trackActivities()
                .build();
    }
```

To enable screen tracking, enable this options in SDK configuration. Add **trackActivities()** into the builder.
For now, only Activities lifecycle will be tracked.

```java
@ScreenName(name = "main_activity")
@TrackIntents(intents = TrackIntents.ALL)
public class MainActivity extends Activity {
    
}
```

To define custom screen name, annotate activity with **@ScreenName** annotation. 
If you don't provide custom name, MainActivity.class.getSimpleName() will be used.

Use **@TrackIntents** annotation to specify which arguments 
(intent keys and values) should be send to UserCom. By default all values will be sent.

To specify which intent keys should be tracked, put them into annotation array:
```java
@ScreenName(name = "main_activity")
@TrackIntents(intents = {MainActivity.INTENT_KEY1, MainActivity.INTENT_KEY2})
public class MainActivity extends Activity {

    public static final String INTENT_KEY1 = "INTENT_KEY1";
    public static final String INTENT_KEY2 = "INTENT_KEY2";
}
```

The following screen events will be sent:
* activity started (with intent arguments)
* activity created
* activity stopped

## Custom events
You can send to UserCom your custom events. 
To create custom event, implement **UserComProperty**
```java
@Event(name = "custom_event")
class MyCustomEvent implements UserComProperty {

    @Override
    public Map<String, Object> toFlat() {
        Map<String, Object> eventBody = new HashMap<>();
        eventBody.put("attr1", "value1");
        return eventBody;
    }
}
```

And send it anywhere from your app:
```java
UserCom.getInstance().sendEvent(new MyCustomEvent());
```

You don't have to worry about offline mode - event will stored in local DB and sent when network will be available.
