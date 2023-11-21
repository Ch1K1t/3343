package CouponRedeemSystem.Coupon.model;

public class RedeemableCoupon extends Coupon {

  public RedeemableCoupon(
    double intrinsicValue,
    String expirationDate,
    String couponCode,
    boolean active,
    String type
  ) {
    super(intrinsicValue, expirationDate, couponCode, active, type);
  }
}
