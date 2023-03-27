package com.opentext.otmm.sc.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;

import com.opentext.otmm.sc.api.util.FileUtil;

public class TestOTMMAPI{
	
	protected static final String FOLDER_ID_MY_FOLDERS = "1001N";
	protected static final String FOLDER_ID_MY_FOLDERS_SAMPLE ="ecd640e8b624eaa194f09398b989450c494a3d3b";
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
			System.err.println("Properties file not found");
		}
		catch (IOException e) {
			System.err.println("Properties file: " + e.getMessage());
		}				
	}
}
