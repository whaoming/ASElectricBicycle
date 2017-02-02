package com.wxxiaomi.ming.webmodule.action.ui;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by 12262 on 2016/11/22.
 */

public class UiTypeAdapter implements JsonDeserializer<UiAction> {
    @Override
    public UiAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonElement floatBtn = json.getAsJsonObject().get("floatBtn");
            if (floatBtn == null) {
                return context.deserialize(json, NormalUiAction.class);
            } else {
                return context.deserialize(json, UiActionWithFloat.class);
            }
        }catch (Exception e){
            e.printStackTrace();;
        }
        return null;
    }
}
