package CouponRedeemSystem.Coupon.model;

import java.time.LocalDateTime;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Shop.model.Shop;

public class NormalCoupon extends Coupon {

    public NormalCoupon(double intrinsicValue, Shop shop, LocalDateTime expirationDate, String couponCode,
            Account owner) {
        super(intrinsicValue, shop, expirationDate, couponCode, owner);
    }
    
}
