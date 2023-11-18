package CouponRedeemSystem.Main;

import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import net.sf.json.JSONObject;

public class Main {

  public static void main(String[] args) throws IOException, ParseException {
    CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
    CouponManager manager = CouponManager.getInstance();
    manager.create(
      new RedeemableCoupon(0, null, new Date(), "798", false),
      "Redeemable"
    );
    JSONObject json = jsonFileManager.searchJSON("09823403.json", null);
    System.out.println(json);
    // new HomePage().execute();

    /*
    Account testUser = new Account("hendry", 20, 213,"2003-01-01");
    AccountManager.getInstance().create(testUser);
    testUser.addPoints(1000);
    testUser.deductPoints(50);
    testUser.addCouponID("testCoupon");
    testUser.addCouponID("testCoupon2");
    testUser.deleteCouponID("testCoupon");
    AccountManager.getInstance().update(testUser);
    */

    // Account testUser2 = AccountManager.getInstance().getAccount("tony");
    // System.out.println(testUser2.getUserName() + testUser2.getTelNo());
  }
}
