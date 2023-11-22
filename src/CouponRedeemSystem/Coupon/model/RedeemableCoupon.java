package CouponRedeemSystem.Coupon.model;

public class RedeemableCoupon extends Coupon {

  public RedeemableCoupon(
    String couponCode,
    double intrinsicValue,
    boolean active,
    String type,
    String expirationDate
  ) {
    super(couponCode, intrinsicValue, active, type, expirationDate);
  }
}
