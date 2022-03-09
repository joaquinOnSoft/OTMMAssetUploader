package com.opentext.teamsite.sc.api.otmm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;

import com.opentext.teamsite.sc.api.otmm.util.FileUtil;

public class TestAbstractOTMMAPI{
	
	protected static Properties prop = null;
	
	@BeforeAll
	static void setupBeforeAll() {		
		try {			
	        prop = new Properties();
	        prop.load(FileUtil.getStreamFromResources("otmm-api.propertie"));	        
		} 
		catch (FileNotFoundException e) {
			System.err.println("Properties file not found");
		}
		catch (IOException e) {
			System.err.println("Properties file: " + e.getMessage());
		}				
	}
}
