# DEPRECATED documentation - no longer actively maintained
This doc has been preserved to give you some details but some of them
may be out of date so be sure to check specific behavior in code. 

Activity event
```json
{
    "event":"ActivityEvent",
    "timestamp":"2017-11-02T22:20:56.0Z",
    "data":
    {
        "event":"stopped | started",
        "screen_name":"LoginActivity"
    }
}
```

If needed we can also track Fragments and custom views lifecycle events.

Customer + device information
It is sent during the login - with all custom user attributes.
```json
{
  "user_id":"myseconduser",
  "name":"Jane Doe",
  "email":"secondemail@example.com",
  "age":27,
  "fcm_key":"fxXpTsFfVhs:APA91bHuDgDCpbJ3DfnNsg0tMSVUuKfgjbkQAkS1_25uz6-5sREP29bHa_ncT7R7VMF9kyswgaJF0yaDxjFdcYwIe3fNHzg1Va9AMkSyxMIgaMA7s4RjdTkY2RYPmT-7i2RjdSStImzQ",
}
```
```json
{
  "version":"3.10.0+ (4290868)",
  "serialVersionUID":-2429611601725601363,
  "manufacturer":"Google",
  "device":"generic_x86",
  "sdk":24,
  "model":"Android SDK built for x86 (sdk_google_phone_x86)"
}
```

Generic event:
```json
{
    "event":"CustomEventName",
    "timestamp":"2017-11-02T22:20:56.0Z",
    "data":
    {
        "key1":"value1",
        "key2":2,
        "key3":true,
        "key4":0.123
    }
}
```
