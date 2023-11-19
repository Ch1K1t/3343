package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;

public class SigninPage extends Page {

  public void execute() {
    System.out.println();
    System.out.println("Please input your username:");
    String username = s.nextLine();
    System.out.println("Please input your password:");
    String password = s.nextLine();

    if (username.equals("user") && password.equals("user")) {
      new UserPage(username).execute();
    } else if (username.equals("admin") && password.equals("admin")) {
      new AdminPage().execute();
    } else {
      System.out.println("Invalid username or password");
    }
  }
}
