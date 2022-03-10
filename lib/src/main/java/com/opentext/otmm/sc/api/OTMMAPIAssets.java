package com.opentext.otmm.sc.api;

import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class OTMMAPIAssets extends OTMMAPI {
	public OTMMAPIAssets(String urlBase) {
		this(urlBase, DEFAULT_VERSION_NUMBER);
	}

	public OTMMAPIAssets(String urlBase, String version) {
		super(urlBase, version);
	}

	public OTMMAPIAssets(String urlBase, int version) {
		super(urlBase, version);
	}

	/**
	 * <strong>Create Asset(s)</strong> with the specified asset representation or import template. 
	 * This should be a multipart/form-data request. Please use the utf-8 charset 
	 * encoding so that any unicode characters in names of the attached files are 
	 * interpreted properly. The operation creates a job and returns the job handle. 
	 * Using the job id in job handle, you can query the 'jobs' resource for completion status.
	 * 
	 * If only an asset representation is specified the user is required to have 
	 * Apply/Remove Security Policy permission for all security policies assigned. 
	 * 
	 * If only an import template is specified the user is not required to have 
	 * Apply/Remove Security Policy permission for the security policies inherited 
	 * from the import template. 
	 * 
	 * If both an asset representation and an import template are specified the metadata, 
	 * security policies, and categories populated into the assets will be determined by 
	 * the asset representation, but the user will only be required to have 
	 * Apply/Remove Security Policy permission for the security policies not present 
	 * in the import template.
	 * 
	 * <strong>Method</strong>: POST
	 * <strong>URL</strong>: /v6/assets
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#operation/createAsset
	 * @see https://www.baeldung.com/httpclient-post-http-request#post-multipart-request
	 */
	public String createAssets(String sessionId, String folderId, File[] files) {
		
	    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	    builder.addTextBody("files", pathsToJSON(files));
	    
	    HttpEntity multipart = builder.build();
		//TODO complete - Work in progress here!!!
		post("assets", null, sessionId);
		
		
		return null;
	}
	
	protected String pathsToJSON(File[] files) {
		String json = null;
		
		if(files != null && files.length > 0) {
						
			JSONArray filesArray = new JSONArray();			
			
			for(File file: files) {
				JSONObject fileObj = new JSONObject();
				fileObj.put("name", file.getName());
				fileObj.put("value", file.getName());
				
				filesArray.put(file);
			}
		}
		
		return json;
	}
}
