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

  public String strInput(String fieldName) {
    System.out.println();
    System.out.println("Please input " + fieldName + ":");
    String input;
    do {
      input = s.nextLine();
      if (input.isEmpty()) {
        System.out.println("Please input " + fieldName + ":");
      }
    } while (input.isEmpty());
    return input;
  }

  public int intInput(String fieldName) {
    System.out.println();
    System.out.println("Please input your " + fieldName + ":");
    String input;
    boolean isValid;
    do {
      input = s.nextLine();
      isValid = input.matches("\\d+");
      if (!isValid) {
        System.out.println("Please input a valid " + fieldName + ":");
      }
    } while (!isValid);

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
    boolean isValidTelNo;
    do {
      telNo = s.nextLine();
      isValidTelNo = telNo.matches("[0-9]{8}");
      if (!isValidTelNo) {
        System.out.println("Please input a 8 digit telephone number:");
      }
    } while (!isValidTelNo);
    return telNo;
  }

  public String beforeDateInput(String fieldName) {
    try {
      System.out.println();
      System.out.println("Please input " + fieldName + " (dd/MM/yyyy):");
      String dateStr;
      boolean isDate = false;
      boolean isBeforeToday = false;
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
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
        Date date = sdf.parse(dateStr);
        isBeforeToday = date.before(new Date());
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
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
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
        Date date = sdf.parse(dateStr);
        isAfterToday = date.after(new Date());
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

  public void createAccount(String type) {
    AccountManager accountManager = AccountManager.getInstance();

    String username = strInput("user name");

    String password = strInput("password");

    boolean notExist = accountManager.createAccount(username, password);
    if (!notExist) return;

    if (!type.equals("User")) {
      accountManager.createAccInfo(username, type);
      return;
    }

    int age = intInput("age");

    String telNo = telInput();

    String dob = beforeDateInput("date of birth");

    accountManager.createAccInfo(username, "user", age, telNo, dob);
  }

  public void exit() {
    System.out.println("Thank you for using Coupon Redeem System");
    System.out.println("Goodbye");
    System.out.println();
    s.close();
    System.exit(0);
  }
}
