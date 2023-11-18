package CouponRedeemSystem.Main;

import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.IOException;
import java.text.ParseException;

public class Main {

  public static void main(String[] args) throws IOException, ParseException {
    new HomePage().execute();
  }
}
