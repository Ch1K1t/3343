package CouponRedeemSystem.Coupon.model;

// TODO: Auto-generated Javadoc
/**
 * The Class RedeemableCoupon.
 */
public class RedeemableCoupon extends Coupon {

  /**
   * Instantiates a new redeemable coupon.
   *
   * @param couponCode the coupon code
   * @param intrinsicValue the intrinsic value
   * @param active the active
   * @param type the type
   * @param expirationDate the expiration date
   */
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
