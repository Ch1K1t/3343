package CouponRedeemSystem.Main;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Page.AdminPage;
import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.Page.StaffPage;
import CouponRedeemSystem.Page.UserPage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

  public static void initializeCoupon() {
    CouponManager couponManager = CouponManager.getInstance();
    couponManager.generateDemoCoupon();
  }

  public static void initializeUser() {
    AccountManager accountManager = AccountManager.getInstance();
    accountManager.generateDemoAccount();
  }

  public static void main(String[] args) {
    // new HomePage().execute();
    // new UserPage("user").execute();
    // new AdminPage().execute();
    // new StaffPage().execute();

    // initializeCoupon();
    // initializeUser();
  }
}
