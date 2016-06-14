package com.wxxiaomi.ming.electricbicycle.engine;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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
import com.wxxiaomi.ming.electricbicycle.bean.format.InitUserInfo;
import com.wxxiaomi.ming.electricbicycle.bean.format.Login;
import com.wxxiaomi.ming.electricbicycle.bean.format.Register;
import com.wxxiaomi.ming.electricbicycle.bean.format.common.ReceiceData;
import com.wxxiaomi.ming.electricbicycle.engine.common.ResultByGetDataListener;


public class UserEngineImpl {

	@SuppressWarnings("unused")
	private Context context;
	RequestQueue mQueue;

	public UserEngineImpl(Context context) {
		super();
		this.context = context;
		mQueue = Volley.newRequestQueue(context);
	}
	
	/**
	 * 根据emnamelist从服务器获取对应的用户公共信息
	 * @param emnamelist
	 * @param lis
	 */
	public void getUserInfoByEmnameList(List<String> emnamelist,final ResultByGetDataListener<InitUserInfo> lis){
		String temp = "";
		for(String e : emnamelist){
			temp += e+"<>";
		}
//		Log.i("wang", "temp="+temp);
		String url = ConstantValue.SERVER_URL
				+ "ActionServlet?action=getuserinfolistbyemname" + "&emnamelist=" + temp;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						Log.i("wang", "根据emname从服务器获取info的返回结果是="+response.toString());
						Gson gson = new Gson();
						ReceiceData<InitUserInfo> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<InitUserInfo>>() {
								}.getType());
//						Log.i("wang", "result.infos.friendList.size()="+result.infos.friendList.size());
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


	
	public void getUserCommonInfoByEmname(String emname,final ResultByGetDataListener<InitUserInfo> lis){
		String url = ConstantValue.SERVER_URL
				+ "ActionServlet?action=getuserinfolistbyemname" + "&emnamelist=" + emname+"#";
//		UserCommonInfo info;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						Log.i("wang", "response.toString()="+response.toString());
						Gson gson = new Gson();
						ReceiceData<InitUserInfo> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<InitUserInfo>>() {
								}.getType());
						Log.i("wang", "result.infos.friendList.size()="+result.infos.friendList.size());
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

	private String getCodePar1(String par) {
		try {
			return URLEncoder.encode(par, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return par;
		}

	}

	public void getUserCommonInfoByName(String name,final ResultByGetDataListener<InitUserInfo> lis){
		String url = ConstantValue.SERVER_URL
				+ "ActionServlet?action=getuserinfobyname" + "&name=" +getCodePar1(name);
//		UserCommonInfo info;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						Log.i("wang", "response.toString()="+response.toString());
						Gson gson = new Gson();
						ReceiceData<InitUserInfo> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<InitUserInfo>>() {
								}.getType());
						Log.i("wang", "result.infos.friendList.size()="+result.infos.friendList.size());
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

	public void initUserInfoData(String username, String password,
			final ResultByGetDataListener<InitUserInfo> lis) {
		String url = ConstantValue.SERVER_URL
				+ "ActionServlet?action=inituserinfo" + "&username=" + username
				+ "&password=" + password;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						Log.i("wang", "response.toString()="+response.toString());
						Gson gson = new Gson();
						ReceiceData<InitUserInfo> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<InitUserInfo>>() {
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

	private String getCodePar(String par) {
		try {
			return URLEncoder.encode(par, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return par;
		}

	}

	/**
	 * 连接服务器检测手机号是否已被注册 如果未被注册，则获取验证码
	 * 
	 *            发送号码
	 */
	public void registerUser(String username, String password,
			final ResultByGetDataListener<Register> lis) {
		String url = null;
		url = ConstantValue.SERVER_URL + "ActionServlet?action=register"
				+ "&username=" + getCodePar(username) + "&password=" + password;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						Gson gson = new Gson();
						ReceiceData<Register> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<Register>>() {
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
	 * 执行登录操作
	 * 
	 * @param username
	 *            账号
	 * @param password
	 *            密码
	 * @return
	 */
	public void Login(String username, String password, boolean isFirst,
			final ResultByGetDataListener<Login> lis) {
		String url;
		if (isFirst) {
			url = ConstantValue.SERVER_URL + ConstantValue.LOGIN_URL
					+ "&username=" + username + "&password=" + password
					+ "&isfirst=y";
		} else {
			url = ConstantValue.SERVER_URL + ConstantValue.LOGIN_URL
					+ "&username=" + username + "&password=" + password
					+ "&isfirst=n";
		}
		Log.i("wang", "url="+url);
		try{
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						if(response!=null){
							Gson gson = new Gson();
							ReceiceData<Login> result = gson.fromJson(
									response.toString(),
									new TypeToken<ReceiceData<Login>>() {
									}.getType());
							lis.success(result);
						}else{
							lis.error("连接不上服务器");
						}
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("wang", error.toString(), error);
						lis.error(error.getMessage());
					}
				});
		mQueue.add(jsonObjectRequest);
		}catch(Exception e){
			lis.error("连接不上服务器");
		}

	}

	// public void RegisterWithCar(String username,String password,String
	// name,int carid
	// ,final ResultByGetDataListener<Register> lis){
	// String url =
	// ConstantValue.SERVER_URL+"ActionServlet?action=register&name="+name+"&username="+username+"&password="+password;
	// }

	public void BundCar(int userid, int carid,
			final ResultByGetDataListener<String> lis) {
		String url = ConstantValue.SERVER_URL
				+ "ActionServlet?action=bundbicycle&userid=" + userid
				+ "&cardid=" + carid;
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						Gson gson = new Gson();
						ReceiceData<String> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<String>>() {
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
	 * 注册一个用户
	 * 
	 * @param userid
	 * @param username

	 * @return
	 */
	public void ImproveUserInfo(int userid, String username, String name,
			String description, String headUrl,
			final ResultByGetDataListener<Register> lis) {
		String url = ConstantValue.SERVER_URL
				+ "ActionServlet?action=improveuserinfo&name=" + getCodePar(name)
				+ "&description=" + getCodePar(description) + "&headUrl=" + headUrl
				+ "&userid=" + userid + "&username=" + username;
//		Log.i("wang", "url=" + url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// processJsonResult(response.toString());
						Gson gson = new Gson();
						ReceiceData<Register> result = gson.fromJson(
								response.toString(),
								new TypeToken<ReceiceData<Register>>() {
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
		//
	}
}
