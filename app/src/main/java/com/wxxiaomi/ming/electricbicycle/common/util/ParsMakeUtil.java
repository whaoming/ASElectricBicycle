package com.wxxiaomi.ming.electricbicycle.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 12262 on 2016/11/24.
 */

public class ParsMakeUtil {

    /**
     * 将类似par1=xxx&par2=sss的参数转化成map
     * @param data
     * @return
     */
    public static Map<String, String> string2Map(String data) {
        Map<String, String> datas = new HashMap<>();
        String[] split = data.split("&");
        for (String item : split) {
            datas.put(item.substring(0, item.indexOf("=")), item.substring(item.indexOf("=") + 1, item.length()));
        }
        return datas;
    }

    public static String makeUpParamLikePic(String primary,String parkey,List<String> parValue){
        String needAdd = "&"+parkey+"=";
        for(int i=0;i<parValue.size();i++){
            if(i==0)
                needAdd += parValue.get(0);
            else
                needAdd += "#"+parValue.get(i);
        }
        primary += needAdd;
        return primary;
    }
}
