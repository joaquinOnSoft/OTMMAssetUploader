package com.opentext.otmm.sc.api;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opentext.otmm.sc.api.beans.OTMMAsset;

public class OTMMAPIFolders extends OTMMAPI {

	public OTMMAPIFolders(String urlBase) {
		super(urlBase);
	}
	
	public OTMMAPIFolders(String urlBase, String version) {
		super(urlBase, version);
	}

	public OTMMAPIFolders(String urlBase, int version) {
		super(urlBase, version);
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
}
