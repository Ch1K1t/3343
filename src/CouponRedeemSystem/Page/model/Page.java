package CouponRedeemSystem.Page.model;

import CouponRedeemSystem.Account.AccountManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public abstract class Page {

  protected static Scanner s;

  public Page() {
    if (s == null) {
      s = new Scanner(System.in);
    }
  }

  public void createAccount(String type) {
    try {
      System.out.println();
      System.out.println("Please input your username:");
      String username = s.nextLine();
      System.out.println();
      System.out.println("Please input your password:");
      String password = s.nextLine();

      AccountManager accountManager = AccountManager.getInstance();
      accountManager.createAccount(username, password);

      System.out.println("Please input your age:");
      String ageStr;
      boolean isValidAge;
      do {
        ageStr = s.nextLine();
        isValidAge = ageStr.matches("[0-9]+");
        if (!isValidAge) {
          System.out.println("Please input a valid age:");
        }
      } while (!isValidAge);
      int age = Integer.parseInt(ageStr);

      System.out.println("Please input your telNo:");
      String telNoStr;
      boolean isValidTelNo;
      do {
        telNoStr = s.nextLine();
        isValidTelNo = telNoStr.matches("[0-9]{8}");
        if (!isValidTelNo) {
          System.out.println("Please input a valid telNo:");
        }
      } while (!isValidTelNo);
      int telNo = Integer.parseInt(telNoStr);

      System.out.println("Please input your dob:");
      String dob = s.nextLine();

      accountManager.createAccInfo(username, "user", age, telNo, dob);
    } catch (NumberFormatException | IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  public void exit() {
    System.out.println("Thank you for using Coupon Redeem System");
    System.out.println("Goodbye");
    System.out.println();
    s.close();
    System.exit(0);
  }
}
