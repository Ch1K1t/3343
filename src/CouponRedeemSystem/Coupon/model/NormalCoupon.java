package CouponRedeemSystem.Coupon.model;

import CouponRedeemSystem.Shop.model.Shop;
import java.util.Date;

public class NormalCoupon extends Coupon {

  public NormalCoupon(
    double intrinsicValue,
    Shop shop,
    Date expirationDate,
    String couponCode
  ) {
    super(intrinsicValue, shop, expirationDate, couponCode);
  }
}