package com.wxxiaomi.ming.electricbicycle.api.constant;

/**
 * 与服务器约定好的数据接收格式
 * @author Mr.W
 *
 * @param <T>
 */
public class Result<T> {
	public int state;
	public String error;
	public T infos;

	@Override
	public String toString() {
		return "Result{" +
				"state=" + state +
				", error='" + error + '\'' +
				", infos=" + infos.toString() +
				'}';
	}
}
