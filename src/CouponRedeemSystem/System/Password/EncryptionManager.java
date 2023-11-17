package CouponRedeemSystem.System.Password;

import java.security.NoSuchAlgorithmException;

import javax.crypto.*;

import org.apache.commons.logging.Log;

public class EncryptionManager {
	private static EncryptionManager instance;
	
	private SecretKey key;
	
	private Cipher cipher;
	
	private EncryptionManager() {
		try {
			this.key = generateKey("AES");
			this.cipher = Cipher.getInstance("AES");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static EncryptionManager getInstance() {
		if (instance == null) {
			instance = new EncryptionManager();
		}
		
		return instance;
	}
	
	private SecretKey generateKey(String encryptType) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptType);
			SecretKey key = keyGenerator.generateKey();
			return key;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public byte[] encryption(String textToEncrypt) {
		try {
			byte[] text = textToEncrypt.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] textEncrypted = cipher.doFinal(text);
			
			return textEncrypted;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public String decryption(byte[] textToDecrypt) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] textDecrypted = cipher.doFinal(textToDecrypt);
			String result = new String(textDecrypted);
			
			return result;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
