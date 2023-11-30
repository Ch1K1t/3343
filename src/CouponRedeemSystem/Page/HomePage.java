package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;

public class HomePage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please select the command and input the number:");
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
      cmd = s.nextLine();

      switch (cmd) {
        case "1":
          new SigninPage().execute();
          break;
        case "2":
          createAccount("User");
          break;
        case "3":
          exit();
          break;
        default:
          System.out.println("Unknown command");
          break;
      }
    } while (!cmd.equals("3"));
  }
}
