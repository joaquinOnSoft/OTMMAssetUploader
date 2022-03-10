package com.opentext.otmm.sc.api;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opentext.otmm.sc.api.beans.OTMMAsset;
import com.opentext.otmm.sc.api.beans.OTMMCollection;

public class OTMMAPIWrapper {
	private static int DEFAULT_VERSION_NUMBER = 6;

	private static int HTTP_RESPONSE_CODE_OK = 200;

	private String urlBase;
	private int version;
	private CloseableHttpClient client;

	static final Logger logger = LogManager.getLogger(OTMMAPIWrapper.class);

	public OTMMAPIWrapper(String urlBase) {
		this(urlBase, DEFAULT_VERSION_NUMBER);
	}

	public OTMMAPIWrapper(String urlBase, String version) {
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

	public OTMMAPIWrapper(String urlBase, int version) {
		this(urlBase, Integer.toString(version));
	}

	protected String getURLFromMethod(String method) {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(urlBase).append("/otmmapi/v").append(version).append("/").append(method);

		return urlBuilder.toString();
	}

	protected String get(String method, String sessionId) {
		String result = null;

		try {			
			HttpGet httpGet = new HttpGet(getURLFromMethod(method));
			httpGet.addHeader("X-Requested-By", sessionId);
	
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
	 * 
	 * @param method - OTMM API method name
	 * @param params - Key/value
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @see https://www.baeldung.com/httpclient-post-http-request
	 */
	protected String post(String method, List<NameValuePair> params) {
		String result = null;

		try {
			HttpPost httpPost = new HttpPost(getURLFromMethod(method));
	
			httpPost.setEntity(new UrlEncodedFormEntity(params));
	
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

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		
		String response = post("sessions", params);
		
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
	
	/**
	 * Retrieve all Root Folders 	
	 * <strong>Method</strong>: GET
	 * <strong>URL</strong>: /v6/folders/rootfolders
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#operation/getRootFolders 
	 */
	public List<OTMMAsset> retrieveAllRootFolders(String sessionId){
		List<OTMMAsset> assets = null;
		
		String response = get("folders/rootfolders", sessionId);
		
		if(response != null) {
			try {
				JSONObject json = new JSONObject(response);
				if (json != null) {
					assets = new LinkedList<OTMMAsset>();
					
					JSONArray jsonArray = json.getJSONObject("folders_resource").getJSONArray("folder_list");
					
					JSONObject colObj = null;
					String id, name  = null;
				    for (int i = 0, size = jsonArray.length(); i < size; i++) {
				    	colObj = (JSONObject) jsonArray.get(i);
				    	id = colObj.getString("asset_id");
				    	name = colObj.getString("name");
				    	
				    	//TODO add all the properties to the OTMMAsset object
				    	
				    	OTMMAsset asset = new OTMMAsset(id, name);
				    					    	
				    	assets.add(asset);
					}
				}
			} catch (JSONException e) {
				logger.error("/otmmapi/v" + version + "/folders/rootfolders (Response to JSON conversion) ", e);
			}
		}
		
		return assets;
	}
	
	/**
	 * Retrieve all children (Assets and Folders) of a Folder
	 * <strong>Method</strong>: GET
	 * <strong>URL</strong>: /v6/folders/{id}/children
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#operation/getChildren
	 */
	public List<OTMMAsset> retrieveAllChildrenOfAFolder(String sessionId, String folderId){
		List<OTMMAsset> assets = null;
		
		String response = get("folders/" + folderId +"/children", sessionId);
		
		if(response != null) {
			try {
				JSONObject json = new JSONObject(response);
				if (json != null) {
					assets = new LinkedList<OTMMAsset>();
					
					JSONArray jsonArray = json.getJSONObject("folder_children").getJSONArray("asset_list");
					
					JSONObject colObj = null;
					String id, name  = null;
				    for (int i = 0, size = jsonArray.length(); i < size; i++) {
				    	colObj = (JSONObject) jsonArray.get(i);
				    	id = colObj.getString("asset_id");
				    	name = colObj.getString("name");
				    	
				    	// TODO add all the properties to the OTMMAsset object
				    	// TODO refactor: extract a method to generate the asset list
				    	
				    	OTMMAsset asset = new OTMMAsset(id, name);
				    					    	
				    	assets.add(asset);
					}
				}
			} catch (JSONException e) {
				logger.error("/otmmapi/v" + version + "/folders/" + folderId + "/childrens (Response to JSON conversion) ", e);
			}
		}
		
		return assets;
	}
	
	/**
	 * <strong>Create Asset(s)</strong> with the specified asset representation or import template. 
	 * This should be a multipart/form-data request. Please use the utf-8 charset 
	 * encoding so that any unicode characters in names of the attached files are 
	 * interpreted properly. The operation creates a job and returns the job handle. 
	 * Using the job id in job handle, you can query the 'jobs' resource for completion status.
	 * 
	 * If only an asset representation is specified the user is required to have 
	 * Apply/Remove Security Policy permission for all security policies assigned. 
	 * 
	 * If only an import template is specified the user is not required to have 
	 * Apply/Remove Security Policy permission for the security policies inherited 
	 * from the import template. 
	 * 
	 * If both an asset representation and an import template are specified the metadata, 
	 * security policies, and categories populated into the assets will be determined by 
	 * the asset representation, but the user will only be required to have 
	 * Apply/Remove Security Policy permission for the security policies not present 
	 * in the import template.
	 * 
	 * <strong>Method</strong>: POST
	 * <strong>URL</strong>: /v6/assets
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#operation/createAsset
	 */
	public String createAssets(String sessionId, String folderId, Path path) {
		return null;
	}
	
	/**
	 * Get list of collections for current user
	 * <strong>Method</strong>: GET
	 * <strong>URL</strong>: /otmmapi/v6/collections
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#operation/getUserCollections
	 **/
	public Map<String, OTMMCollection> getListOfCollectionForCurrentUser(String sessionId) {
		Map<String, OTMMCollection> collections = null;
		
		String response = get("collections", sessionId);
		
		if(response != null) {
			try {
				JSONObject json = new JSONObject(response);
				if (json != null) {
					collections = new HashMap<String, OTMMCollection>();
					
					JSONArray jsonArray = json.getJSONObject("collection_resource").getJSONArray("collection");
					
					JSONObject colObj = null;
					String id, name, ownerName = null;
				    for (int i = 0, size = jsonArray.length(); i < size; i++) {
				    	colObj = (JSONObject) jsonArray.get(i);
				    	id = colObj.getString("id");
				    	name = colObj.getString("name");
				    	ownerName = colObj.getString("owner_name");
				    	
				    	OTMMCollection col = new OTMMCollection(id, name);
				    	col.setOwnerName(ownerName);
				    	
				    	collections.put(name, col);
					}
				}
			} catch (JSONException e) {
				logger.error("/otmmapi/v" + version + "/collections (Response to JSON conversion) ", e);
			}
		}
		
		return collections;
	}
	
	/**
	 * Retrieve all assets of a Collection
	 * <strong>Method</strong>: GET
	 * <strong>URL</strong>: /v6/collections/{id}/assets
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @param collectionId - OTMM Collection identifier
	 * @return
	 */
	public List<OTMMAsset> retrieveAllAssetsOfACollection(String sessionId, String collectionId) {
		List<OTMMAsset> assets = null;
		
		String response = get("collections/" + collectionId + "/assets", sessionId);
		
		if(response != null) {
			try {
				JSONObject json = new JSONObject(response);
				if (json != null) {
					assets = new LinkedList<OTMMAsset>();
					
					JSONArray jsonArray = json.getJSONObject("assets_resource").getJSONArray("asset_list");
					
					JSONObject assetObj, assetContentInfo, masterContent = null;
					String id, name, mimeType, deliveryServiceURL = null;
				    for (int i = 0, size = jsonArray.length(); i < size; i++) {
				    	assetObj = (JSONObject) jsonArray.get(i);
				    	
				    	assetContentInfo =  assetObj.getJSONObject("asset_content_info");	
				    	masterContent = assetContentInfo.getJSONObject("master_content"); 
				    	id = masterContent.getString("id");
				    	name = masterContent.getString("name");
				    	mimeType = masterContent.getString("mime_type");
				    	
				    	deliveryServiceURL = assetObj.getString("delivery_service_url");
				    	
				    	OTMMAsset asset = new OTMMAsset(id, name);
				    	asset.setMimeType(mimeType);
				    	asset.setDeliveryServiceURL(deliveryServiceURL);
				    	
				    	assets.add(asset);
					}
				}
			} catch (JSONException e) {
				logger.error("/otmmapi/v" + version + "/collections (Response to JSON conversion) ", e);
			}
		}
		
		
		return assets;
	}
	
	protected void finalize() throws Throwable {  
		if (client != null) {
			client.close();
		}
	}
}
