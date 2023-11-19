package CouponRedeemSystem.Role.model;

import java.io.IOException;
import java.text.ParseException;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Shop.model.Shop;

public class Staff extends Role{
    private Shop workingIn;

    public Staff(Shop shop) {
        this.workingIn = shop;
    }
    
    //To be added in staffPage
    public Coupon createRedeemableCoupon(String couponCode, double value, String shopName) throws IOException, ParseException {
        CouponManager couponManager = CouponManager.getInstance();
        Coupon coupon = null;

        //create a new coupon in coupon manager
        couponManager.create(couponCode, value, null, 
            workingIn, "Redeemable", null);
        coupon = couponManager.getCoupon(couponCode);

        //add the coupon to the shop  
        workingIn.appendApprovedCoupons(couponCode, coupon);
        return coupon;
    }

    @Override
    public void couponToPoints(String couponId, Account account) {
        // TODO Auto-generated method stub
        System.out.println("Staff cannot convert coupon to points.");
    }
}
