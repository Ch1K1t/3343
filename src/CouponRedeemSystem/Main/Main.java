package CouponRedeemSystem.Main;

import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.IOException;
import net.sf.json.JSONObject;

public class Main {

  public static void main(String[] args) throws IOException {
    new HomePage().execute();
  }
}
