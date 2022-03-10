package com.opentext.otmm.sc.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.beans.OTMMAsset;

class TestOTMMAPIFolders extends TestOTMMAPI {

	//private static final String FOLDER_ID_MY_FOLDERS = "1001N";
	private static final String FOLDER_ID_PUBLIC_FOLDERS = "ARTESIA.PUBLIC.TREEN";
	
	private static OTMMAPIFolders wrapper = null;
	private static String sessionId = null;
	
	@BeforeEach
	void setupBeforeEach() {				
		wrapper = new OTMMAPIFolders(prop.getProperty("url"), prop.getProperty("version", "6"));	
		
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
	void getURLFromMethod() {
		String url = wrapper.getURLFromMethod("sessions");
		assertNotNull(url);
		assertTrue(url.endsWith("/otmmapi/v6/sessions"));
	}	
	
	@Test 
	void retrieveAllRootFolders(){
		List<OTMMAsset> assets = wrapper.retrieveAllRootFolders(sessionId);
		assertNotNull(assets);
		assertTrue(assets.size() > 0);				
	}
		
	@Test 
	void retrieveAllChildrenOfAFolder(){
		List<OTMMAsset> assets = wrapper.retrieveAllChildrenOfAFolder(sessionId, FOLDER_ID_PUBLIC_FOLDERS);
		assertNotNull(assets);
		assertTrue(assets.size() > 0);				
	}
}
