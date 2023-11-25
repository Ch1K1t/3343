package CouponRedeemSystem.Test.model;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import CouponRedeemSystem.System.Password.EncryptionManager;
import CouponRedeemSystem.System.Password.PasswordManager;
import java.text.SimpleDateFormat;
import org.junit.Assert;
import org.junit.Test;

public class MainTest {

  protected final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  protected final EncryptionManager encryptionManager = EncryptionManager.getInstance();
  protected final PasswordManager passwordManager = PasswordManager.getInstance();
  protected final AccountManager accountManager = AccountManager.getInstance();
  protected final CouponManager couponManager = CouponManager.getInstance();
  protected final ShopManager shopManager = ShopManager.getInstance();
  protected final DiscountManager discountManager = DiscountManager.getInstance();
  protected final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

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
