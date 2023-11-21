package CouponRedeemSystem.Main;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Page.AdminPage;
import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.Page.ShopManagerPage;
import CouponRedeemSystem.Page.StaffPage;
import CouponRedeemSystem.Page.UserPage;
import CouponRedeemSystem.Shop.ShopManager;

public class Main {

  public static void initializeUser() {
    AccountManager accountManager = AccountManager.getInstance();
    accountManager.generateDemoAccount();
  }

  public static void initializeCoupon() {
    CouponManager couponManager = CouponManager.getInstance();
    couponManager.generateDemoCoupon();
  }

  public static void initializeShop() {
    ShopManager shopManager = ShopManager.getInstance();
    shopManager.generateDemoShop();
  }

  public static void main(String[] args) {
    // new HomePage().execute();
    // new AdminPage().execute();
    // new ShopManagerPage().execute();
    // new StaffPage("staff").execute();
    // new UserPage("user").execute();

    // initializeCoupon();
    // initializeUser();

  }
}
