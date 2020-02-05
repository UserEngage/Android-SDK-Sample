# DEPRECATED documentation - no longer actively maintained
This doc has been preserved to give you some details but some of them
may be out of date so be sure to check specific behavior in code. 

# Minimal example

## request
```json
{
  "to":"fxXpTsFfVhs:APA91bHuDgDCpbJ3DfnNsg0tMSVUuKfgjbkQAkS1_25uz6-5sREP29bHa_ncT7R7VMF9kyswgaJF0yaDxjFdcYwIe3fNHzg1Va9AMkSyxMIgaMA7s4RjdTkY2RYPmT-7i2RjdSStImzQ",
  "data": {
    "id": "akdhsjd",
    "user_com_notification": "user_com_notification",
    "title": "Welcome to UserCom demo",
    "subtitle": "Hope you are doing well"
  }
}
```

### required fields
* id
* user_com_notification
* title
* subtitle

This notification by default will be parsed by SDK and no action will be taken.

# Notification displayed at the top bar

### request
```json
{
  "to":"fxXpTsFfVhs:APA91bHuDgDCpbJ3DfnNsg0tMSVUuKfgjbkQAkS1_25uz6-5sREP29bHa_ncT7R7VMF9kyswgaJF0yaDxjFdcYwIe3fNHzg1Va9AMkSyxMIgaMA7s4RjdTkY2RYPmT-7i2RjdSStImzQ",
  "data": {
    "id": "akdhsjd",
    "user_com_notification": "user_com_notification",
    "type": 1,
    "title": "Welcome to UserCom demo",
    "subtitle": "Hope you are doing well"
  }
}
```
### required fields
* id
* user_com_notification
* title
* subtitle
* type: 1

Notification will be displayed at the top bar with title, subtitle, default icon and sound.

# Notification displayed at the top bar with launch screen action

### request:
```json
{
  "to":"fxXpTsFfVhs:APA91bHuDgDCpbJ3DfnNsg0tMSVUuKfgjbkQAkS1_25uz6-5sREP29bHa_ncT7R7VMF9kyswgaJF0yaDxjFdcYwIe3fNHzg1Va9AMkSyxMIgaMA7s4RjdTkY2RYPmT-7i2RjdSStImzQ",
  "data": {
    "id": "akdhsjd",
    "user_com_notification": "user_com_notification",
    "type": 3,
    "title": "Welcome to UserCom demo",
    "subtitle": "Hope you are doing well",
    "screen_name": "example_activity"
  }
}
```

### required fields
* id
* user_com_notification
* title
* subtitle
* type: 3
* screen_name  - name of screen. Defined with @ScreenName, or by default with ActivityClass.class.getSimpleName()

Notification will be displayed at the top bar with title, subtitle, default icon and sound.

If user clicks notification, he will be redirected to "screen_name" activity.

# In-app messages

## Toast
Toast is not supported right now

## Dialog

### request:
```json
{
  "to":"fxXpTsFfVhs:APA91bHuDgDCpbJ3DfnNsg0tMSVUuKfgjbkQAkS1_25uz6-5sREP29bHa_ncT7R7VMF9kyswgaJF0yaDxjFdcYwIe3fNHzg1Va9AMkSyxMIgaMA7s4RjdTkY2RYPmT-7i2RjdSStImzQ",
  "data": {
    "id": "akdhsjd",
    "user_com_notification": "user_com_notification",
    "type": 4,
    "subtype": 2,
    "title": "Welcome to UserCom demo",
    "content": "Hope you are doing well",
    "action_button_title": "Show",
    "action_button_link": "https://www.google.com"
  }
}
```

### required fields
* id
* user_com_notification
* title
* subtitle
* type: 4
* subtype: 2
* action_button_title

After push notification is received, SDK will display styleable Dialog
