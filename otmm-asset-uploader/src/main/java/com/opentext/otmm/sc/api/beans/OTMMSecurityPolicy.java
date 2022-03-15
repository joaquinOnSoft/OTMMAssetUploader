package com.opentext.otmm.sc.api.beans;

import java.text.ParseException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opentext.otmm.sc.api.util.DateUtil;
import com.opentext.otmm.sc.api.util.HashUtil;

public class OTMMSecurityPolicy {
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
	
	private Date createDate;
	private String description;
	private String id;
	private Date lastUpdateDate;
	private String lastUpdatedBy;
	private String name;
	private String ownership_type;
	private String status;
	
	protected static final Logger log = LogManager.getLogger(HashUtil.class);
	
	public OTMMSecurityPolicy() {		
	}

	public OTMMSecurityPolicy(String id, String name) {
		this.id = id;
		this.name = name;
	}
		
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDate(String createDate) {
		try {
			this.createDate = DateUtil.strToDate(createDate, DATE_FORMAT);
		} catch (ParseException e) {
			log.error("Error parsing date", e);
		}
	}	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public void setLastUpdateDate(String lastUpdateDate) {
		try {
			this.lastUpdateDate = DateUtil.strToDate(lastUpdateDate, DATE_FORMAT);
		} catch (ParseException e) {
			log.error("Error parsing date", e);
		}
	}		
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwnership_type() {
		return ownership_type;
	}
	
	public void setOwnership_type(String ownership_type) {
		this.ownership_type = ownership_type;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}	
}
