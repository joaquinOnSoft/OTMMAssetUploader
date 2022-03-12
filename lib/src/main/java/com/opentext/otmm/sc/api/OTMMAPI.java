package com.opentext.otmm.sc.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class OTMMAPI {

	protected static int DEFAULT_VERSION_NUMBER = 6;
	private static int HTTP_RESPONSE_CODE_OK = 200;
	protected String urlBase;
	protected int version;
	protected CloseableHttpClient client;
	protected static final Logger logger = LogManager.getLogger(OTMMAPIAssets.class);

	public OTMMAPI(String urlBase) {
		this(urlBase, DEFAULT_VERSION_NUMBER);
	}

	public OTMMAPI(String urlBase, String version) {
		int ver = DEFAULT_VERSION_NUMBER;

		try {
			ver = Integer.parseInt(version);
		} catch (NumberFormatException e) {
			logger.error("Invalid version number, using default. ", e);
		}

		this.urlBase = urlBase;
		this.version = ver;
		this.client = HttpClients.createDefault();
	}
	
	public OTMMAPI(String urlBase, int version) {
		this(urlBase, Integer.toString(version));
	}		

	protected String getURLFromMethod(String method) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(urlBase).append("/otmmapi/v").append(version).append("/").append(method);
	
		return urlBuilder.toString();
	}

	protected List<NameValuePair> getDefaultHeaders(String sessionId){
		List<NameValuePair> headers = new LinkedList<NameValuePair>();
		NameValuePair header = new BasicNameValuePair("X-Requested-By", sessionId);
		
		headers.add(header);
		
		return headers;
	}
	
	/**
	 * HTTP GET request
	 * @param method - OTMM API method name
	 * @param sessionId - OTMM session identifier (got using "create session" API method)
	 * @return response from server (usually in JSON format)
	 */
	protected String get(String method, List<NameValuePair> headers) {
		String result = null;
	
		try {			
			HttpGet httpGet = new HttpGet(getURLFromMethod(method));
			
			if(headers != null) {
				for(NameValuePair pair: headers) {
					httpGet.addHeader(pair.getName(), pair.getValue());
				}
			}
	
			HttpResponse response = client.execute(httpGet);
	
			if (response.getStatusLine().getStatusCode() == HTTP_RESPONSE_CODE_OK) {
				result = EntityUtils.toString(response.getEntity());
			}						
		} catch (IOException e) {
			logger.error("OTMM API " + method + " (I/O) ", e);
		} catch (ParseException e) {
			logger.error("OTMM API " + method + " (Parse) ", e);
		}
		
		return result;
	}

	/**
	 * HTTP POST request
	 * @param method - OTMM API method name
	 * @param params - Key/value
	 * @return
	 * @see https://www.baeldung.com/httpclient-post-http-request
	 */
	protected String post(String method, List<NameValuePair> headers, List<HttpEntity> entities) {
		String result = null;
	
		try {
			HttpPost httpPost = new HttpPost(getURLFromMethod(method));
			if(headers != null) {
				for(NameValuePair pair: headers) {
					httpPost.addHeader(pair.getName(), pair.getValue());
				}
			}
	
			if(entities != null) {
				for(HttpEntity entity: entities) {
					httpPost.setEntity(entity);	
				}
			}
	
			CloseableHttpResponse response = client.execute(httpPost);
	
			if (response.getStatusLine().getStatusCode() == HTTP_RESPONSE_CODE_OK) {
				result = EntityUtils.toString(response.getEntity());
			}
		} catch (IOException e) {
			logger.error("OTMM API " + method + " (I/O) ", e);
		} catch (ParseException e) {
			logger.error("OTMM API " + method + " (Parse) ", e);
		}
		
		return result;
	}

	/**
	 * Create a Session Create a security Session in OTMM. It returns a valid
	 * SecuritySession object if the provided credentials are valid. This is
	 * equivalent to login to OTMM 
	 * <strong>Method</strong>: POST
	 * <strong>URL</strong>: /otmmapi/v6/sessions
	 * 
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#operation/createSession
	 */
	public String createSession(String username, String password) {
		String sessionId = null;
		List<HttpEntity> entities = null;
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		try {
			HttpEntity  entity = new UrlEncodedFormEntity(params);	
			entities = new LinkedList<HttpEntity>();
			entities.add(entity);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		String response = post("sessions", null, entities);
		
		if(response != null) {
			try {
				JSONObject json = new JSONObject(response);
				if (json != null) {
					sessionId = json.getJSONObject("session_resource").getJSONObject("session").optString("id");
				}
			} catch (JSONException e) {
				logger.error("/otmmapi/v" + version + "/sessions (Response to JSON conversion) ", e);
			}
		}
	
		return sessionId;
	}

	protected void finalize() throws Throwable {  
		if (client != null) {
			client.close();
		}
	}
}