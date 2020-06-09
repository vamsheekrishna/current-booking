package com.currentbooking.utilits.encrypt;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class EncryptionHelper {

	
	public static String getSHA256(String value) throws SecurityException {
		    String hashValue = "";
		    try {
		      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		      hashValue = byteArray2Hex(messageDigest.digest(value.getBytes()));
		    }
		    catch (NoSuchAlgorithmException e) {
		      throw new SecurityException(e.getMessage(), e);
		    }
		    
		    return hashValue;
		  }
	public static String getMD5(String value) throws SecurityException {
	    String hashValue = "";
	    try {
	      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	      hashValue = byteArray2Hex(messageDigest.digest(value.getBytes()));
	    }
	    catch (NoSuchAlgorithmException e) {
	      throw new SecurityException(e.getMessage(), e);
	    }
	    
	    return hashValue;
	  }
	private static String byteArray2Hex(byte[] hash)
	   {
	     Formatter formatter = new Formatter();
	     byte[] arrayOfByte = hash;
	     int j = hash.length;
	     for (int i = 0; i < j; i++) {
	     	byte b = arrayOfByte[i];
	       
	       formatter.format("%02x", Byte.valueOf(b));
	     }
	     return formatter.toString();
	   }
}
