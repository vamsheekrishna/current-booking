package com.currentbooking.utilits.encrypt;

import android.os.Bundle;

import com.currentbooking.utilits.views.BaseActivity;

public class testClient  extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Encryption AESencryption;
		EncryptionHelper objEncryptionHelper = new EncryptionHelper();


		try {
			AESencryption = EncryptionFactory.getEncryptionByName("AES");


			String input = "Rajan";
			String key = "QWRTEfnfdys635";//E-m!tr@2016
			AESencryption.setKey(key);


			String output = AESencryption.encrypt(input);


			System.out.println("des string : " + AESencryption.encrypt(input));
			System.out.println("des string : " + AESencryption.decrypt(output));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}