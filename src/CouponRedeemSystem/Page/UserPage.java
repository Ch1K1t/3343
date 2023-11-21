package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import CouponRedeemSystem.System.File.CRSJsonFileManager;
import java.io.File;
import net.sf.json.JSONObject;

public class UserPage extends Page {

  private Account account;

  public UserPage(String username) {
    AccountManager accountManager = AccountManager.getInstance();
    this.account = accountManager.getAccount(username);
  }

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Check Remaining Points");
    System.out.println("2. Purchase Coupon");
    System.out.println("3. Redeem Coupon");
    System.out.println("4. Signout");
    System.out.println("5. Exit");
    System.out.println();
  }

  public void checkRemainingPoints() {
    System.out.println();
    System.out.println("Your remaining points is: " + account.getPoints());
  }

  public void purchaseCoupon() {
    CRSJsonFileManager jsonFileManager = CRSJsonFileManager.getInstance();

    System.out.println();
    System.out.println("Your balance is " + account.getPoints());
    File[] fileArr = new File("Data/Coupon/Purchasable").listFiles();

    System.out.println();
    System.out.println("The available coupons are:");
    for (File file : fileArr) {
      JSONObject jsonObject = jsonFileManager.convertFileTextToJSON(file);
      if (jsonObject.getString("owner").equals("null")) {
        System.out.println(
          String.format(
            "%-" + 15 + "s",
            "Code: " + jsonObject.getString("couponCode")
          ) +
          "Required Points: " +
          jsonObject.getString("points")
        );
      }
    }

    String couponID = strInput("coupon's code");
    account.pointsToCoupon(couponID);
    System.out.println();
    System.out.println("Purchase successfully");
  }

  public void redeemCoupon() {
    String couponID = strInput("coupon's code");
    account.couponToPoints(couponID);
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
        case "1":
          checkRemainingPoints();
          break;
        case "2":
          purchaseCoupon();
          break;
        case "3":
          redeemCoupon();
          break;
        case "4":
          System.out.println("Signout successfully");
          break;
        case "5":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("4"));
  }
}
