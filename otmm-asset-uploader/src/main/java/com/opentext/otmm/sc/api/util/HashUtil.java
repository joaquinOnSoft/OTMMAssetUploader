package com.opentext.otmm.sc.api.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HashUtil {
	protected static final Logger log = LogManager.getLogger(HashUtil.class);
	
	/**
	 * Generate a hash code (using SHA-256) for a given string
	 * @param str - String to generate a hash from it
	 * @return SHA-256 hash
	 * @see https://stackoverflow.com/questions/2624192/good-hash-function-for-strings
	 * @see https://www.baeldung.com/sha-256-hashing-java
	 */
	public static String hash(String str) {
		String stringHash = null;
		
		if(str != null) {
			MessageDigest messageDigest = null;
			
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				log.error("Unable to initialize message digest: ", e);
			}
			if(messageDigest != null) {
				messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
				//stringHash = new String(messageDigest.digest());
				stringHash = bytesToHex(messageDigest.digest());
			}
		}
		
		return stringHash;
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
}
