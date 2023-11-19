package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Page.model.Page;
import java.io.IOException;
import java.text.ParseException;

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
      System.out.println("Please input the coupon's id:");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void redeemCoupon() {
    
  }

  public void execute() {
    String cmd;

    do {
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
