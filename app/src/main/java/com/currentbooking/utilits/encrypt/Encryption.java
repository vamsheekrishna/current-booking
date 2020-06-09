package com.currentbooking.utilits.encrypt;

public interface Encryption {

    
	String encrypt(String encryptionSTR);
    String decrypt(String decryptionSTR);
    
    void setKey(String key);
    

}
