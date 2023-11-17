package CouponRedeemSystem.Main;

import CouponRedeemSystem.Page.CouponManagerPage;
import CouponRedeemSystem.Page.HomePage;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

  public static void main(String[] args) throws IOException {
    // CouponManager manager = CouponManager.getInstance();
    // manager.create(new NormalCoupon(1230, null, new Date(), "17897"));
    // CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
    // JSONObject json = jsonFileManager.searchJSON("17897.json", null);
    // System.out.println(json);

    new HomePage().execute();
  }
}
