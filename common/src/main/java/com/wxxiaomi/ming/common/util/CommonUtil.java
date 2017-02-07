package com.wxxiaomi.ming.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mr.W on 2017/2/7.
 * E-maiil：122627018@qq.com
 * github：https://github.com/122627018
 */

public class CommonUtil {

    /**
     * 检查是否含有中文
     */
    public static boolean checkChainse(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

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

    /**
     * 参数拼装 主要用于图片参数拼装
     * @param primary
     * @param parkey
     * @param parValue
     * @return
     */
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
