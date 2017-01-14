package com.wxxiaomi.ming.electricbicycle;

public interface ConstantValue {

	/**
	 * 是否开启聊天服务器
	 */
	 boolean isEMOpen = true;
	/**
	 * 是否开启调试模式
	 */
	 boolean isDeBug = true;

	/**
	 * 是否开启扫描
	 */
	 boolean openScan = false;

	/**
	 * 校园网wifi笔记本
	 */
	String SERVER_URL = "http://xiejinhao.me/ElectricBicycleServer/";

	/**
	 * 登录操作的url
	 */
	 String OSS_MY_URL = "http://xiejinhao.me/OssTokenGetServer/Oss";


	//标识去往userinfo页面要加载的是目标user还是自己
	String INTENT_ISMINE = "intent_runmine";
	String INTENT_USERID = "intent_userid";
	//在userinfoactivity中传userinfo给fragment的标识
	String BUNDLE_USERINFO = "bundle_userinfo";
	String BUNDLE_OPTIONS = "bundle_options";
	String INTENT_USERINFO = "intent_userinfo";
}
