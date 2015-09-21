package com.allpay.tw.mobilesdk;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
//import org.apache.http.protocol.HTTP;
import android.annotation.SuppressLint;
import android.content.Context;
//import android.net.http.AndroidHttpClient;
import android.util.Log;

@SuppressLint("DefaultLocale")
class HttpUtil {
//  private static final String TAG = HttpUtil.class.getName();
	private static final String TAG = Utility.LOGTAG;
  
  // Timeout (ms) for establishing a connection.
  private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 10 * 1000;

  // Timeout (ms) for read operations on connections.
  private static final int DEFAULT_READ_TIMEOUT_MILLIS = 10 * 1000;

  // Timeout (ms) for obtaining a connection from the connection pool.
  private static final int DEFAULT_GET_CONNECTION_FROM_POOL_TIMEOUT_MILLIS = 10 * 1000;

//  private static AndroidHttpClient createHttpClient() {
//    AndroidHttpClient httpClient = AndroidHttpClient.newInstance(null);
//    HttpParams params = httpClient.getParams();
//    HttpConnectionParams.setStaleCheckingEnabled(params, false);
//    HttpConnectionParams.setConnectionTimeout(params,DEFAULT_CONNECT_TIMEOUT_MILLIS);
//    HttpConnectionParams.setSoTimeout(params, DEFAULT_READ_TIMEOUT_MILLIS);
//    HttpConnectionParams.setSocketBufferSize(params, 8192);
//    ConnManagerParams.setTimeout(params,DEFAULT_GET_CONNECTION_FROM_POOL_TIMEOUT_MILLIS);
//
//    // Don't handle redirects automatically
//    HttpClientParams.setRedirecting(params, false);
//
//    // Don't handle authentication automatically
//    HttpClientParams.setAuthenticating(params, false);
//
//    return httpClient;
//  }
  
  public static String execute(HttpUriRequest request,Context cxt) throws Exception {
	  String result = null;
	  
	  boolean IsSony = false;
	  String sManufacturer = android.os.Build.MANUFACTURER;
		if(sManufacturer != null && sManufacturer.length() > 0){
			sManufacturer = sManufacturer.toLowerCase();

			if(sManufacturer.indexOf("sony") >= 0)
				IsSony = true;
			else 
				IsSony = false;
		}
	  
//	  if(IsSony){
		  HttpClient httpClient = null;		  
		  try {
			  httpClient = new DefaultHttpClient();
			  HttpResponse httpResponse = httpClient.execute(request);
			  int responseCode = httpResponse.getStatusLine().getStatusCode();

			  HttpEntity entity = httpResponse.getEntity();

			  InputStream instream = entity.getContent();

			  char[] buffer = new char[1024];
			  int flag = -1;
			  StringWriter sw = new StringWriter();
			  InputStreamReader in = new InputStreamReader(instream, "UTF-8");

			  while ((flag = in.read(buffer)) > 0) {
				  sw.write(buffer, 0, flag);
			  }

			  android.util.Log.d(TAG, "[sony]response : "+sw.toString());//TODO

			  if(responseCode != HttpStatus.SC_OK){
				  android.util.Log.d(TAG, "[sony]http responseCode: "+responseCode);//TODO
			  }

			  result = sw.toString();
		  } catch (Exception e) {
			  throw e;
		  } finally {
			  try{
				  if (httpClient != null) {
					  //          httpClient.close();

				  }
			  } catch (Exception e) {
				  // Ignored because this is not an error that is relevant to clients of this transport.
			  }
		  }
		  
//	  }else {
//		  AndroidHttpClient httpClient = null;
//		  try {
//			  httpClient = createHttpClient();
//			  HttpResponse httpResponse = httpClient.execute(request);
//			  int responseCode = httpResponse.getStatusLine().getStatusCode();
//
//			  HttpEntity entity = httpResponse.getEntity();
//
//			  InputStream instream = entity.getContent();
//
//			  char[] buffer = new char[1024];
//			  int flag = -1;
//			  StringWriter sw = new StringWriter();
//			  InputStreamReader in = new InputStreamReader(instream, "UTF-8");
//
//			  while ((flag = in.read(buffer)) > 0) {
//				  sw.write(buffer, 0, flag);
//			  }
//
//			  android.util.Log.d(TAG, "response : "+sw.toString());//TODO
//
//			  if(responseCode != HttpStatus.SC_OK){
//				  android.util.Log.d(TAG, "http responseCode: "+responseCode);//TODO
//			  }
//
//			  result = sw.toString();
//		  } catch (Exception e) {
//			  e.printStackTrace();
//		  } finally {
//			  try{
//				  if (httpClient != null) {
//			           httpClient.close();
//				  }
//			  } catch (Exception e) {
//				  // Ignored because this is not an error that is relevant to clients of this transport.
//			  }
//		  }
//	  }
	   
    
    return result;
  }
  
	public static String get(String url, Map<String, String> params, Context cxt) throws Exception {
		HttpGet httpGet = null;
		Log.d(TAG, "url : " + url);

		List<NameValuePair> getParams = new ArrayList<NameValuePair>();

		if (params == null) {
			httpGet = new HttpGet(url);
		} else {
			if (params.containsKey("")) {
				httpGet = new HttpGet(url + "?" + params.get(""));
			} else {
				for (String key : params.keySet()) {
					getParams.add(new BasicNameValuePair(key, params.get(key)));
				}

				httpGet = new HttpGet(url + "?" + URLEncodedUtils.format(getParams, "UTF-8"));
			}
		}
		
		return execute(httpGet, cxt);
	}
  
  public static String post(String url, Map<String, String> params,Context cxt) throws Exception {
    HttpPost httpPost = new HttpPost(url);
    Log.d(TAG, "url : "+url);
    
    List<NameValuePair> postParams = new ArrayList<NameValuePair>();
    
    if(params != null){
      if(params.containsKey("")){
        httpPost.setEntity(new StringEntity(params.get("")));
      }else{
    	  Log.d(TAG, "post data : \n");
        for(String key:params.keySet()){
        	Log.d(TAG, key + " = " + params.get(key) + "\n");
          postParams.add(new BasicNameValuePair(key, params.get(key)));
        }
         
        httpPost.setEntity(new UrlEncodedFormEntity(postParams, "UTF-8"));
      }
    }
    
    
    return execute(httpPost,cxt);
  }
  
 

}
