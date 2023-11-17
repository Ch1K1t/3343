package CouponRedeemSystem.Page;

import CouponRedeemSystem.Page.model.Page;

public class UserPage extends Page {

  public void getInstruction() {
    System.out.println();
    System.out.println("Please input the command e.g. !RedeemCoupon:");
    System.out.println("1. RedeemCoupon");
    System.out.println("2. Signout");
    System.out.println("3. Exit");
    System.out.println();
  }

  public void execute() {
    String cmd;

    do {
      cmd = s.nextLine().toLowerCase();

      switch (cmd) {
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
  }
}
