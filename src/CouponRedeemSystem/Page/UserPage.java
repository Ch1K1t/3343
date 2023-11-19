package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Coupon.model.RedeemableCoupon;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import net.sf.json.JSONObject;

public class UserPage extends Page {

  private String username;

  public UserPage(String username) {
    this.username = username;
  }

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Purchase Coupon");
    System.out.println("2. Redeem Coupon");
    System.out.println("3. Signout");
    System.out.println("4. Exit");
    System.out.println();
  }

  public void purchaseCoupon() {
    try {
      AccountManager accountManager = AccountManager.getInstance();
      Account account = accountManager.getAccount(username);
      System.out.println("Your balance is " + account.getPoints());
      File[] fileArr = new File("Data/Coupon/Purchasable").listFiles();

      CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();
      System.out.println("The available coupons are:");
      for (File file : fileArr) {
        JSONObject jsonObject = jsonFileManager.convertFileTextToJSON(file);
        if (jsonObject.getString("owner").equals("null")) {
          System.out.println(
            String.format(
              "%-" + 15 + "s",
              "Code: " + jsonObject.getString("code")
            ) +
            "Required Points: " +
            jsonObject.getString("points")
          );
        }
      }
      System.out.println("Please input the coupon's id:");
      String couponID = s.nextLine();
      Coupon.pointsToCoupon(couponID, account);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void redeemCoupon() {
    try {
      AccountManager accountManager = AccountManager.getInstance();
      Account account = accountManager.getAccount(username);

      System.out.println("Please input the coupon's id:");
      String couponID = s.nextLine();
      Coupon.couponToPoints(couponID, account);
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
        case "1":
          purchaseCoupon();
          break;
        case "2":
          redeemCoupon();
          break;
        case "3":
          System.out.println("Signout successfully");
          break;
        case "4":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("3"));
  }
}
