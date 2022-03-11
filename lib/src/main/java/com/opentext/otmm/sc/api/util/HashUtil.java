package com.opentext.otmm.sc.api.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HashUtil {
	protected static final Logger log = LogManager.getLogger(FileUtil.class);
	
	/**
	 * Generate a hash code (using SHA-256) for a given string
	 * @param str - String to generate a hash from it
	 * @return Hash
	 * @see https://stackoverflow.com/questions/2624192/good-hash-function-for-strings
	 */
	public static String hash(String str) {
		String stringHash = null;
		MessageDigest messageDigest = null;
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			log.error("Unable to initialize message digest: ", e);
		}
		if(messageDigest != null) {
			messageDigest.update(str.getBytes());
			stringHash = new String(messageDigest.digest());
		}
		
		return stringHash;
	}
}
