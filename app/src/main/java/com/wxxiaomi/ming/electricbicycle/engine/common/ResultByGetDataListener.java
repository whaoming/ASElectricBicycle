package com.wxxiaomi.ming.electricbicycle.engine.common;


import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;

public interface ResultByGetDataListener<T> {
	void success(ReceiceData<T> result);
	void error(String error);
}
