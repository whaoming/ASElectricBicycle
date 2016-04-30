package com.wxxiaomi.ming.electricbicycle.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {

	/**
	 * 检查是否含有中文
	 * @param username2
	 */
	public static boolean checkChainse(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
           
            return true;
        }
        return false;
	}
}
