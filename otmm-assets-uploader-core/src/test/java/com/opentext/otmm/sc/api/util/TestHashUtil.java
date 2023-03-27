package com.opentext.otmm.sc.api.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class TestHashUtil {

	@Test
	public void hash() {
		String hash = HashUtil.hash("otmm-api.properties");
		
		assertNotNull(hash);
		assertEquals("61e63dc48522e3683e2f31ff16f2697ceeed381fb0da87788f9aaea52fb42e4f", hash);
	}
}
