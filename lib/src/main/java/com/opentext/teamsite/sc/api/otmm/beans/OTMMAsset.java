package com.opentext.teamsite.sc.api.otmm.beans;

public class OTMMAsset {
	private String id;
	private String name;
	private String mimeType;
	private String deliveryServiceURL;
	
	public OTMMAsset(String id, String name) {
		this.id = id;
		this.name = name;
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mime_type) {
		this.mimeType = mime_type;
	}
	
	public String getDeliveryServiceURL() {
		return deliveryServiceURL;
	}
	
	public void setDeliveryServiceURL(String deliveryServiceURL) {
		this.deliveryServiceURL = deliveryServiceURL;
	}	
}
