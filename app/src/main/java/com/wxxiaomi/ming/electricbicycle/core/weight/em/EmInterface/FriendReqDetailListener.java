package com.wxxiaomi.ming.electricbicycle.core.weight.em.EmInterface;

/**
 * 好友请求
 * @author Mr.W
 *
 */
public interface FriendReqDetailListener {

	/**
	 * 好友申请被同意
	 * @param username
	 */
	void agree(String username);

	/**
	 * 好友申请被拒绝
	 * @param username
	 */
	void refuse(String username);

	/**
	 * 受到好友申请
	 * @param username
	 * @param reason
	 */
	void getFriend(String username, String reason);

	/**
	 * 被删除
	 * @param username
	 */
	void deleted(String username);

	/**
	 * 被添加
	 * @param username
	 */
	void addContact(String username);

}
