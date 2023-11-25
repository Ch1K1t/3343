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
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

public class Main {

  public static void initializeUser() {
    AccountManager accountManager = AccountManager.getInstance();
    accountManager.generateDemoAccount();
  }

  public static void initializeShop() {
    ShopManager shopManager = ShopManager.getInstance();
    shopManager.generateDemoShop();
  }

  public static void initializeCoupon() {
    CouponManager couponManager = CouponManager.getInstance();
    couponManager.generateDemoCoupon();
  }

  public static void initializeDiscount() {
    DiscountManager discountManager = DiscountManager.getInstance();
    discountManager.generateDemoDiscount();
  }

  public static void main(String[] args) {
    // new HomePage().execute();
    // new AdminPage().execute();
    // new ShopManagerPage().execute();
    // new StaffPage("staff1").execute();
    // new UserPage("user").execute();

    // initializeUser();
    // initializeShop();
    // initializeCoupon();
    // initializeDiscount();

    // JSONObject jsonObject = CRSJsonFileManager
    //   .getInstance()
    //   .searchJSON("ReferenceTable");

    // JSONObject jsonObject2 = jsonObject;
    // if (jsonObject2.get("") == null) {
    //   // if (jsonObject2.getJSONObject("").toString().equals("null")) {

    //   System.out.println("nulla");
    // }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("a", "b");
    System.out.println(jsonObject.get("a").toString());
  }
}
