package com.wxxiaomi.ming.electricbicycle.db.bean.format;

import java.util.List;

import com.wxxiaomi.ming.electricbicycle.db.bean.FootPrintDetail;
import com.wxxiaomi.ming.electricbicycle.db.bean.UserCommonInfo;

/**
 * 在获取足迹的时候的数据格式
 */
public class FootPrintGet {

	public List<FootPrintDetail> footPrints;
	public UserCommonInfo userInfo;
	public FootPrintGet(List<FootPrintDetail> footPrints,
			UserCommonInfo userInfo) {
		super();
		this.footPrints = footPrints;
		this.userInfo = userInfo;
	}
	
	
}
