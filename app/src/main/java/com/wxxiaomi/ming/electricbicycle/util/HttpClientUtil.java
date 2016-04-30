package com.wxxiaomi.ming.electricbicycle.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 通用的网络链接工具
 * 
 * @author Administrator
 * 
 */
public class HttpClientUtil {
//	private HttpClient client;
//	HttpURLConnection c;
//
//	private HttpGet get;
//	private HttpPost post;
//
//	private HttpResponse response;
//
//	private static Header[] headers;
//	static {
//		headers = new BasicHeader[10];
//		headers[0] = new BasicHeader("Appkey", "12343");
//		headers[1] = new BasicHeader("Udid", "");// 手机串号
//		headers[2] = new BasicHeader("Os", "android");//
//		headers[3] = new BasicHeader("Osversion", "");//
//		headers[4] = new BasicHeader("Appversion", "");// 1.0
//		headers[5] = new BasicHeader("Sourceid", "");//
//		headers[6] = new BasicHeader("Ver", "");
//
//		headers[7] = new BasicHeader("Userid", "");
//		headers[8] = new BasicHeader("Usersession", "");
//
//		headers[9] = new BasicHeader("Unique", "");
//	}
//
//	public HttpClientUtil() {
//		client = new DefaultHttpClient();
//		// 设置代理信息
//		if (StringUtils.isNotBlank(GlobalParams.PROXY_IP)) {
//			HttpHost host = new HttpHost(GlobalParams.PROXY_IP,
//					GlobalParams.PROXY_PORT);
//
//			client.getParams()
//					.setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
//		}
//
//	}
//
//	/**
//	 * 发送请求
//	 * 
//	 * @param uri
//	 * @param xml
//	 */
//	// public InputStream sendPost(String uri, String xml) {
//	// post = new HttpPost(uri);
//	//
//	// // 超时
//	//
//	// try {
//	// HttpEntity entity = new StringEntity(xml, ConstantValue.ENCODING);
//	// post.setEntity(entity);
//	//
//	// response = client.execute(post);
//	// if (response.getStatusLine().getStatusCode() == 200) {
//	// return response.getEntity().getContent();
//	// }
//	//
//	// } catch (Exception e) {
//	// e.printStackTrace();
//	// }
//	// return null;
//	// }
//
//	/*************************** 电商 **********************************/
//	// post或get请求带参数
//	// Get:url?params=xxx&
//	// post：带参数（HttpEntity：参数+编码）
//
//	// 细节：设置头，设置超时限制
//	/**
//	 * 发送Post请求
//	 * 
//	 * @param uri
//	 * @param params
//	 *            ：参数
//	 * @return
//	 */
//	public String sendPost(String uri, Map<String, String> params) {
//		post = new HttpPost(uri);
//
//		post.setHeaders(headers);
//
//		// 处理超时
//		HttpParams httpParams = new BasicHttpParams();//
//		httpParams = new BasicHttpParams();
//		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
//		HttpConnectionParams.setSoTimeout(httpParams, 8000);
//		post.setParams(httpParams);
//
//		// 设置参数
//		if (params != null && params.size() > 0) {
//			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
//			for (Map.Entry<String, String> item : params.entrySet()) {
//				BasicNameValuePair pair = new BasicNameValuePair(item.getKey(),
//						item.getValue());
//				parameters.add(pair);
//			}
//			try {
//				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
//						parameters, ConstantValue.ENCODING);
//				post.setEntity(entity);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//
//		try {
//			response = client.execute(post);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				return EntityUtils.toString(response.getEntity(),
//						ConstantValue.ENCODING);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return "";
//	}
//
//	/**
//	 * 发送get请求
//	 */
//	public String sendGet(String uri) {
//		// uri = "http://192.168.1.102:8080/redbaby/home";
//		// uri = "http://192.168.1.102:8080/redbaby/search/recommend";
//		get = new HttpGet(uri);
//		get.setHeaders(headers);
//		get.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,5000);
//		get.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000);
//		try {
//			response = client.execute(get);
//			if (response.getStatusLine().getStatusCode() == 200) {
//				return EntityUtils.toString(response.getEntity(),
//						ConstantValue.ENCODING);
//			} else {
////				System.out.println(response.getStatusLine().getStatusCode());
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return "";
//	}
//
//	public InputStream loadImg(String uri) {
//		get = new HttpGet(uri);
//
//		HttpParams httpParams = new BasicHttpParams();//
//		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
//		HttpConnectionParams.setSoTimeout(httpParams, 8000);
//		get.setParams(httpParams);
//
//		try {
//			response = client.execute(get);
//
//			if (response.getStatusLine().getStatusCode() == 200) {
//				return response.getEntity().getContent();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	 /** 
     * Get请求，获得返回数据 
     *  
     * @param urlStr 
     * @return 
     * @throws Exception 
     */  
    public static String doGet(String urlStr)   
    {  
        URL url = null;  
        HttpURLConnection conn = null;  
        InputStream is = null;  
        ByteArrayOutputStream baos = null;  
        try  
        {  
            url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            
            conn.setReadTimeout(8000);  
            conn.setConnectTimeout(8000);  
            conn.setRequestMethod("GET");  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            if (conn.getResponseCode() == 200)  
            {  
                is = conn.getInputStream();  
                baos = new ByteArrayOutputStream();  
                int len = -1;  
                byte[] buf = new byte[128];  
  
                while ((len = is.read(buf)) != -1)  
                {  
                    baos.write(buf, 0, len);  
                }  
                baos.flush();  
                return baos.toString();  
            } else  
            {  
                throw new RuntimeException(" responseCode is not 200 ... ");  
            }  
  
        } catch (Exception e)  
        {  
            e.printStackTrace();  
        } finally  
        {  
            try  
            {  
                if (is != null)  
                    is.close();  
            } catch (IOException e)  
            {  
            }  
            try  
            {  
                if (baos != null)  
                    baos.close();  
            } catch (IOException e)  
            {  
            }  
            conn.disconnect();  
        }  
          
        return null ;  
  
    }  
	
	/**  
     * 向指定 URL 发送POST方法的请求  
     *   
     * @param url  
     *            发送请求的 URL  
     * @param param  
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。  
     * @return 所代表远程资源的响应结果  
     * @throws Exception  
     */  
    public static String doPost(String url, String param)   
    {  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try  
        {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            HttpURLConnection conn = (HttpURLConnection) realUrl  
                    .openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestMethod("POST");  
//            conn.setRequestProperty("Content-Type",  
//                    "application/x-www-form-urlencoded");  
            conn.setRequestProperty("charset", "utf-8");  
            conn.setRequestProperty("ContentType", "text/html;charset=UTF-8");  
            conn.setUseCaches(false);  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setReadTimeout(6000);  
            conn.setConnectTimeout(6000);  
  
            if (param != null && !param.trim().equals(""))  
            {  
                // 获取URLConnection对象对应的输出流  
                out = new PrintWriter(conn.getOutputStream());  
                // 发送请求参数  
                out.print(param);  
                // flush输出流的缓冲  
                out.flush();  
            }  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(  
                    new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null)  
            {  
                result += line;  
            }  
        } catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally  
        {  
            try  
            {  
                if (out != null)  
                {  
                    out.close();  
                }  
                if (in != null)  
                {  
                    in.close();  
                }  
            } catch (IOException ex)  
            {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }  
}
