package com.wxxiaomi.ming.electricbicycle;

public interface ConstantValue {

	/**
	 * 是否开启聊天服务器
	 */
	static final boolean isEMOpen = false;
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
//	public static String SERVER_URL = "http://10.0.2.2:8080/ElectricBicycleServer/";
//	public static String SERVER_URL = "http://210.38.162.23:8080/ElectricBicycleServer/";
//	public static String SERVER_URL = "http://192.168.56.1:8080/ElectricBicycleServer/";
	public static String SERVER_URL = "http://192.168.42.218:8080/ElectricBicycleServer/";
//	public static String SERVER_URL = "http:/10.15.75.175:8080/ElectricBicycleServer/";
	
	/**
	 * 登录操作的url
	 */
	public static String LOGIN_URL = "ActionServlet?action=login";
}
