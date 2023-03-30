package com.opentext.otmm.sc.api;

import java.util.LinkedList;
import java.util.List;

public class OTMMMetadataElement {
	private String id;
	private String type;
	private List<OTMMMetadataElementValue> values = new LinkedList<OTMMMetadataElementValue>();;

	public OTMMMetadataElement() {		
	}
	
	public OTMMMetadataElement(String id, String type) {		
		this.id = id;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<OTMMMetadataElementValue> getValues() {
		return values;
	}

	public void setValues(List<OTMMMetadataElementValue> values) {
		this.values = values;
	}	
	
	public void addValue(OTMMMetadataElementValue value) {
		values.add(value);
	}
	
	public void addValue(String type, String value) {
		values.add(new OTMMMetadataElementValue(type, value));
	}	
}
