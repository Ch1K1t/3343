package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;

public class SigninPage extends Page {

  public void getInstructions() {
    System.out.println();
    System.out.println("Please input the command e.g. !CreateCoupon:");
    System.out.println("1. CreateCoupon");
    System.out.println("2. RedeemCoupon");
    System.out.println("3. Signout");
    System.out.println("4. Exit");
    System.out.println();
  }

  public void execute() {
    System.out.println();
    System.out.println("Please input your username:");
    String username = s.nextLine();
    System.out.println("Please input your password:");
    String password = s.nextLine();
    System.out.println();

    if (username.equals("admin") && password.equals("admin")) {
      System.out.println("Welcome " + username + "!");

      String cmd;
      do {
        getInstructions();
        cmd = s.nextLine().toLowerCase();

        switch (cmd) {
          case "!createcoupon":
            System.out.println("Coupon created");
            break;
          case "!redeemcoupon":
            System.out.println("Coupon redeemed");
            break;
          case "!signout":
            System.out.println("Signout");
            break;
          case "!exit":
            exit();
            break;
          default:
            System.out.println("Unknown command");
            break;
        }
      } while (!cmd.equals("!signout"));
    } else {
      System.out.println("Invalid username or password");
    }
  }
}
