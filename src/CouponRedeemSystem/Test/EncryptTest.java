package CouponRedeemSystem.Test;

import CouponRedeemSystem.System.Password.EncryptionManager;
import CouponRedeemSystem.Test.model.MainTest;
import org.junit.Assert;
import org.junit.Test;

public class EncryptTest extends MainTest {

  EncryptionManager encryptionManager = EncryptionManager.getInstance();

  @Test
  public void encryptionTest() {
    String textToEncrypt = "123456";
    String expected = "A/f9RMarL3ZrzpzGjTylKQ==";

    String textEncrypted = encryptionManager.encryption(textToEncrypt);
    Assert.assertEquals(expected, textEncrypted);
  }

  @Test
  public void decryptionTest() {
    String textToDecrypt = "A/f9RMarL3ZrzpzGjTylKQ==";
    String expected = "123456";

    String textDecrypted = encryptionManager.decryption(textToDecrypt);
    Assert.assertEquals(expected, textDecrypted);
  }
}
