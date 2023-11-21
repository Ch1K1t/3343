package CouponRedeemSystem.Main;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Page.AdminPage;
import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.Page.ShopManagerPage;
import CouponRedeemSystem.Page.StaffPage;
import CouponRedeemSystem.Page.UserPage;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.LazyDynaBean;

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
    // new AdminPage().execute();
    // new ShopManagerPage().execute();
    // new StaffPage().execute();
    // new UserPage("user").execute();

    // initializeCoupon();
    // initializeUser();
    // ShopManager shopManager = ShopManager.getInstance();
    // shopManager.RemoveShop("shop1");
  }
}
