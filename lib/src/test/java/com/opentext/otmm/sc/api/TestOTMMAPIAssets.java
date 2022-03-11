package com.opentext.otmm.sc.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.util.FileUtil;

class TestOTMMAPIAssets extends TestOTMMAPI {

	private static OTMMAPIAssets wrapper = null;
	private static final String MANIFEST = "{\"upload_manifest\":{\"master_files\":[{\"file\":{\"file_name\":\"otmm-api.properties\"}}]}}";
	
	@BeforeEach
	void setupBeforeEach() {				
		wrapper = new OTMMAPIAssets(prop.getProperty("url"), prop.getProperty("version", "6"));	
		
		sessionId = wrapper.createSession(prop.getProperty("user"), prop.getProperty("password"));		
	}
	
	@Test
	void getManifest() {
		File file = FileUtil.getFileFromResources("otmm-api.properties");
		
		assertNotNull(file);
		
		String json = wrapper.getManifest(file);
		assertNotNull(json);
		assertEquals(MANIFEST, json);;		
	}
}
