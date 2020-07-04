package com.currentbooking.utilits.encrypt;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public  class AESEncryption  extends EncryptionKey implements Encryption
{

	private static SecretKeySpec	secretKey;
	private static IvParameterSpec ivParameterSpec ;
	
	
	@Override
	public String encrypt(String encryptionSTR) {
		
		String encode = "";
		try {
			if (encryptionSTR != null) {
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
				byte[] data = cipher.doFinal(encryptionSTR.getBytes(StandardCharsets.UTF_8));
				encode = Base64.encodeToString(data, Base64.DEFAULT);
			}
		} catch (NoSuchPaddingException npe) {
			npe.printStackTrace();
		} catch (NoSuchAlgorithmException nae) {
			nae.printStackTrace();
		} catch (InvalidKeyException ike) {
			ike.printStackTrace();
		} catch (BadPaddingException bpe) {
			bpe.printStackTrace();
		} catch (IllegalBlockSizeException ibe) {
			ibe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encode;

		 
		 
	}

	@Override
	public String decrypt(String decryptionSTR) {
		try {
			if (decryptionSTR != null) {
				//getKey(key); 
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
				byte[] bts = Base64.decode(decryptionSTR, Base64.DEFAULT);
				byte[] decrypted = cipher.doFinal(bts);
				return new String(decrypted).replaceAll("\0", "");
			}
		} catch (NoSuchPaddingException npe) {
			npe.printStackTrace();
		} catch (NoSuchAlgorithmException nae) {
			nae.printStackTrace();
		} catch (InvalidKeyException ike) {
			ike.printStackTrace();
		} catch (BadPaddingException bpe) {
			bpe.printStackTrace();
		} catch (IllegalBlockSizeException ibe) {
			ibe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	void getKey(String myKey) {
		
		byte[]	key;
		MessageDigest sha = null;
		try {
			key = myKey.getBytes(StandardCharsets.UTF_8);
			// System.out.println(key.length);
			sha = MessageDigest.getInstance("SHA-256");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			secretKey = new SecretKeySpec(key, "AES");
			ivParameterSpec=new IvParameterSpec(key);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void setKey(String key) {
		getKey(key);
	}

}