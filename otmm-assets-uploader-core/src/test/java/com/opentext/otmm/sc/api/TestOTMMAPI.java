package com.opentext.otmm.sc.api;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;

import com.opentext.otmm.sc.api.util.FileUtil;

public class TestOTMMAPI{
	
	protected static final String FOLDER_ID_MY_FOLDERS = "1001N";
	protected static final String FOLDER_ID_PUBLIC_FOLDERS = "ARTESIA.PUBLIC.TREEN";
	
	protected static Properties prop = null;
	protected static String sessionId = null;
	
	@BeforeAll
	static void setupBeforeAll() {		
		try {			
	        prop = new Properties();
	        prop.load(FileUtil.getStreamFromResources("otmm-api.properties"));	        
		} 
		catch (FileNotFoundException e) {
			fail("Properties file not found");
		}
		catch (IOException e) {
			fail("Properties file. ", e);
		}				
	}
}
