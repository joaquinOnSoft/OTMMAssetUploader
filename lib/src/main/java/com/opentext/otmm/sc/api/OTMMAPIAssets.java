package com.opentext.otmm.sc.api;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opentext.otmm.sc.api.util.HashUtil;

public class OTMMAPIAssets extends OTMMAPI {
	
	private static final String TEMPLATE_STOCK_IMAGE = "01acfc5f70f34ef84711e0b83161b716e147c87b";
	
	//Default Asset Policy
	private static final String DEFAULT_ASSET_POLICY = "2";
	
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
	public String createAssets(String sessionId, String folderId, List<File> files) {
		List<HttpEntity> entities = new LinkedList<HttpEntity>();
		String result = null;
		
		for(File file: files) {
		    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		    builder.addTextBody("parent_folder_id", folderId);
		    builder.addTextBody("manifest", getManifest(file), ContentType.APPLICATION_JSON);
		    builder.addTextBody("asset_representation", getAsset(file), ContentType.APPLICATION_JSON);
		    builder.addBinaryBody("files", file, ContentType.create("application/octet-stream"), file.getName());
			
		    entities.add(builder.build());
		    
			result = post("assets", getDefaultHeaders(sessionId), entities);
			
			logger.debug("RESULT: " + result); 
			
			// Clean entities for next call (just in case there are more than one file)
			entities.clear();
		}
		
		
		return null;
	}
	
	/***
	 * Generate a JSON with the <strong>manifest</strong> field 
	 * that looks like this:
	 * <pre>
	 * {
	 * 	"upload_manifest": {
	 * 		"master_files": [{
	 * 			"file": {
	 * 				"file_name": "otmm-api.properties"
	 * 			}
	 * 		}]
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param file
	 * @return
	 */
	protected String getManifest(File file) {
		JSONObject jsonObj = new JSONObject();
		JSONObject uploadManifest = new JSONObject();
		JSONArray masterFiles = new JSONArray();
		
		JSONObject fileObj = new JSONObject();		
		JSONObject fileAttributes = new JSONObject();
		fileAttributes.put("file_name", file.getName());
		
		fileObj.put("file", fileAttributes);
		masterFiles.put(fileObj);				
		uploadManifest.put("master_files", masterFiles);
		jsonObj.put("upload_manifest", uploadManifest);
		
		return jsonObj.toString();
	}
	
	/***
	 * Generate a JSON with the <strong>manifest</strong> field 
	 * that looks like this:
	 * <pre>
	 * {
	 * 	"asset_resource": {
	 * 		"metadata": {
	 * 			"name": "otmm-api.properties",
	 * 			"id": "61e63dc48522e3683e2f31ff16f2697ceeed381fb0da87788f9aaea52fb42e4f"
	 * 		},
	 * 		"metadata_model_id": "01acfc5f70f34ef84711e0b83161b716e147c87b",
	 * 		"asset_id": "61e63dc48522e3683e2f31ff16f2697ceeed381fb0da87788f9aaea52fb42e4f",
	 * 		"security_policy_list": [{
	 * 			"id": "2"
	 * 		}]
	 * 	}
	 * }
	 * </pre>
	 * 
	 * @param file
	 * @return
	 */	
	protected String getAsset(File file) {
		String id = HashUtil.hash(file.getName());
		
		JSONObject assetResource = new JSONObject();
		
		JSONObject asset = new JSONObject();
		asset.put("asset_id", id);
		
		JSONObject metadata = new JSONObject();		
		metadata.put("id", id);
		metadata.put("name", file.getName());

		JSONArray securityPolicyList = new JSONArray();
		JSONObject securityPolicy = new JSONObject();
		securityPolicy.put("id", DEFAULT_ASSET_POLICY);
		securityPolicyList.put(securityPolicy);
				
		asset.put("metadata", metadata);
		asset.put("metadata_model_id", TEMPLATE_STOCK_IMAGE);
		asset.put("security_policy_list", securityPolicyList);
		
		assetResource.put("asset_resource", asset);
		
		return assetResource.toString();
	}
}
