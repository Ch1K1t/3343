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
      "Purchasable",
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

    Scanner s = new Scanner(System.in);

    String code = "123";
    // String code = s.nextLine();

    // String type = "Purchasable";
    // Object objType = jsonFileManager.searchJSON(code + ".json").get("type");
    JSONObject jsonObject = jsonFileManager.searchJSON(code + ".json");

    String strType = jsonObject.getString("type");

    // String type = s.nextLine();

    // System.out.println(objType.equals("Purchasable"));
    System.out.println(strType.equals("Purchasable"));

    File file = jsonFileManager.createJson("Coupon/" + strType, code);
    System.out.println(file);
    Scanner s2 = new Scanner(file);
    while (s2.hasNextLine()) {
      System.out.println(s2.nextLine());
    }
    file.delete();
    
    s.close();
    s2.close();
  }

  public static void main(String[] args) throws IOException, ParseException {
    // new HomePage().execute();
    // new AdminPage().execute();

    // create();
    // delete();

    CouponManager couponManager = CouponManager.getInstance();
    couponManager.delete("123");
  }
}
