package com.opentext.otmm.sc.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.beans.OTMMAsset;
import com.opentext.otmm.sc.api.beans.OTMMFolder;
import com.opentext.otmm.sc.api.util.DateUtil;

class TestOTMMAPIFolders extends TestOTMMAPI {

	private static final int DEFAULT_ASSET_POLICY = 2;
	private static final String ARTESIA_BASIC_FOLDER = "ARTESIA.BASIC.FOLDER";
	
	private static final String ARTESIA_FIELD_ASSET_DESCRIPTION = "ARTESIA.FIELD.ASSET DESCRIPTION";
	private static final String METADATA_FIELD = "com.artesia.metadata.MetadataField";
	
	private static OTMMAPIFolders wrapper = null;
	
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
	void createAFolder() {
		OTMMFolder folder = new OTMMFolder(null, "Test" + DateUtil.nowToUTC());
		folder.setContainerType(ARTESIA_BASIC_FOLDER);
		folder.addSecurityPolicie(DEFAULT_ASSET_POLICY);
		
		OTMMMetadataElement metatadaElement = new OTMMMetadataElement(ARTESIA_FIELD_ASSET_DESCRIPTION, METADATA_FIELD);
		metatadaElement.addValue("string",  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ");
		folder.addMetadataElement(metatadaElement);
				
		folder = wrapper.createAFolder(sessionId, prop.getProperty("folder.id.my.folders.sample"), folder);
		assertNotNull(folder);
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
