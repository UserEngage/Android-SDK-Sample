package com.user.sdk.sample;

import com.user.sdk.events.ProductEvent;
import com.user.sdk.events.ProductEventType;
import com.user.sdk.events.UserComProductEvent;

import java.util.HashMap;
import java.util.Map;

@ProductEvent(productId = "1234", eventType = ProductEventType.REFUND)
public class MyCustomProductEvent implements UserComProductEvent {
    @Override
    public Map<String, Object> toFlat() {
        Map<String, Object> eventBody = new HashMap<>();

        Map<String, Object> innerMap = new HashMap<>();
        innerMap.put("CustomKey1", "Value1");
        innerMap.put("CustomKey2", 2);

        eventBody.put("product_rating", 0);
        eventBody.put("custom_data", innerMap);
        return eventBody;
    }
}