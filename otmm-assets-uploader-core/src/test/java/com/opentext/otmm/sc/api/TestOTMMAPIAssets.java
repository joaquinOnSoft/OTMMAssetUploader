package com.opentext.otmm.sc.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.util.FileUtil;

class TestOTMMAPIAssets extends TestOTMMAPI {

	private static final String DEFAULT_OTMM_API_VERSION = "6";
	private static OTMMAPIAssets wrapper = null;
	private static final String MANIFEST = "{\"upload_manifest\":{\"master_files\":[{\"file\":{\"file_name\":\"otmm-api.properties\"}}]}}";
	private static final String ASSET = "{\"asset_resource\":{\"asset\":{\"metadata\":{\"name\":\"otmm-api.properties\",\"id\":\"61e63dc48522e3683e2f31ff16f2697ceeed381fb0da87788f9aaea52fb42e4f\"},\"metadata_model_id\":\"ARTESIA.MODEL.DEFAULT\",\"asset_id\":\"61e63dc48522e3683e2f31ff16f2697ceeed381fb0da87788f9aaea52fb42e4f\",\"security_policy_list\":[{\"id\":\"2\"}]}}}";
	
	@BeforeEach
	void setupBeforeEach() {				
		wrapper = new OTMMAPIAssets(prop.getProperty("url"), prop.getProperty("version", DEFAULT_OTMM_API_VERSION));	
		
		sessionId = wrapper.createSession(prop.getProperty("user"), prop.getProperty("password"));		
	}
	
	
	@Test 
	void createAssets() {		
		File kittenImg = FileUtil.getFileFromResources("the-red-or-white-cat-i-on-white-studio.jpg");
		
		String jobId = wrapper.createAsset(sessionId, prop.getProperty("folder.id.my.folders.sample"), kittenImg);
		
		assertNotNull(jobId);		
	}
	
	@Test
	void getManifest() {
		File file = FileUtil.getFileFromResources("otmm-api.properties");
		
		assertNotNull(file);
		
		String json = wrapper.getManifest(file);
		assertNotNull(json);
		assertEquals(MANIFEST, json);;		
	}
	
	@Test
	void getAsset() {
		File file = FileUtil.getFileFromResources("otmm-api.properties");
		
		assertNotNull(file);
		
		String json = wrapper.getAsset(file);
		assertNotNull(json);
		assertEquals(ASSET, json);;		
	}
}
