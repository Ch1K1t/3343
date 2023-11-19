package CouponRedeemSystem.Role.model;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.model.Coupon;

public class Customer extends Role{

    @Override
    public void couponToPoints(String couponId, Account account) {
        // TODO Auto-generated method stub
        Coupon.couponToPoints(couponId, account);
    } 
}
