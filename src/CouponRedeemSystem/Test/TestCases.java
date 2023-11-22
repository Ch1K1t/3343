package CouponRedeemSystem.Test;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.PurchasableCoupon;
import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import org.junit.Assert;
import org.junit.Test;

public class TestCases {

  private final CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
  private final AccountManager accountManager = AccountManager.getInstance();
  private final CouponManager couponManager = CouponManager.getInstance();
  private final ShopManager shopManager = ShopManager.getInstance();
  private final DiscountManager discountManager = DiscountManager.getInstance();

  @Test
  public void testMethod() {
    ShopManager shopManager = ShopManager.getInstance();
    Shop shop1 = shopManager.getShop("shop1");
    Coupon coupon = couponManager.createCoupon(
      "P1",
      10.0,
      15.0,
      shop1,
      "Purchasable",
      "11/11/2025"
    );
    Assert.assertEquals(coupon, null);
    System.out.println(coupon);
  }
}
