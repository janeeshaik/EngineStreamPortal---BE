package com.ste.enginestreamportal.util;


import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class PasswordUtils {
	public final static String UBI_ENCRYPTION_PASSWORD = "Z9876&A4321/1824#483034";
	public final static String UBI_SALT = "Esp!@#@b@d1128";

    public static String generateencryptPassword(String password) {
        Base64.Encoder encoder = Base64.getEncoder();       
        String str = encoder.encodeToString(password.getBytes());  
       return str;  
         
         
    }  
    
    public static String generatedecryotPassword(String encodedpassword) {
    	Base64.Decoder decoder = Base64.getDecoder();    
        String dStr = new String(decoder.decode(encodedpassword));  
        return dStr; 
    }
    
    /*public static boolean verifyUserPassword(String providedPassword,
            String securedPassword) {
    	boolean returnValue = false;
        Base64.Encoder encoder = Base64.getEncoder();       
        String str = encoder.encodeToString(providedPassword.getBytes());  
    	returnValue = str.equalsIgnoreCase(securedPassword);
    	return returnValue;
    }*/
    
    public static boolean verifyUserPassword(String providedPassword, String securePassword) {
    	boolean returnValue = false;
    	String decodedPassword = decryptAES256(securePassword);
    	returnValue = decodedPassword.equals(providedPassword);
    	return returnValue;
    }
    
    public static boolean passwordFormatCheck(String password) {
    	boolean returnValue = false;
    	returnValue = password.matches(("^(?=.*[0-9])(?=.*[A-Z])[a-zA-Z0-9]{8,}$"));
    	return returnValue;
    }
    
    //leekendra
    
 // Encryption
 	public static String encryptAES256(String strToEncrypt) {
 		try {
 			byte[] iv = { 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3 };
 			IvParameterSpec ivspec = new IvParameterSpec(iv);

 			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
 			KeySpec spec = new PBEKeySpec(UBI_ENCRYPTION_PASSWORD.toCharArray(), UBI_SALT.getBytes(), 65536, 256);
 			SecretKey tmp = factory.generateSecret(spec);
 			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

 			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
 			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
 			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
 		} catch (Exception e) {
 			System.out.println("Error while encrypting: " + e.toString());
 		}
 		return null;
 	}

 	// Decryption
 	public static String decryptAES256(String strToDecrypt) {
 		try {
 			byte[] iv = { 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3 };
 			IvParameterSpec ivspec = new IvParameterSpec(iv);

 			SecretKeyFactory factory =
 					SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
 			KeySpec spec = new PBEKeySpec(UBI_ENCRYPTION_PASSWORD.toCharArray(), UBI_SALT.getBytes(),
 					65536, 256);
 			SecretKey tmp = factory.generateSecret(spec);
 			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

 			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
 			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
 			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
 		} catch (Exception e) {
 			System.out.println("Error while decrypting: " + e.toString());
 		}
 		return null;
 	}
    
 	public static String getRandomGenaratedPassword() {
		String password = new Random().ints(10, 33, 122).mapToObj(i -> String.valueOf((char) i))
				.collect(Collectors.joining());
		return password;
	}
}

