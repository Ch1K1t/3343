package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;

// TODO: Auto-generated Javadoc
/**
 * The Class PurchasableCoupon.
 */
public class PurchasableCoupon extends Coupon {

  /**
   * Instantiates a new purchasable coupon.
   *
   * @param couponCode the coupon code
   * @param intrinsicValue the intrinsic value
   * @param purchasingValue the purchasing value
   * @param shop the shop
   * @param owner the owner
   * @param active the active
   * @param type the type
   * @param expirationDate the expiration date
   */
  public PurchasableCoupon(
    String couponCode,
    double intrinsicValue,
    double purchasingValue,
    Shop shop,
    Account owner,
    boolean active,
    String type,
    String expirationDate
  ) {
    super(
      couponCode,
      intrinsicValue,
      purchasingValue,
      shop,
      owner,
      active,
      type,
      expirationDate
    );
  }
}
