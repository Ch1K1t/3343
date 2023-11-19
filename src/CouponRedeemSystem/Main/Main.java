package CouponRedeemSystem.Main;

import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import net.sf.json.JSONObject;

public class Main {

  public static void create() throws ParseException {
    CouponManager couponManager = CouponManager.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    Date expirationDate = sdf.parse("11/11/2022");
    couponManager.create(
      "123",
      (double) 10,
      expirationDate,
      null,
      "Redeemable",
      (double) -1
    );
  }

  /*
    Purchasable
    123
    11/11/2022
    123
  */

  public static void delete() throws IOException {
    CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

    // Scanner s = new Scanner(System.in);

    String code = "123";
    // String code = s.nextLine();

    String type = "Redeemable";
    // Object objType = jsonFileManager.searchJSON(code + ".json").get("type");
    JSONObject jsonObject = jsonFileManager.searchJSON(code + ".json");
    System.out.println(jsonObject);

    // String strType = jsonObject.getString("type");

    // String type = s.nextLine();

    jsonFileManager.deleteJSON("Coupon/" + type, code);

    // s.close();
  }

  public static void main(String[] args) throws IOException, ParseException {
    // new HomePage().execute();
    // new AdminPage().execute();

    // create();
    delete();
  }
}
