package com.opentext.otmm.sc.api.beans;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opentext.otmm.sc.api.OTMMMetadataElement;
import com.opentext.otmm.sc.api.OTMMMetadataElementValue;

public class OTMMFolder {
	private String id;
	private String name;
	private String type;
	private String containerType;
	private List<Integer> securityPolicies;
	private List<OTMMMetadataElement> metadataElements;
	private String metadataModelId;

	public OTMMFolder(String name) {
		this(name, null, null);
	}
	
	public OTMMFolder(String name, String type) {
		this(name, type, null);
	}
	

	public OTMMFolder(String name, String type, String id) {
		this.id = id;
		this.name = name;
		this.type = type;
		securityPolicies = new LinkedList<Integer>();
		metadataElements = new LinkedList<OTMMMetadataElement>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public List<Integer> getSecurityPolicies() {
		return securityPolicies;
	}

	public void addSecurityPolicie(int securityPolicy) {
		securityPolicies.add(securityPolicy);
	}	
	
	public void setSecurityPolicies(List<Integer> securityPolicies) {
		this.securityPolicies = securityPolicies;
	}

	public List<OTMMMetadataElement> getMetadataElements() {
		return metadataElements;
	}

	public void setMetadataElements(List<OTMMMetadataElement> metadataElements) {
		this.metadataElements = metadataElements;
	}
	
	public void addMetadataElement(OTMMMetadataElement metadataElement) {
		metadataElements.add(metadataElement);
	}	
	
	public String getMetadataModelId() {
		return metadataModelId;
	}

	public void setMetadataModelId(String metadataModelId) {
		this.metadataModelId = metadataModelId;
	}		
	
	public String toJSON() {
		JSONObject jsonObj = new JSONObject();
		JSONObject folderResource = new JSONObject();
		JSONObject folder = new JSONObject();
		
		JSONArray policies = new JSONArray();
		
		for(int policy: securityPolicies) {
			JSONObject policyObj = new JSONObject();
			policyObj.put("id", policy);
			
			policies.put(policyObj);
		}
		
		folder.put("name", name);
		folder.put("container_type_id", containerType);
		folder.put("security_policy_list", policies);
		
		JSONObject metadata = new JSONObject();
		JSONArray metadataElementList = new JSONArray();
		for(OTMMMetadataElement metadataElement: metadataElements) {
			JSONObject metadataElementObj = new JSONObject();
			metadataElementObj.put("id", metadataElement.getId());
			metadataElementObj.put("type", metadataElement.getType());
			
			for(OTMMMetadataElementValue metadataElementValue: metadataElement.getValues()) {
				JSONObject metadataElementValueL1Obj = new JSONObject();
				
				JSONObject metadataElementValueL2Obj = new JSONObject();
				metadataElementValueL2Obj.put("type", metadataElementValue.getType());
				metadataElementValueL2Obj.put("value", metadataElementValue.getValue());
				metadataElementValueL1Obj.put("value", metadataElementValueL2Obj);
				
				metadataElementObj.put("value", metadataElementValueL1Obj);
			}
			
			metadataElementList.put(metadataElementObj);
		}		
		
		metadata.put("metadata_element_list", metadataElementList);
		
		folder.put("metadata", metadata);
		folder.put("metadata_model_id", metadataModelId);
		
		folderResource.put("folder", folder);
		
		jsonObj.put("folder_resource", folderResource);
		
		return jsonObj.toString();
	}

}
