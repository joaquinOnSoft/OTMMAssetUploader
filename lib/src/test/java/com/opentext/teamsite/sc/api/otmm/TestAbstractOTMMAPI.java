package com.opentext.teamsite.sc.api.otmm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeAll;

public class TestAbstractOTMMAPI{
	
	protected static Properties prop = null;
	
	@BeforeAll
	static void setupBeforeAll() {		
		try {
			String path= System.getProperty("user.dir");
			String fullPath = path + System.getProperty("file.separator") +"otmm-api.properties";
	        			
	        prop = new Properties();
	        prop.load(new FileInputStream(fullPath));	        
		} 
		catch (FileNotFoundException e) {
			System.err.println("Properties file not found");
		}
		catch (IOException e) {
			System.err.println("Properties file: " + e.getMessage());
		}				
	}
}
