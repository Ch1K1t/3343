package CouponRedeemSystem.Coupon.model;

import java.util.Date;

public class RedeemableCoupon extends Coupon {

  public RedeemableCoupon(
    double intrinsicValue,
    Date expirationDate,
    String couponCode,
    boolean active,
    String type
  ) {
    super(intrinsicValue, expirationDate, couponCode, active, type);
  }
}
