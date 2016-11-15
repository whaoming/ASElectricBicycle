package com.wxxiaomi.ming.electricbicycle.core.web.action.dialog;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by 12262 on 2016/11/13.
 */

public class DialogTypeAdapter implements JsonDeserializer<DialogACtion> {
    @Override
    public DialogACtion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String type = json.getAsJsonObject().get("type").getAsString();
        if(type.equals("loading")){
            return context.deserialize(json, LoadingAction.class);
        }else if(type.equals("alert")){
            return context.deserialize(json, AlertAction.class);
        }
        return null;
    }
}
