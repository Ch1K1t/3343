package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;

public class PurchasableCoupon extends Coupon {

  public PurchasableCoupon(
    String couponCode,
    double intrinsicValue,
    double points,
    Shop shop,
    Account owner,
    boolean active,
    String type,
    String expirationDate
  ) {
    super(
      couponCode,
      intrinsicValue,
      points,
      shop,
      owner,
      active,
      type,
      expirationDate
    );
  }
}
