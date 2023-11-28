package CouponRedeemSystem.Test;

import CouponRedeemSystem.Test.model.MainTest;
import org.junit.Assert;
import org.junit.Test;

public class EncryptTest extends MainTest {

  @Test
  public void encryptionTest() {
    String result = encryptionManager.encryption(textDecrypted);

    Assert.assertEquals(textEncrypted, result);
  }

  @Test
  public void decryptionTest() {
    String result = encryptionManager.decryption(textEncrypted);

    Assert.assertEquals(textDecrypted, result);
  }
}
