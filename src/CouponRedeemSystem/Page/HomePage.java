package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;

public class HomePage extends Page {

  public static void getInstruction() {
    System.out.println();
    System.out.println("Please input the command e.g. !signin:");
    System.out.println("1. Signin");
    System.out.println("2. Signup");
    System.out.println("3. Exit");
    System.out.println();
  }

  public void execute() {
    System.out.println("Welcome to Coupon Redeem System");
    String cmd;
    do {
      getInstruction();
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
        case "!signin":
          new SigninPage().execute();
          break;
        case "!signup":
          new SignupPage().execute();
          break;
        case "!exit":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (true);
  }
}
