package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Account.model.Account;
import CouponRedeemSystem.Coupon.CouponManager;
import CouponRedeemSystem.Coupon.model.Coupon;
import CouponRedeemSystem.Page.model.Page;
import java.util.HashMap;
import java.util.Map;

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

    Map<String, Runnable> cmdMap = new HashMap<>();
    cmdMap.put("1", () -> createAccount("Admin"));
    cmdMap.put("2", () -> createAccount("Shop Manager"));
    cmdMap.put("3", () -> createAccount("Staff"));
    cmdMap.put("4", () -> createAccount("User"));
    cmdMap.put("5", this::deleteAccount);
    cmdMap.put("6", this::createRedeemableCoupon);
    cmdMap.put("7", () -> System.out.println("Signout successfully"));
    cmdMap.put("8", this::exit);

    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();
      cmdExecute(cmdMap, cmd);
    } while (!cmd.equals("7"));
  }
}
