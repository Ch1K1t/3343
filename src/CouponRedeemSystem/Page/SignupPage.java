package CouponRedeemSystem.Page;

import CouponRedeemSystem.Account.AccountManager;
import CouponRedeemSystem.Page.model.Page;
import java.io.IOException;
import java.text.ParseException;

public class SignupPage extends Page {

  public void execute() {
    try {
      System.out.println();
      System.out.println("Please input your username:");
      String username = s.nextLine();
      System.out.println("Please input your password:");
      String password = s.nextLine();

      AccountManager accountManager = AccountManager.getInstance();
      accountManager.createAccount(username, password);

      /*
      String userName,
      String role,
      int age,
      int telNo,
      String dob
      */

      System.out.println("Please input your role:");
      String role = s.nextLine();

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

      accountManager.createAccInfo(username, role, age, telNo, dob);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
