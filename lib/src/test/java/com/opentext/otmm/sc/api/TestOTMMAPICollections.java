package com.opentext.otmm.sc.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.beans.OTMMAsset;
import com.opentext.otmm.sc.api.beans.OTMMCollection;

class TestOTMMAPICollections extends TestOTMMAPI {

	private static OTMMAPICollections wrapper = null;
	private static String sessionId = null;
	
	@BeforeEach
	void setupBeforeEach() {				
		wrapper = new OTMMAPICollections(prop.getProperty("url"), prop.getProperty("version", "6"));	
		
		sessionId = wrapper.createSession(prop.getProperty("user"), prop.getProperty("password"));		
	}
	
	@Test
	void getListOfCollectionForCurrentUser() {
		Map<String, OTMMCollection> collections = wrapper.getListOfCollectionForCurrentUser(sessionId);
		assertNotNull(collections);
		assertTrue(collections.size() > 0);		
	}

	@Test
	void retrieveAllAssetsOfACollection() {
		List<OTMMAsset> assets = wrapper.retrieveAllAssetsOfACollection(sessionId, "37565d42b090cae4774605bd1c9dd85c75063ad5");
		assertNotNull(assets);
		assertTrue(assets.size() > 0);
	}	
}
