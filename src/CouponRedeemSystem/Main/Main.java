package CouponRedeemSystem.Main;

import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.NormalCoupon;
import CouponRedeemSystem.Page.HomePage;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class Main {

  public static void main(String[] args) throws IOException {
    CouponManager manager = CouponManager.getInstance();
    manager.create(new NormalCoupon(1230, null, new Date(), "17897"));
    new HomePage().execute();
  }
}
