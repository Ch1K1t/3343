package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Shop.model.Shop;
import java.util.Date;

public class PurchasableCoupon extends Coupon {

  public PurchasableCoupon(
    double intrinsicValue,
    Shop shop,
    Date expirationDate,
    String couponCode,
    boolean active
  ) {
    super(intrinsicValue, shop, expirationDate, couponCode, active);
  }
}