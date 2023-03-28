package com.opentext.otmm.sc.api.beans;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class TestOTMMSecurityPolicy {

	@Test
	public void setLastUpdateDate() {
		OTMMSecurityPolicy policy = new OTMMSecurityPolicy();

		policy.setLastUpdateDate("2022-05-02T17:18:35Z");

		assertNotNull(policy.getLastUpdateDate());	
	}
}
