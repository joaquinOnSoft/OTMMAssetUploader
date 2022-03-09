package com.opentext.teamsite.sc.api.otmm;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opentext.teamsite.sc.api.otmm.beans.OTMMAsset;
import com.opentext.teamsite.sc.api.otmm.beans.OTMMCollection;

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
		
		OTMMAPIWrapper wrapper = new OTMMAPIWrapper(urlBase, version);
		
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

}
