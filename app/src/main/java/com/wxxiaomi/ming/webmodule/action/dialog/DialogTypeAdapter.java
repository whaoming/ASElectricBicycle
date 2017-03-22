package com.wxxiaomi.ming.webmodule.action.dialog;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
* @author whaoming
* github：https://github.com/whaoming
* created at 2017/2/24 9:50
* TODO: 用于dialog相关的json解析
*/
public class DialogTypeAdapter implements JsonDeserializer<DialogACtion> {
    @Override
    public DialogACtion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String type = json.getAsJsonObject().get("type").getAsString();
        DialogACtion a = new AlertAction();
        if(type.equals("loading")){
            return context.deserialize(json, LoadingAction.class);
        }else if(type.equals("alert")){
            return context.deserialize(json, AlertAction.class);
        }
        return null;
    }
}
