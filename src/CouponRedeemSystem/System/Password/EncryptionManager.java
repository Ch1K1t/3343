package CouponRedeemSystem.System.Password;

import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

// TODO: Auto-generated Javadoc
/**
 * The Class EncryptionManager.
 */
public class EncryptionManager {

  /** The instance. */
  private static EncryptionManager instance;

  /** The encoding. */
  private String encoding = "utf-8";

  /** The key str. */
  private String keyStr = "3343334333433343";

  /** The iv. */
  private String IV = "3343334333433343";

  /** The key. */
  private SecretKeySpec key;

  /** The cipher. */
  private Cipher cipher;

  /**
   * Instantiates a new encryption manager.
   */
  private EncryptionManager() {
    try {
      this.key = new SecretKeySpec(keyStr.getBytes(), "AES");
      this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Gets the single instance of EncryptionManager.
   *
   * @return single instance of EncryptionManager
   */
  public static EncryptionManager getInstance() {
    if (instance == null) {
      instance = new EncryptionManager();
    }

    return instance;
  }

  /**
   * Encryption.
   *
   * @param textToEncrypt the text to encrypt
   * @return the string
   */
  public String encryption(String textToEncrypt) {
    try {
      byte[] text = textToEncrypt.getBytes(encoding);
      cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));
      String textEncrypted = Base64
        .getEncoder()
        .encodeToString(cipher.doFinal(text));

      return textEncrypted;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  /**
   * Decryption.
   *
   * @param textToDecrypt the text to decrypt
   * @return the string
   */
  public String decryption(String textToDecrypt) {
    try {
      cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));
      byte[] textDecrypted = cipher.doFinal(
        Base64.getDecoder().decode(textToDecrypt)
      );
      String result = new String(textDecrypted);

      return result;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }
}
