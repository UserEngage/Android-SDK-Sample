package com.user.sdk.sample;

import com.user.sdk.events.Event;
import com.user.sdk.events.UserComEvent;

import java.util.HashMap;
import java.util.Map;

@Event(name = "custom_event")
class MyCustomEvent implements UserComEvent {

    @Override
    public Map<String, Object> toFlat() {
        Map<String, Object> eventBody = new HashMap<>();
        eventBody.put("attr1", "value1");
        return eventBody;
    }
}
