package com.wxxiaomi.ming.electricbicycle.bean.format;

import com.wxxiaomi.ming.electricbicycle.bean.UserCommonInfo;

import java.util.Arrays;
import java.util.List;



public class NearByPerson {
	public List<UserLocatInfo> userLocatList;
	public static class UserLocatInfo{
		public UserCommonInfo userCommonInfo;
		public double[] point;

		public int id;
		public String time;
		public String geo;

		@Override
		public String toString() {
			return "UserLocatInfo{" +
					"userCommonInfo=" + userCommonInfo.toString() +
					", point=" + Arrays.toString(point) +
					", id=" + id +
					", time='" + time + '\'' +
					", geo='" + geo + '\'' +
					'}';
		}
	}
}
