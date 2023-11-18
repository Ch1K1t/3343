package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;

public class SigninPage extends Page {

  public void execute() {
    System.out.println();
    System.out.println("Please input your username:");
    String username = s.nextLine();
    System.out.println("Please input your password:");
    String password = s.nextLine();
    System.out.println();

    if (username.equals("user") && password.equals("user")) {
      new UserPage().execute();
    } else if (
      username.equals("couponManager") && password.equals("couponManager")
    ) {
      new CouponManagerPage().execute();
    } else {
      System.out.println("Invalid username or password");
    }
  }
}
