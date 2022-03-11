package com.opentext.otmm.sc.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opentext.otmm.sc.api.beans.OTMMAsset;
import com.opentext.otmm.sc.api.beans.OTMMCollection;

public class OTMMAPICollections extends OTMMAPI {

	public OTMMAPICollections(String urlBase) {
		super(urlBase);
	}
	
	public OTMMAPICollections(String urlBase, String version) {
		super(urlBase, version);
	}

	public OTMMAPICollections(String urlBase, int version) {
		super(urlBase, version);
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
		
		String response = get("collections", getDefaultHeaders(sessionId));
		
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
		
		String response = get("collections/" + collectionId + "/assets", getDefaultHeaders(sessionId));
		
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
}
