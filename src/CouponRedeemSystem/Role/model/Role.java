package CouponRedeemSystem.Role.model;

import CouponRedeemSystem.Account.model.Account;

public abstract class Role { //Customer, Staff, Admin
    //Customer: can exchange point, exchange coupon
    //Staff: setup new coupon, transaction
    //Admin: 
    
    public abstract void couponToPoints(String couponId, Account account);
}
