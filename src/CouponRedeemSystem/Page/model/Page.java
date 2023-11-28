package CouponRedeemSystem.Page.model;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Shop.ShopManager;
import CouponRedeemSystem.Shop.model.Shop;
import CouponRedeemSystem.System.Util.Util;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public abstract class Page {

  protected static Scanner s;

  public Page() {
    if (s == null) {
      s = new Scanner(System.in);
    }
  }

  public String strInput(String fieldName) {
    System.out.println();
    System.out.println("Please input the " + fieldName + ":");
    String input;
    do {
      input = s.nextLine();
      if (input.isEmpty()) {
        System.out.println("Please input the " + fieldName + ":");
      }
    } while (input.isEmpty());
    return input;
  }

  public int intInput(String fieldName) {
    System.out.println();
    System.out.println("Please input the " + fieldName + ":");
    String input;
    boolean isInt;
    do {
      input = s.nextLine();
      isInt = input.matches("\\d+");
      if (!isInt) {
        System.out.println("Invalid value, please input again:");
      }
    } while (!isInt);

    return Integer.parseInt(input);
  }

  public double doubleInput(String fieldName) {
    System.out.println();
    System.out.println("Please input the " + fieldName + ":");
    String input;
    boolean isDouble;
    do {
      input = s.nextLine();
      isDouble = input.matches("\\d+(\\.\\d+)?");
      if (!isDouble) {
        System.out.println("Invalid value, please input again:");
      }
    } while (!isDouble);
    return Double.parseDouble(input);
  }

  public String telInput() {
    System.out.println();
    System.out.println("Please input telephone number:");
    String telNo;
    boolean isValid;
    do {
      telNo = s.nextLine();
      isValid = telNo.matches("[0-9]{8}");
      if (!isValid) {
        System.out.println("Please input a 8 digit telephone number:");
      }
    } while (!isValid);
    return telNo;
  }

  public String beforeDateInput(String fieldName) {
    try {
      System.out.println();
      System.out.println("Please input " + fieldName + " (dd/MM/yyyy):");
      String dateStr;
      boolean isDate = false;
      boolean isBeforeToday = false;
      do {
        dateStr = s.nextLine();
        isDate =
          dateStr.matches(
            "^((0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|2[0-1])[0-9]{2})$"
          );
        if (!isDate) {
          System.out.println("Invalid date format, please input again:");
          continue;
        }
        Date date = Util.sdf.parse(dateStr);
        isBeforeToday = date.compareTo(new Date()) <= 0;
        if (!isBeforeToday) {
          System.out.println("Date must be before today, please input again:");
          continue;
        }
      } while (!isDate || !isBeforeToday);

      return dateStr;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public String afterDateInput(String fieldName) {
    try {
      System.out.println();
      System.out.println("Please input " + fieldName + " (dd/MM/yyyy):");
      String dateStr;
      boolean isDate = false;
      boolean isAfterToday = false;
      do {
        dateStr = s.nextLine();
        isDate =
          dateStr.matches(
            "^((0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|2[0-1])[0-9]{2})$"
          );
        if (!isDate) {
          System.out.println("Invalid date format, please input again:");
          continue;
        }
        Date date = Util.sdf.parse(dateStr);
        isAfterToday = date.compareTo(new Date()) >= 0;
        if (!isAfterToday) {
          System.out.println("Date must be after today, please input again:");
          continue;
        }
      } while (!isDate || !isAfterToday);

      return dateStr;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void createAccount(String role) {
    AccountManager accountManager = AccountManager.getInstance();

    String username = strInput("user name");

    String password = strInput("password");

    boolean isPwdCreated = accountManager.createPassword(username, password);
    if (!isPwdCreated) {
      System.out.println();
      System.out.println("User already exists");
      return;
    }

    if (role.equals("Staff")) {
      ShopManager shopManager = ShopManager.getInstance();

      String shopName;
      Shop shop;
      do {
        shopName = strInput("shop name");
        shop = shopManager.getShop(shopName);
        if (shop == null) {
          System.out.println();
          System.out.println("Shop " + shopName + " does not exist!");
        }
      } while (shop == null);
      shop.addStaff(username);
      shopManager.updateShop(shop);

      accountManager.createAccount(username, role);
      System.out.println();
      System.out.println("Account created");

      return;
    } else if (!role.equals("User")) {
      accountManager.createAccount(username, role);
      System.out.println();
      System.out.println("Account created");

      return;
    }

    String telNo = telInput();

    String dob = beforeDateInput("date of birth");

    accountManager.createAccount(username, role, telNo, dob);
    System.out.println();
    System.out.println("Account created");
  }

  public void exit() {
    System.out.println();
    System.out.println("Thank you for using Coupon Redeem System");
    System.out.println("Goodbye");
    System.out.println();
    s.close();
    System.exit(0);
  }

  public static void setS(Scanner s) {
    Page.s = s;
  }
}
