package com.opentext.otmm.sc.api;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opentext.otmm.sc.api.beans.OTMMAsset;
import com.opentext.otmm.sc.api.beans.OTMMCollection;

public class OTMMAPIHelper {
	
	private String urlBase;
	private int version;
	private String username; 
	private String password;
	
	static final Logger logger = LogManager.getLogger(OTMMAPIHelper.class);
	
	/**
	 * Initialize the OTMM API helper 
	 * @param urlBase - OTMM API URL base, i.e. http://mydomainexmple.com:11090
	 * @param username - OTMM API user name 
	 * @param password - OTMM API user password
	 * 
	 * <strong>NOTE:</strong> This constructor assume OTMM API version 6
	 */	
	public OTMMAPIHelper(String urlBase, String username, String password) {
		this(urlBase, 6, username, password);
	}
	
	public OTMMAPIHelper(String urlBase, String version, String username, String password) throws NumberFormatException {
		this(urlBase, Integer.parseInt(version), username, password);
	}
	
	/**
	 * Initialize the OTMM API helper 
	 * @param urlBase - OTMM API URL base, i.e. https://mydomainexmple.com:11090
	 * @param version - OTMM API version
	 * @param username - OTMM API user name 
	 * @param password - OTMM API user password
	 */
	public OTMMAPIHelper(String urlBase, int version, String username, String password) {
		this.urlBase = urlBase;
		this.version = version;
		this.username = username;
		this.password = password;
	}
	
	public List<OTMMAsset> retrieveAllAssetsOfACollectionByName(String collectionName){
		List<OTMMAsset> assets = null;
		
		OTMMAPICollections wrapper = new OTMMAPICollections(urlBase, version);
		
		String sessionId = wrapper.createSession(username, password);
		if(sessionId != null) {
			Map<String, OTMMCollection> collections = wrapper.getListOfCollectionForCurrentUser(sessionId);
			
			if (collections != null) {
				OTMMCollection col = collections.get(collectionName);
				
				if (col != null) {
					assets = wrapper.retrieveAllAssetsOfACollection(sessionId, col.getId());
				}
				else {
					logger.debug("No assets in collection '" + collectionName + "'");
				}
			}
			else {
				logger.debug("No collections retrieved for current user");
			}
		}
		else {
			logger.debug("OTMM API Session not initialized");
		}
		
		return assets;
	}
	
	/**
	 * Retrieve folder identifier from an OTMM folder path, e.g.
	 * "Public Folders\Stock\Stock Travel\Paris"
	 * @param path - Folder path
	 * @return folder identifier, null if path doesn't exists.
	 */
	public String retrieveFolderIdFromPath(String path) {
		String folderId = null;
		String currentFolderId = null;
		
		List<OTMMAsset> assets = null;
		int nAssets = 0;
		
		boolean found = true;
		
		if (path != null) {
			String[] folders = path.split("\\\\");
			
			if(folders != null && folders.length > 0) {
				int nFolders = folders.length;
				
				OTMMAPIFolders wrapper = new OTMMAPIFolders(urlBase, version);
				
				String sessionId = wrapper.createSession(username, password);
				
				for(int i=0; i < nFolders && found; i++) {
					logger.debug("Path segment: " + folders[i]);

					if(i == 0) {
						assets = wrapper.retrieveAllRootFolders(sessionId);
					}
					else {
						assets = wrapper.retrieveAllChildrenOfAFolder(sessionId, currentFolderId);
					}
					
					found = false;
					if(assets != null) {
						nAssets = assets.size();
						
						for (int j=0; j < nAssets && !found; j++) {
							logger.debug("Evaluating folder: " + assets.get(j).getName());
							
							if (assets.get(j).getName().compareTo(folders[i]) == 0) {
								found = true;
								currentFolderId = assets.get(j).getId();
							}
						}
					}
				}
				
				if(found) {
					folderId = currentFolderId;
				}
			}			
		}
		
		return folderId;
	}
	
	public String createAssetInPath(String otmmPath, File assetFile){
		String jobId = null;
		
		if(otmmPath != null) {
			String folderId = retrieveFolderIdFromPath(otmmPath);
			logger.debug("Folder id: " + folderId);
			
			if(folderId != null) {
				jobId = createAsset(folderId, assetFile);
			}
		}
		
		return jobId;		
	}	
	
	public String createAsset(String folderId, File assetFile){
		String jobId = null;
		OTMMAPIAssets wrapper = new OTMMAPIAssets(urlBase, version);
		
		String sessionId = wrapper.createSession(username, password);
		if(sessionId != null) {
			jobId = wrapper.createAsset(sessionId, folderId, assetFile);
		}
		return jobId;
	}
}
