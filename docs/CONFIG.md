# DEPRECATED documentation - no longer actively maintained
This doc has been preserved to give you some details but some of them
may be out of date so be sure to check specific behavior in code. 

# Configuration

## Add UserCom SDK to your app:

Copy usercom-sdk.aar into your libs directory in app module.

In **top level build.gradle** file add:
```groovy
allprojects {
    repositories {
        jcenter()
        flatDir {
            dirs 'libs'
        }
    }
}
```

Then in **app module build.gradle**:
```groovy
    compile(name:'usercom-sdk', ext: 'aar')
```

## Configure SDK in your app:
```java
public class App extends Application {

    public static final String TAG = App.class.getSimpleName();
    private UserCom userCom;

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
}
```
