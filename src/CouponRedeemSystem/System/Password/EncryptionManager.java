package CouponRedeemSystem.System.Password;

import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionManager {

  private static EncryptionManager instance;

  private String encoding = "utf-8";

  private String keyStr = "3343334333433343";

  private String IV = "3343334333433343";

  private SecretKeySpec key;

  private Cipher cipher;

  private EncryptionManager() {
    try {
      this.key = generateKey();
      this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
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

  private SecretKeySpec generateKey() {
    return new SecretKeySpec(keyStr.getBytes(), "AES");
  }

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
