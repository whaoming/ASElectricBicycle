package com.wxxiaomi.ming.electricbicycle;

public interface ConstantValue {

	/**
	 * 是否开启聊天服务器
	 */
	static final boolean isEMOpen = true;
	/**
	 * 是否开启调试模式
	 */
	 static final boolean isDeBug = true;

	/**
	 * 是否开启扫描
	 */
	static final boolean openScan = false;
	/**
	 * 百度地图api key
	 */
	String KEY="7586D98848407A4891F6FBA3C2E64ACD1F4D08BE";
	
	/**
	 *新浪sae服务器地址
	 */
//	public static String SERVER_URL = "http://haoming1994.applinzi.com/";
	
	
	/**
	 * 校园网wifi笔记本
	 */
<<<<<<< HEAD
	public static String SERVER_URL = "http://192.168.1.46:8080/ElectricBicycleServer/";
=======
	public static String SERVER_URL = "http://192.168.191.1:8080/ElectricBicycleServer/";
>>>>>>> c97ae7380867e58ef1d15c871180776c719ed907

	/**
	 * 登录操作的url
	 */
<<<<<<< HEAD
	public static String OSS_MY_URL = "http://192.168.1.46:8080/OssTokenGetServer/Oss";
=======
	public static String OSS_MY_URL = "http://192.168.191.1:8080/OssTokenGetServer/Oss";
>>>>>>> c97ae7380867e58ef1d15c871180776c719ed907


	//全局变量
	public static final  String LONGTOKEN = "long_token";
	//标识去往userinfo页面要加载的是目标user还是自己
	String INTENT_ISMINE = "intent_runmine";
	String INTENT_USERID = "intent_userid";
	//在userinfoactivity中传userinfo给fragment的标识
	String BUNDLE_USERINFO = "bundle_userinfo";
	String BUNDLE_OPTIONS = "bundle_options";
	String INTENT_USERINFO = "intent_userinfo";
}
