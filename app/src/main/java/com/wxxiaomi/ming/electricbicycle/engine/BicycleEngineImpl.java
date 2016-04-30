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
import com.wxxiaomi.ming.electricbicycle.bean.Bicycle;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;
import com.wxxiaomi.ming.electricbicycle.engine.common.ResultByGetDataListener;


public class BicycleEngineImpl {

	Context context;
	RequestQueue mQueue;
	public BicycleEngineImpl(Context context) {
		super();
		this.context = context;
		mQueue = Volley.newRequestQueue(context);
	}
	
	public void getBicycleInfo(String carScanResult,final ResultByGetDataListener<Bicycle> lis){
		String url = ConstantValue.SERVER_URL+"ActionServlet?action=getbicycleinfo&bicycleid="+carScanResult;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						Gson gson = new Gson();
						ReceiceData<Bicycle> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<Bicycle>>() {
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
}
