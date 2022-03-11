package com.opentext.otmm.sc.api.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class TestHashUtil {

	@Test
	public void hash() {
		String hash = HashUtil.hash("otmm-api.properties");
		
		assertNotNull(hash);
		assertEquals("", hash);
	}
}
