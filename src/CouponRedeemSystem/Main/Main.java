package CouponRedeemSystem.Main;

import CouponRedeemSystem.Page.HomePage;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import net.sf.json.JSONObject;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
    JSONObject json = jsonFileManager.searchJSON("09823403.json", null);
    System.out.println(json);
    new HomePage().execute();
  }
}
