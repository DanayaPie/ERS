package com.revature.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
	
	// hexadecimal = hexArray
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	// convert bytes To String
	private static String bytesToStringHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2 ];
		for (int i = 0; i < bytes.length; i++) {
			int j = bytes[i] & 0xFF;
			hexChars[i * 2] = hexArray[j >>> 4];
			hexChars[i * 2 + 1] = hexArray[j & 0x0F];
		}
			
		return new String(hexChars);
	}

	// create salt
	public static byte[] createSalt() {

		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		
		return bytes;
	}

	// hash password
	public static String hashPassword(String password, String algorithm, byte[] salt) throws NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		digest.update(salt);
		byte[] hash = digest.digest(password.getBytes());
		
		return bytesToStringHex(hash);
	}
	
}