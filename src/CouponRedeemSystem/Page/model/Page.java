package CouponRedeemSystem.Page.model;

import CouponRedeemSystem.Account.AccountManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
      AccountManager accountManager = AccountManager.getInstance();

      System.out.println();
      System.out.println("Please input your username:");
      String username = s.nextLine();

      System.out.println();
      System.out.println("Please input your password:");
      String password = s.nextLine();

      accountManager.createAccount(username, password);
      if (type.equals("Admin") || type.equals("Shop Manager")) {
        accountManager.createAccInfo(username, type);
        return;
      }

      System.out.println();
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

      System.out.println();
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

      System.out.println();
      System.out.println("Please input your date of birth (dd/MM/yyyy):");
      String dobStr;
      Date dob;
      boolean isDate;
      boolean isBeforeToday;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
      do {
        dobStr = s.nextLine();
        isDate =
          dobStr.matches(
            "^((0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|2[0-1])[0-9]{2})$"
          );
        dob = sdf.parse(dobStr);
        isBeforeToday = dob.before(new Date());
        if (!isDate || !isBeforeToday) {
          System.out.println("Invalid date, please input again:");
        }
      } while (!isDate || !isBeforeToday);

      accountManager.createAccInfo(username, "user", age, telNo, dobStr);
    } catch (NumberFormatException | ParseException e) {
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
