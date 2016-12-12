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
	public static String SERVER_URL = "http://192.168.1.46:8080/ElectricBicycleServer/";

	/**
	 * 登录操作的url
	 */
	public static String OSS_MY_URL = "http://192.168.1.46:8080/OssTokenGetServer/Oss";


	//全局变量
	public static final  String LONGTOKEN = "long_token";
}
