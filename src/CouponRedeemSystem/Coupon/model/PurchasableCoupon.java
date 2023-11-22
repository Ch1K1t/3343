package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Shop.model.Shop;

public class PurchasableCoupon extends Coupon {

  public PurchasableCoupon(
    double intrinsicValue,
    Shop shop,
    String expirationDate,
    String couponCode,
    boolean active,
    double points,
    String type
  ) {
    super(
      intrinsicValue,
      shop,
      expirationDate,
      couponCode,
      active,
      points,
      type
    );
  }
}
