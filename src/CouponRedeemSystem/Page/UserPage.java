package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Page.model.Page;
import java.util.List;

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
    CouponManager couponManager = CouponManager.getInstance();

    System.out.println();
    System.out.println("Your balance is " + account.getPoints());

    System.out.println();
    System.out.println("The available coupons are:");
    List<Coupon> couponList = couponManager.getPurchasableCouponList();
    for (Coupon coupon : couponList) {
      System.out.println(
        String.format("%-" + 15 + "s", "Code: " + coupon.getCouponCode()) +
        "Required Points: " +
        coupon.getPoints()
      );
    }

    String couponID = strInput("coupon's code");
    boolean isSuccess = account.pointsToCoupon(couponID);

    if (isSuccess) {
      System.out.println();
      System.out.println("Purchase successfully");
    } else {
      System.out.println();
      System.out.println("Purchase failed");
    }
  }

  public void redeemCoupon() {
    String couponID = strInput("coupon's code");
    boolean isSuccess = account.couponToPoints(couponID);

    if (isSuccess) {
      System.out.println();
      System.out.println("Redeem successfully");
    } else {
      System.out.println();
      System.out.println("Redeem failed");
    }
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
