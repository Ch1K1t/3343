package CouponRedeemSystem.Main;

import CouponRedeemSystem.Page.UserPage;
import java.io.IOException;
import java.text.ParseException;

public class Main {

  public static void main(String[] args) throws IOException, ParseException {
    // new HomePage().execute();
    new UserPage("test01").execute();
  }
}
