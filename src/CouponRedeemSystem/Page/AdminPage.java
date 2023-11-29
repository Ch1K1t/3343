package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Page.model.Page;

public class AdminPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
    System.out.println("1. Create Admin Account");
    System.out.println("2. Create Shop Manager Account");
    System.out.println("3. Create Staff Account");
    System.out.println("4. Create User Account");
    System.out.println("5. Delete Account");
    System.out.println("6. Create Redeemable Coupon");
    System.out.println("7. Signout");
    System.out.println("8. Exit");
    System.out.println();
  }

  public void deleteAccount() {
    AccountManager accountManager = AccountManager.getInstance();

    String username = strInput("user name");
    Account account = accountManager.getAccount(username);
    if (account == null) {
      System.out.println();
      System.out.println("Account not found");
      return;
    }

    accountManager.deleteAccount(account);
    System.out.println();
    System.out.println("Account deleted");
  }

  public void createRedeemableCoupon() {
    CouponManager couponManager = CouponManager.getInstance();

    String couponCode = strInput("coupon's code");
    Coupon coupon = couponManager.getCoupon(couponCode);
    if (coupon != null) {
      System.out.println();
      System.out.println("Coupon already exists");
      return;
    }
    
    double intrinsicValue = doubleInput("coupon's intrinsic value");
    String expirationDate = afterDateInput("coupon's expiration date");

    couponManager.createCoupon(
      couponCode,
      intrinsicValue,
      "Redeemable",
      expirationDate
    );

    System.out.println();
    System.out.println("Coupon created");
  }

  public void execute() {
    String cmd;

    do {
      getInstruction();
      cmd = s.nextLine();

      switch (cmd) {
        case "1":
          createAccount("Admin");
          break;
        case "2":
          createAccount("Shop Manager");
          break;
        case "3":
          createAccount("Staff");
          break;
        case "4":
          createAccount("User");
        case "5":
          deleteAccount();
          break;
        case "6":
          createRedeemableCoupon();
          break;
        case "7":
          System.out.println("Signout successfully");
          break;
        case "8":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("7"));
  }
}
