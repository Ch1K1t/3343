package CouponRedeemSystem.Main;

import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Page.AdminPage;
import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.Page.StaffPage;
import CouponRedeemSystem.Page.UserPage;

public class Main {

  public static void initializeCoupon() {
    CouponManager couponManager = CouponManager.getInstance();
    couponManager.generateDemoCoupon();
  }

  public static void main(String[] args) {
    // new HomePage().execute();
    // new UserPage("user").execute();
    // new AdminPage().execute();
    // new StaffPage().execute();

    // initializeCoupon();
  }
}
