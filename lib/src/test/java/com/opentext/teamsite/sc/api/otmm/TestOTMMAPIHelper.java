package com.opentext.teamsite.sc.api.otmm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;



import com.opentext.teamsite.sc.api.otmm.beans.OTMMAsset;

class TestOTMMAPIHelper extends TestAbstractOTMMAPI {
	
	private static OTMMAPIHelper apiHelper;
	
	@BeforeAll
	static void init() {
		apiHelper = new OTMMAPIHelper(prop.getProperty("url"), 
				prop.getProperty("user"), 
				prop.getProperty("password"));		
	}
	
	@Test
	void retrieveAllAssetsOfACollectionByName() {		
		List<OTMMAsset> assets = apiHelper.retrieveAllAssetsOfACollectionByName("Auckland");
		
		assertNotNull(assets);
		assertTrue(assets.size() > 0);
	}
	
	@Test
	void retrieveFolderIdFromPath(){
		String folderId = apiHelper.retrieveFolderIdFromPath("Public Folders\\Stock\\Stock Travel\\Paris");
		
		assertNotNull(folderId);
	}
}
