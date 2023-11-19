package CouponRedeemSystem.Main;

import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.Page.UserPage;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.IOException;
import java.text.ParseException;
import net.sf.json.JSONObject;

public class Main {

  public static void main(String[] args) throws IOException, ParseException {
    // new HomePage().execute();
    new UserPage("test01").execute();

  }
}
