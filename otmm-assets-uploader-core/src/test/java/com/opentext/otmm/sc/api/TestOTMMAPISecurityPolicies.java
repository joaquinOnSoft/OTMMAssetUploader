package com.opentext.otmm.sc.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.beans.OTMMSecurityPolicy;

class TestOTMMAPISecurityPolicies extends TestOTMMAPI {

	private static OTMMAPISecurityPolicies wrapper = null;
	
	@BeforeEach
	void setupBeforeEach() {				
		wrapper = new OTMMAPISecurityPolicies(prop.getProperty("url"), prop.getProperty("version", "6"));	
		
		sessionId = wrapper.createSession(prop.getProperty("user"), prop.getProperty("password"));		
	}
	
	@Test
	void retrieveAllSecurityPolicies() {
		List<OTMMSecurityPolicy> policies = wrapper.retrieveAllSecurityPolicies(sessionId);
		assertNotNull(policies);
		assertTrue(policies.size() > 0);		
	}
}
