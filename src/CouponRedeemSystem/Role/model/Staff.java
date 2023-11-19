package CouponRedeemSystem.Role.model;

import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Shop.model.Shop;

public class Staff extends Role{
    Shop workingIn;

    public Staff(Shop shop) {
        this.workingIn = shop;
    }
    
    //To be added in staffPage
    public Coupon createRedeemableCoupon(String) {
        CouponManager couponManager = CouponManager.getInstance();

        //create a new coupon in coupon manager
        couponManager.create(null, 0, null, workingIn, null, null)
        //add the coupon to the shop
    }
}
