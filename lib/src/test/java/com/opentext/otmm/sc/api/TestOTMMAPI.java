package com.opentext.otmm.sc.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;

import com.opentext.otmm.sc.api.util.FileUtil;

public class TestOTMMAPI{
	
	protected static Properties prop = null;
	
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
