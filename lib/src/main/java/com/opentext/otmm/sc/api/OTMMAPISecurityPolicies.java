package com.opentext.otmm.sc.api;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opentext.otmm.sc.api.beans.OTMMSecurityPolicy;

public class OTMMAPISecurityPolicies extends OTMMAPI {

	public OTMMAPISecurityPolicies(String urlBase) {
		super(urlBase);
	}
	
	public OTMMAPISecurityPolicies(String urlBase, String version) {
		super(urlBase, version);
	}

	public OTMMAPISecurityPolicies(String urlBase, int version) {
		super(urlBase, version);
	}	

	/**
	 * <strong>Retrieve all Security Policies</strong>
	 * Retrieve all Security Policies. Optionally only Security Policies 
	 * which the user has edit permission for may be retrieved.
	 * <strong>Method</strong>: GET
	 * <strong>URL</strong>: /v6/securitypolicies
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#tag/securitypolicies
	 **/
	public List<OTMMSecurityPolicy> retrieveAllSecurityPolicies(String sessionId) {
		List<OTMMSecurityPolicy> policies = null;
		
		String response = get("securitypolicies", sessionId);
		
		if(response != null) {
			try {
				JSONObject json = new JSONObject(response);
				if (json != null) {
					policies = new LinkedList<OTMMSecurityPolicy>();
					
					JSONArray jsonArray = json.getJSONObject("security_policies_resource").getJSONArray("security_policy_list");
					
					JSONObject colObj = null;					
				    for (int i = 0, size = jsonArray.length(); i < size; i++) {
				    	colObj = (JSONObject) jsonArray.get(i);
				    	
				    	OTMMSecurityPolicy policy = new OTMMSecurityPolicy(colObj.get("id").toString(), colObj.get("name").toString());
				    	if(hasValue(colObj, "create_date")) {
				    		policy.setCreateDate(colObj.getString("create_date"));
				    	}
				    	if(hasValue(colObj, "description")) {
				    		policy.setDescription(colObj.getString("description"));
				    	}
				    	if(hasValue(colObj, "last_update_date")) {
				    		policy.setLastUpdateDate(colObj.get("last_update_date").toString());
				    	}
				    	if(hasValue(colObj, "last_updated_by")) {
				    		policy.setLastUpdatedBy(colObj.getString("last_updated_by"));
				    	}
				    	if(hasValue(colObj, "ownership_type")) {
				    		policy.setOwnership_type(colObj.getString("ownership_type"));
				    	}
				    	if(hasValue(colObj, "status")) {
				    		policy.setStatus(colObj.getString("status"));
				    	}
				    	
				    	policies.add(policy);
					}
				}
			} catch (JSONException e) {
				logger.error("/otmmapi/v" + version + "/collections (Response to JSON conversion) ", e);
			}
		}
		
		return policies;
	}	
	
	private boolean hasValue(JSONObject colObj, String fieldName) {
		String value = colObj.optString(fieldName);
		
		return value != null && value.compareTo("") != 0; 
	}
}
