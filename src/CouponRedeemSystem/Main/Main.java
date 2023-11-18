package CouponRedeemSystem.Main;

import CouponRedeemSystem.Page.CouponManagerPage;
import java.io.IOException;
import java.text.ParseException;

public class Main {

  public static void main(String[] args) throws IOException, ParseException {
    //new HomePage().execute();
    new CouponManagerPage().execute();
    // Account testUser = new Account("hendry", 20, 213,"2003-01-01");
    // AccountManager.getInstance().create(testUser);
    // testUser.addPoints(1000);
    // testUser.deductPoints(50);
    // testUser.addCouponID("testCoupon");
    // testUser.addCouponID("testCoupon2");
    // testUser.deleteCouponID("testCoupon");
    // AccountManager.getInstance().update(testUser);

    // Account testUser2 = AccountManager.getInstance().getAccount("tony");
    // System.out.println(testUser2.getUserName() + testUser2.getTelNo());
  }
}
