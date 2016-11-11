package com.wxxiaomi.ming.electricbicycle.bean.format.common;

/**
 * 公共数据格式
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
