package CouponRedeemSystem.Main;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Discount.DiscountManager;
import CouponRedeemSystem.Page.AdminPage;
import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.Page.ShopManagerPage;
import CouponRedeemSystem.Page.StaffPage;
import CouponRedeemSystem.Page.UserPage;
import CouponRedeemSystem.Shop.ShopManager;
import java.io.File;

public class Main {

  public static void initializeSystem() {
    AccountManager.getInstance().generateDemoAccount();
    ShopManager.getInstance().generateDemoShop();
    CouponManager.getInstance().generateDemoCoupon();
    DiscountManager.getInstance().generateDemoDiscount();
  }

  public static void initializeTest() {
    String[] dirList = new String[] {
      "Account",
      "Coupon/Purchasable",
      "Coupon/Redeemable",
      "Shop",
      "Discount",
      "Password",
    };

    for (String dir : dirList) {
      File[] fileList = new File("Data/" + dir).listFiles();
      for (File file : fileList) {
        file.delete();
      }
    }
  }

  public static void main(String[] args) {
    // new HomePage().execute();
    // new AdminPage().execute();
    // new ShopManagerPage().execute();
    // new StaffPage("staff1").execute();
    // new UserPage("user").execute();
    initializeSystem();
    // initializeTest();
  }
}
