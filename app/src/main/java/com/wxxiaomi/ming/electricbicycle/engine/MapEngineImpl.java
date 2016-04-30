package com.wxxiaomi.ming.electricbicycle.engine;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxxiaomi.ming.electricbicycle.ConstantValue;
import com.wxxiaomi.ming.electricbicycle.GlobalParams;
import com.wxxiaomi.ming.electricbicycle.bean.format.NearByPerson;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;
import com.wxxiaomi.ming.electricbicycle.engine.common.ResultByGetDataListener;


public class MapEngineImpl {

//	private MapEngineImpl() {
//	}
//
//	private static class LazyHolder {
//		private static final MapEngineImpl INSTANCE = new MapEngineImpl();
//	}
//
//	public static final MapEngineImpl getInstance() {
//		return LazyHolder.INSTANCE;
//	}
	
	Context context;
	RequestQueue mQueue;

	public MapEngineImpl(Context context) {
		super();
		this.context = context;
		mQueue = Volley.newRequestQueue(context);
	}

	public void getNearByFromServer1(double latitude, double longitude,
			final ResultByGetDataListener<NearByPerson> lis) {
		String url;
		if (GlobalParams.user == null) {
			url = ConstantValue.SERVER_URL
					+ "ActionServlet?action=getnearby&userid=19" + "&latitude="
					+ latitude + "&longitude=" + longitude;
		} else {
			url = ConstantValue.SERVER_URL
					+ "ActionServlet?action=getnearby&userid="
					+ GlobalParams.user.id + "&latitude=" + latitude
					+ "&longitude=" + longitude;
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {

						// processJsonResult(response.toString());
						Gson gson = new Gson();
					Log.i("wang", "获取附近的人json="+response.toString());
						ReceiceData<NearByPerson> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<NearByPerson>>() {
								}.getType());
						lis.success(result);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("wang", error.toString(), error);
						lis.error(error.getMessage());
					}
				});
		mQueue.add(jsonObjectRequest);

	}

	/**
	 * 获取附近的人
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public ReceiceData<NearByPerson> getNearByFromServer(double latitude,
			double longitude) {
		String url;
		ReceiceData<NearByPerson> result = null;
		// result = gson.fromJson(response.toString(), new
		// TypeToken<ReceiceData<NearByPerson>>(){}.getType());
		if (GlobalParams.user == null) {
			url = ConstantValue.SERVER_URL
					+ "ActionServlet?action=getnearby&userid=19" + "&latitude="
					+ latitude + "&longitude=" + longitude;
		} else {
			url = ConstantValue.SERVER_URL
					+ "ActionServlet?action=getnearby&userid="
					+ GlobalParams.user.id + "&latitude=" + latitude
					+ "&longitude=" + longitude;
		}
		getJson(url);

		// String json = HttpClientUtil.doGet(url);

		return result;
	}

	private void getJson(String url) {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("wang", error.getMessage(), error);
					}
				});
		mQueue.add(jsonObjectRequest);

	}
}
