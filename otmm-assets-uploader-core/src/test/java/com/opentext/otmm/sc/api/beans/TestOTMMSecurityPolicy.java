package com.opentext.otmm.sc.api.beans;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class TestOTMMSecurityPolicy {

	@Test
	public void setLastUpdateDate() {
		OTMMSecurityPolicy policy = new OTMMSecurityPolicy();

		// ERROR com.opentext.otmm.sc.api.util.HashUtil - 
		// Error parsing date java.text.ParseException: Unparseable date: "2022-05-02T17:18:35Z"
		policy.setLastUpdateDate("2022-05-02T17:18:35Z");

		assertNotNull(policy.getLastUpdateDate());	
	}
}
