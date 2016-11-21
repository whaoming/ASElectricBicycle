package com.wxxiaomi.ming.electricbicycle.core.web.action.forward;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12262 on 2016/11/12.
 * 跳转动作对于gson的适配器
 */

public class ForwardTypeAdapter implements JsonDeserializer<ForwardAction> {



    @Override
    public ForwardAction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String type = json.getAsJsonObject().get("type").getAsString();
        Log.i("wang","gson->type:"+type);
        ForwardAction action = null;
        if(type.equals("h5")){
            String page = json.getAsJsonObject().get("page").getAsString();
            String data  = json.getAsJsonObject().get("data").getAsString();
            //Log.i("wang","data="+data);
            boolean isRetrun = json.getAsJsonObject().get("isReturn").getAsString().equals("true")?true:false;
            String callback = json.getAsJsonObject().get("callback").getAsString();
            action = new H5ACtion(page,data,isRetrun,callback);
        }else if(type.equals("native")){
            String page = json.getAsJsonObject().get("page").getAsString();
            String data  = json.getAsJsonObject().get("data").getAsString();
            //Log.i("wang","data="+data);
            boolean isRetrun = json.getAsJsonObject().get("isReturn").getAsString().equals("true")?true:false;
            String callback = json.getAsJsonObject().get("callback").getAsString();
            action = new NativeAction(page,data,isRetrun,callback);
        }else if(type.equals("manyH5")){
            int pageSize = Integer.valueOf(json.getAsJsonObject().get("pageCount").getAsString());
            JsonArray pageArray = json.getAsJsonObject().get("pages").getAsJsonArray();
            JsonArray dataArray = json.getAsJsonObject().get("datas").getAsJsonArray();
            JsonArray tabArray = json.getAsJsonObject().get("tabs").getAsJsonArray();
            List<String> pages = new ArrayList<>();
            List<String> datas = new ArrayList<>();
            List<String> tabs = new ArrayList<>();
            String title = json.getAsJsonObject().get("title").getAsString();
            for(int i=0;i<pageSize;i++){
                pages.add(pageArray.get(i).getAsString());
                datas.add(dataArray.get(i).getAsString());
                tabs.add(tabArray.get(i).getAsString());
            }
           action = new ManyH5Action(pages,datas,pageSize,title,tabs);
        }
        return action;
    }
    /**
     * {
     "type_tag":"h5",
     "page":"","data":"",
     "isReturn":"",
     "callback":""
     }
     */
}
