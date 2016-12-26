package com.wxxiaomi.ming.electricbicycle.dao.bean.format;

import java.util.List;

import com.wxxiaomi.ming.electricbicycle.dao.bean.UserCommonInfo2;

public class FootPrintGet {

	public List<FootPrintDetail> footPrints;
	public UserCommonInfo2 userInfo;
	public FootPrintGet(List<FootPrintDetail> footPrints,
			UserCommonInfo2 userInfo) {
		super();
		this.footPrints = footPrints;
		this.userInfo = userInfo;
	}
	
	
}
