package com.currentbooking.utilits.encrypt;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;



public  class DESEncryption extends EncryptionKey implements Encryption
{

	
	
	private static KeySpec			keySpec;
	private static SecretKey		secretKey;
	private static IvParameterSpec	iv;
	
	
	
	
	@Override
	public String encrypt(String encryptionSTR) {


		try {
			Cipher ecipher = Cipher.getInstance("DESede/CBC/PKCS5Padding",
					"SunJCE");
			
			
			ecipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

			if (encryptionSTR == null) {
				return null;
			}

			// Encode the string into bytes using utf-8
			byte[] utf8 = encryptionSTR.getBytes(StandardCharsets.UTF_8);

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return new String(Base64.encodeBase64(enc), StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
		
	}

	@Override
	public String decrypt(String decryptionSTR) {
		try {
			Cipher dcipher = Cipher.getInstance("DESede/CBC/PKCS5Padding",
					"SunJCE");
			
			
			
			
			dcipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			if (decryptionSTR == null) {
				return null;
			}

			// Decode base64 to get bytes
			byte[] dec = Base64.decodeBase64(decryptionSTR.getBytes());

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	void getKey(String keyString) {
		
		try {
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest(Base64.decodeBase64(keyString.getBytes(StandardCharsets.UTF_8)));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			keySpec = new DESedeKeySpec(keyBytes);

			secretKey = SecretKeyFactory.getInstance("DESede")
					.generateSecret(keySpec);

			iv = new IvParameterSpec(new byte[8]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setKey(String key) {
		getKey(key);
		
	}

}