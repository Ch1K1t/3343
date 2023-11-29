package CouponRedeemSystem.Test.model;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.EncryptionManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import CouponRedeemSystem.System.Util.Util;
import java.io.File;
import java.util.Date;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;

public class MainTest {

  // All instances
  protected final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  protected final EncryptionManager encryptionManager = EncryptionManager.getInstance();
  protected final PasswordManager passwordManager = PasswordManager.getInstance();
  protected final AccountManager accountManager = AccountManager.getInstance();
  protected final CouponManager couponManager = CouponManager.getInstance();
  protected final ShopManager shopManager = ShopManager.getInstance();
  protected final DiscountManager discountManager = DiscountManager.getInstance();

  protected final String fieldName = "test field";
  // CRSJsonFileManager attributes
  protected final String dirName = "Test";
  protected final String fileName = "test";
  protected final File directory = new File("Data/" + dirName);

  // EncryptionManager attributes
  protected final String textDecrypted = "123456";
  protected final String textEncrypted = "A/f9RMarL3ZrzpzGjTylKQ==";

  // Account attributes
  protected final String userName = "accountTest";
  protected final String password = "passwordTest";
  protected final String age = "23";
  protected final String telNo = "12345678";
  protected final String dateOfBirth = "01/01/2000";

  // Coupon attributes
  protected final double intrinsicValue = 10.0;
  protected final double purchasingValue = 15.0;
  protected final String couponExpirationDate = Util.sdf.format(
    DateUtils.addYears(new Date(), 1)
  );

  // Shop attributes
  protected final String shopName = "shopTest";
  protected final String staffName = "staffTest";

  // Discount attributes
  protected final String discountName = "discountTest";
  protected final double value = 10.0;
  protected final String startDateStr = "01/01/2024";
  protected final int day = 10;
  protected final String expireDateStr = "11/01/2024";

  @Test
  public void testJsonFileManagerInstance() {
    CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
    Assert.assertEquals(true, jsonFileManager != null);
  }

  @Test
  public void testEncryptionManagerInstance() {
    EncryptionManager encryptionManager = EncryptionManager.getInstance();
    Assert.assertEquals(true, encryptionManager != null);
  }

  @Test
  public void testPasswordManagerInstance() {
    PasswordManager passwordManager = PasswordManager.getInstance();
    Assert.assertEquals(true, passwordManager != null);
  }

  @Test
  public void testAccountManagerInstance() {
    AccountManager accountManager = AccountManager.getInstance();
    Assert.assertEquals(true, accountManager != null);
  }

  @Test
  public void testCouponManagerInstance() {
    CouponManager couponManager = CouponManager.getInstance();
    Assert.assertEquals(true, couponManager != null);
  }

  @Test
  public void testShopManagerInstance() {
    ShopManager shopManager = ShopManager.getInstance();
    Assert.assertEquals(true, shopManager != null);
  }

  @Test
  public void testDiscountManagerInstance() {
    DiscountManager discountManager = DiscountManager.getInstance();
    Assert.assertEquals(true, discountManager != null);
  }
}
