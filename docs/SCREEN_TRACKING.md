# DEPRECATED documentation - no longer actively maintained
This doc has been preserved to give you some details but some of them
may be out of date so be sure to check specific behavior in code. 

# Screen tracking in UserCom

UserCom SDK will track your activity events. You know when your user open app,
navigates to specific activity or exits App.

## Configuration

To enable this feature in your app, add trackActivities() to your
UserCom builder:

```java
package com.user.sdk;

import android.app.Application;
import com.user.sdk.UserCom;

public class App extends Application {

    private UserCom userCom;

    @Override
    public void onCreate() {
        super.onCreate();
        userCom = new UserCom.Builder()
                .context(this)
                .apiKey("key")
                .trackActivities()
                .build();
    }
}

```

## Examples

### Track activity lifecycle

It will be done automatically. Default screen name is "MainActivity"

```json
{
  "event": "ActivityEvent",
  "timestamp": "2017-11-02T22:20:56.0Z",
  "data": {
    "event": "started",
    "screen_name": "MainActivity"
  }
}
```

### Custom screen name


To customize screen name add @ScreenName annotation.

##### Code
```java
@ScreenName(name = "main_activity")
public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }
}
```

##### Result:

```json
{
  "event": "ActivityEvent",
  "timestamp": "2017-11-02T22:20:56.0Z",
  "data": {
    "event": "started",
    "screen_name": "main_activity"
  }
}
```
### Track intent extras

#### All

All passed intent extras will be sent to UserCom server.

##### Code
```java
@ScreenName(name = "main_activity")
@TrackIntents(intents = TrackIntents.ALL)
public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }
}
```
##### Result
```json
{
  "event": "ActivityEvent",
  "timestamp": "2017-11-02T22:20:56.0Z",
  "data": {
    "event": "started",
    "screen_name": "main_activity",
    "extras1": "value1",
    "extras2": 137,
    "extras3": true
  }
}
```

#### Specific

If you want to pass only specific intent extras, write their keys in @TrackIntents annotation as follows:

##### Code

```java
@ScreenName(name = "main_activity")
@TrackIntents(intents = {MainActivity.INTENT_KEY1, MainActivity.INTENT_KEY2})
public class MainActivity extends Activity {

    public static final String INTENT_KEY1 = "INTENT_KEY1";
    public static final String INTENT_KEY2 = "INTENT_KEY2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }
}
```

##### Result
```json
{
  "event": "ActivityEvent",
  "timestamp": "2017-11-02T22:20:56.0Z",
  "data": {
    "event": "stopped | started",
    "screen_name": "main_activity",
    "INTENT_KEY1": "value1",
    "INTENT_KEY2": "value2"
  }
}
```
