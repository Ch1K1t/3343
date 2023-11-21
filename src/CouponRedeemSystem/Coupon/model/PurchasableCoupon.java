package CouponRedeemSystem.Coupon.model;

public class PurchasableCoupon extends Coupon {

  public PurchasableCoupon(
    double intrinsicValue,
    String shop,
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
