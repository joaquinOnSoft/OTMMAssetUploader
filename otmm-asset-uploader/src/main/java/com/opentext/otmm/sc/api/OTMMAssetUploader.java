package com.opentext.otmm.sc.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opentext.otmm.sc.api.util.FileUtil;

public class OTMMAssetUploader {
	protected static final Logger log = LogManager.getLogger(OTMMAssetUploader.class);

	
	public static void main(String[] args) {
		String otmmPath = null;
		File asset = null;
		Properties prop = new Properties();
		InputStream file = null;

		Options options = new Options();

		Option assetConfig = new Option("a", "asset", true, "Path to asset (image or video) to be uploaded");		
		assetConfig.setRequired(true);
		options.addOption(assetConfig);		
		
		Option actionConfig = new Option("c", "config", true, "Define config file path");		
		actionConfig.setRequired(true);
		options.addOption(actionConfig);
		
		Option actionPath = new Option("p", "path", true, "Path in OTMM to store the asset, e.g. \"Public Folders\\Stock\\Stock Travel\\Paris\"");
		actionPath.setRequired(true);
		options.addOption(actionPath);
		
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {			
			cmd = parser.parse(options, args);
			
			if (cmd.hasOption("path")) {
				otmmPath = cmd.getOptionValue("path");
			}
			
			if(cmd.hasOption("asset")) {
				asset = new File(cmd.getOptionValue("asset"));
			}
			
			if (cmd.hasOption("config")) {
				String configFilePath = cmd.getOptionValue("config");

				if(FileUtil.isFile(configFilePath)) {
					file = new FileInputStream(configFilePath);
					prop.load(file);										
				}
			}
			
			if(asset != null && asset.isFile() && prop != null) {
				String url = prop.getProperty("url");
				String version = prop.getProperty("version", "6");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");
				
				if(url != null && version != null && user != null && password != null) {
					OTMMAPIHelper helper = new OTMMAPIHelper(url, version, user, password);
					String folderId = helper.retrieveFolderIdFromPath(otmmPath);
					
					if(folderId == null) {
						System.err.println("Invalid OTMM path: " + otmmPath);
					}
					else {
						String jobId = helper.createAsset(folderId, asset);
						
						System.out.println("Import process launched. Job Id: " + jobId);
					}
				}
				else {
					System.err.println("Invalid configuration parameters in `properties` file.");
				}
			}

		}
		catch (IOException e) {
			log.error(e.getMessage());
			System.err.println(e.getMessage());
			
		}
		catch (org.apache.commons.cli.ParseException e) {
			log.error(e.getMessage());
			
			formatter.printHelp("java -jar file.jar --config/-c 'config file path' --path \"Public Folders\\Stock\\Stock Travel\\Paris\"", options);

			System.exit(-1);	
		}
		catch (NumberFormatException e) {
			log.error(e.getMessage());
			
			System.out.println("Invalid version number in config file");
			System.exit(-1);
		}
		finally {
			if (file != null) {
				try {
					file.close();
				} 
				catch (IOException e2) {
					log.error(e2.getMessage());
					System.err.println(e2.getMessage());
					System.exit(-1);
				}
			}
		}
	}
}
