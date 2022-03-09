package com.opentext.teamsite.sc.api.otmm;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opentext.teamsite.sc.api.otmm.beans.OTMMAsset;
import com.opentext.teamsite.sc.api.otmm.beans.OTMMCollection;

class TestOTMMAPIWrapper extends TestAbstractOTMMAPI {

	private static OTMMAPIWrapper wrapper = null;
	private static String sessionId = null;
	
	@BeforeEach
	void setupBeforeEach() {				
		wrapper = new OTMMAPIWrapper(prop.getProperty("url"), prop.getProperty("version", "6"));	
		
		sessionId = wrapper.createSession(prop.getProperty("user"), prop.getProperty("password"));		
	}
	
	@Test
	void createSession() {
		String response = wrapper.createSession(prop.getProperty("user"), prop.getProperty("password"));
		assertNotNull(response);
		
		int intSessionId = -1;
		try {
			intSessionId = Integer.parseInt(response);
		} catch (NumberFormatException e) {
			fail("Invalid session id: " +  intSessionId);
		}
		
		assertTrue(intSessionId > 0);
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
	
	@Test
	void getURLFromMethod() {
		String url = wrapper.getURLFromMethod("sessions");
		assertNotNull(url);
		assertTrue(url.endsWith("/otmmapi/v6/sessions"));
	}
}
